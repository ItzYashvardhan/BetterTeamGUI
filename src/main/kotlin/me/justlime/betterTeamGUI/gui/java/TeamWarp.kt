package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Main
import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import com.booksaw.betterTeams.Warp
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamWarpItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.adventure
import me.justlime.betterTeamGUI.utilities.applyBackground
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamWarp(setting: GUISetting, team: Team, teamPlayer: TeamPlayer, player: Player): ChestGUI = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    applyBackground(TeamWarpItem, this) {
        GUIManager.openTeamGUI(player)
    }
    addPage {
        // Calculate Limits
        val warps = team.warps.get()

        val currentMaxWarps = team.maxWarps

        val levelsSection = Main.plugin.config.getConfigurationSection("levels")
        val ultimateMaxWarps = levelsSection?.getKeys(false)?.maxOfOrNull { key ->
            levelsSection.getInt("$key.maxWarps")
        } ?: currentMaxWarps

        // Display Claimed (Occupied) Warps
        val warpItemTemplate = TeamWarpItem.occupiedWarpItem
        if (warpItemTemplate != null) {
            warps.forEach { warp ->
                val warpItemCopy = warpItemTemplate.copy().apply {
                    name = name.replace("{warp}", warp.name)
                    lore = lore.map { it.replace("{warp}", warp.name) }.toMutableList()
                }

                addItem(warpItemCopy) { clickEvent ->
                    if (clickEvent.click.isShiftClick) {
                        if (teamPlayer.rank == PlayerRank.DEFAULT) {
                            permissionDenied(clickEvent, setting.style)
                            return@addItem
                        }
                        TeamService.delWarp(clickEvent.whoClicked as Player, warp.name)
                        foliaLib.scheduler.runLater(Runnable {
                            GUIManager.openTeamWarpGUI(clickEvent.whoClicked as Player)
                        }, 2)
                        return@addItem

                    }

                    // Teleport Logic
                    else {
                        (clickEvent.whoClicked as? Player)?.let { player ->
                            if (!warp.hasPassword()) {
                                foliaLib.scheduler.runAtEntityLater(player, Runnable {
                                    TeamService.warp(player, warp.name)
                                }, 2)
                                GUIManager.closeInventory(player)
                            } else {
                                validateAndTeleport(player, warp)
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
                    if (teamPlayer.rank == PlayerRank.DEFAULT) {
                        permissionDenied(clickEvent, setting.style)
                        return@addItem
                    }

                    (clickEvent.whoClicked as? Player)?.let { player ->
                        val title = applyMiniColor(TeamWarpItem.setWarpNameTitle)
                        val label = applyMiniColor(TeamWarpItem.setWarpNameLabel)
                        val inputItem = TeamWarpItem.setWarpNameInputItem ?: GuiItem(Material.PAPER)
                        val outputItem = TeamWarpItem.setWarpNameOutputItem ?: GuiItem(Material.PAPER)

                        val reopenGUI = { GUIManager.openTeamWarpGUI(clickEvent.whoClicked as Player) }

                        openAnvilGUI(player, title, label, inputItem, outputItem, reopenGUI, reopenGUI) { warpInput ->
                            val args = if (warpInput.contains(" ")) {
                                warpInput.split(" ").toTypedArray()
                            } else arrayOf(warpInput)

                            TeamService.setWarp(player, args[0], args.getOrNull(1))
                            foliaLib.scheduler.runLater(Runnable {
                                reopenGUI()
                            }, 2)

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
                addItem(lockedItemCopy)
            }
        }
    }

}

fun validateAndTeleport(player: Player, warp: Warp) {
    val title = applyMiniColor(TeamWarpItem.enterWarpPasswordToTeleportTitle)
    val label = applyMiniColor(TeamWarpItem.enterWarpPasswordToTeleportLabel)
    val inputItem = TeamWarpItem.enterWarpPasswordToTeleportInputItem ?: GuiItem(Material.STONE)
    val outputItem = TeamWarpItem.enterWarpPasswordToTeleportOutputItem ?: GuiItem(Material.STONE)
    val onInvalidInput = { }
    val onCancel = { GUIManager.openTeamWarpGUI(player) }


    openAnvilGUI(player, title, label, inputItem, outputItem, onInvalidInput, onCancel) { password ->
        if (warp.isCorrectPassword(password)) {
            foliaLib.scheduler.runAtEntityLater(player, Runnable {
                TeamService.warp(player, warp.name, password)
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

