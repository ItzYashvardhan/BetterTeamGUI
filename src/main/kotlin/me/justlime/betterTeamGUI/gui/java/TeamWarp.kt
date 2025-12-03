package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Main
import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import com.booksaw.betterTeams.Warp
import com.booksaw.betterTeams.commands.team.DelwarpCommand
import com.booksaw.betterTeams.commands.team.SetWarpCommand
import com.booksaw.betterTeams.commands.team.WarpCommand
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamWarpItem
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.adventure
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamWarp(guiSetting: GUISetting, team: Team, teamPlayer: TeamPlayer, player: Player): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {
    onClick { it.isCancelled = true }

    // Background & Static Items
    TeamWarpItem.background.forEach { setItem(it) }
    setItem(TeamButton.back, TeamWarpItem.backSlot) { GUIManager.openTeamGUI(player) }
    setItem(TeamButton.home, TeamWarpItem.homeSlot) { GUIManager.openTeamGUI(player) }

    // Calculate Limits
    val warps = team.warps.get()
    // You confirmed team.maxWarps is the limit for the CURRENT level
    val currentMaxWarps = team.maxWarps

    // Calculate the Ultimate Max Warps from Config
    val levelsSection = Main.plugin.config.getConfigurationSection("levels")
    val ultimateMaxWarps = levelsSection?.getKeys(false)?.maxOfOrNull { key ->
        levelsSection.getInt("$key.maxWarps")
    } ?: currentMaxWarps // Fallback to current if config fails

    // Display Claimed (Occupied) Warps
    val warpItemTemplate = TeamWarpItem.occupiedWarpItem
    if (warpItemTemplate != null) {
        warps.forEach { warp ->
            val warpItemCopy = warpItemTemplate.copy().apply {
                name = name.replace("{warp}", warp.name)
                lore = lore.map { it.replace("{warp}", warp.name) }.toMutableList()
            }

            addItem(warpItemCopy) { clickEvent ->
                // Shift-Click Delete Logic
                if (clickEvent.click.isShiftClick && teamPlayer.rank != PlayerRank.DEFAULT) {
                    Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                        DelwarpCommand().onCommand(teamPlayer, "", arrayOf(warp.name), team)
                        GUIManager.openTeamWarpGUI(clickEvent.whoClicked as Player)
                    }, 2)
                }

                // Teleport Logic
                else {
                    (clickEvent.whoClicked as? Player)?.let { player ->
                        if (!warp.hasPassword()) {
                            Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                                WarpCommand().onCommand(teamPlayer, "", arrayOf(warp.name), team)
                            }, 2)
                            GUIManager.closeInventory(player)
                        } else {
                            validateAndTeleport(player, teamPlayer, team, warp)
                        }
                    }
                }
            }
        }
    }

    // Display Claimable Slots
    // Only show claimable slots up to the CURRENT level's limit
    val availableSlots = currentMaxWarps - warps.size
    val claimableWarpItem = TeamWarpItem.claimableWarpItem

    if (availableSlots > 0 && claimableWarpItem != null) {
        repeat(availableSlots) {
            addItem(claimableWarpItem) { clickEvent ->
                (clickEvent.whoClicked as? Player)?.let { player ->
                    val title = applyMiniColor(TeamWarpItem.setWarpNameTitle ?: "")
                    val label = applyMiniColor(TeamWarpItem.setWarpNameLabel ?: "")
                    // Safely handle input/output items with fallbacks
                    val inputItem = TeamWarpItem.setWarpNameInputItem ?: GuiItem(Material.PAPER)
                    val outputItem = TeamWarpItem.setWarpNameOutputItem ?: GuiItem(Material.PAPER)

                    val reopenGUI = { GUIManager.openTeamWarpGUI(clickEvent.whoClicked as Player) }

                    openAnvilGUI(player, title, label, inputItem, outputItem, reopenGUI, reopenGUI) { warpInput ->
                        val args = if (warpInput.contains(" ")) {
                            warpInput.split(" ").toTypedArray()
                        } else {
                            arrayOf(warpInput)
                        }
                        SetWarpCommand().onCommand(teamPlayer, "", args, team)
                        reopenGUI()
                    }
                }
            }
        }
    }

    // Display Locked Slots
    // Show slots starting from the current limit up to the ultimate config limit
    val lockedWarpItem = TeamWarpItem.lockedWarpItem

    if (currentMaxWarps < ultimateMaxWarps && lockedWarpItem != null && levelsSection != null) {

        for (slotIndex in currentMaxWarps until ultimateMaxWarps) {

            val requiredLevelNum = levelsSection.getKeys(false).mapNotNull { key ->
                // Parse "l1" -> 1
                val levelNum = key.removePrefix("l").toIntOrNull() ?: return@mapNotNull null
                val maxAtLevel = levelsSection.getInt("$key.maxWarps")
                // Return pair of (LevelNumber, MaxWarpsAtThatLevel)
                levelNum to maxAtLevel
            }.sortedBy { it.first }.firstOrNull { (_, maxAtLevel) -> maxAtLevel > slotIndex } // Find first level that unlocks this slot
                ?.first ?: (team.level + 1)

            val lockedItemCopy = lockedWarpItem.copy().apply {
                name = name.replace("{level}", requiredLevelNum.toString())
                lore = lore.map { it.replace("{level}", requiredLevelNum.toString()) }.toMutableList()
            }
            addItem(lockedItemCopy) {}
        }
    }
}

fun validateAndTeleport(player: Player, teamPlayer: TeamPlayer, team: Team, warp: Warp) {
    val title = applyMiniColor(TeamWarpItem.enterWarpPasswordToTeleportTitle ?: "")
    val label = applyMiniColor(TeamWarpItem.enterWarpPasswordToTeleportLabel ?: "")
    println(label)
    val inputItem = TeamWarpItem.enterWarpPasswordToTeleportInputItem ?: GuiItem(Material.STONE)
    val outputItem = TeamWarpItem.enterWarpPasswordToTeleportOutputItem ?: GuiItem(Material.STONE)
    val onInvalidInput = { GUIManager.openTeamWarpGUI(player) }
    val onCancel = { GUIManager.openTeamWarpGUI(player) }


    openAnvilGUI(player, title, label, inputItem, outputItem, onInvalidInput = onInvalidInput, onCancel = onCancel) { password ->
        if (warp.isCorrectPassword(password)) {
            Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                WarpCommand().onCommand(teamPlayer, "", arrayOf(warp.name, password), team)
            }, 2)
            GUIManager.closeInventory(player)
        } else {
            val msg = ConfigManager.messages.getString("warp-password-incorrect.chat") ?: ""
            val componentMsg = applyMiniColor(msg)
            adventure.player(player).sendMessage(componentMsg)
            GUIManager.openTeamWarpGUI(player)
        }
    }
}

