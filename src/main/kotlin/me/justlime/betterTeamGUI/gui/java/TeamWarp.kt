package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import com.booksaw.betterTeams.Warp
import com.booksaw.betterTeams.commands.team.DelwarpCommand
import com.booksaw.betterTeams.commands.team.WarpCommand
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamWarpItem
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamWarp(guiSetting: GUISetting, team: Team, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {
    onClick { it.isCancelled = true }

    TeamWarpItem.background.forEach { setItem(it) }

    val warps = team.warps.get()
    val warpItem = TeamWarpItem.occupiedWarpItem ?: return@ChestGUI
    warps.forEach { warp ->
        val warpItemCopy = warpItem.copy() // Important otherwise all display name or lore will be same
        warpItemCopy.name = warpItemCopy.name.replace("{warp}", warp.name)
        warpItemCopy.lore = warpItemCopy.lore.map { it.replace("{warp}", warp.name) }.toMutableList()

        addItem(warpItemCopy) {
            if (it.click.isShiftClick && teamPlayer.rank != PlayerRank.DEFAULT) {
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    DelwarpCommand().onCommand(teamPlayer, "", arrayOf(warp.name), team)
                    teamWarp(guiSetting, team, teamPlayer).open(it.whoClicked as Player)
                }, 2)
            } else {
                val player = it.whoClicked as? Player ?: return@addItem
                if (!warp.hasPassword()) {
                    Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                        WarpCommand().onCommand(teamPlayer, "", arrayOf(warp.name), team)
                    }, 2)
                    GUIManager.closeInventory(player)
                } else {
                    validateAndTeleport(player,teamPlayer ,team,warp)
                }
            }

        }
    }

    val availableWarps = team.maxWarps - warps.size
    val claimableWarpItem = TeamWarpItem.claimableWarpItem ?: return@ChestGUI
    if (availableWarps > 0) repeat(availableWarps) {
        addItem(claimableWarpItem) { clickEvent ->
            // Use a safe cast with 'let' to only execute the block if the clicker is a Player.
            (clickEvent.whoClicked as? Player)?.let { player ->
                player.closeInventory()
                player.sendMessage("Please use /team setwarp <name> to set a warp")
            }
        }
    }

    val backItem = TeamButton.back
    val backItemSlot = TeamButton.backSlot?.slot
    setItem(backItem, backItemSlot) {}
}

fun validateAndTeleport(player: Player, teamPlayer: TeamPlayer, team: Team, warp: Warp) {
    val title = TeamWarpItem.enterWarpPasswordToTeleportTitle.let { Component.text(it ?: "") }
    val label = TeamWarpItem.enterWarpPasswordToTeleportLabel.let { Component.text(it ?: "") }
    val inputItem = TeamWarpItem.enterWarpPasswordToTeleportInputItem ?: GuiItem(Material.STONE)
    val outputItem = TeamWarpItem.enterWarpPasswordToTeleportOutputItem ?: GuiItem(Material.STONE)

    println("Input Item: ${inputItem.toItemStack().itemMeta}")
    println("Output Item: ${outputItem.toItemStack().itemMeta}")


    openAnvilGUI(player, title, label, inputItem, outputItem, onInvalidInput = {
        // Handle invalid input if needed
    }, onCancel = {
        // Reopen the warp GUI if cancelled
        GUIManager.openTeamWarpGUI(player)
    }) { password ->
        if (warp.isCorrectPassword(password)) {
            Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                WarpCommand().onCommand(teamPlayer, "", arrayOf(warp.name, password), team)
            }, 2)
            GUIManager.closeInventory(player)
        } else {
            player.sendMessage("Incorrect password!") // Replace with proper message from config
            GUIManager.openTeamWarpGUI(player) // Reopen the warp GUI on incorrect password
        }
    }
}

