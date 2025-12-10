package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamAlliesItem
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamAlliesList(setting: GUISetting, player: Player, team: Team) {

    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        nav {
            prevSlot = TeamAlliesItem.prevSlot
            nextSlot = TeamAlliesItem.nextSlot
        }
        TeamAlliesItem.background.forEach { setItem(it) }
        TeamAlliesItem.backSlot.forEach { slot ->
            setItem(TeamButton.back, slot) {
                GUIManager.openTeamGUI(it.whoClicked as Player)
            }
        }

        TeamAlliesItem.homeSlot.forEach { slot ->
            setItem(TeamButton.home, slot) {
                GUIManager.openTeamGUI(it.whoClicked as Player)
            }
        }

        val alliesRequestInbox = TeamAlliesItem.allyRequestInbox?.apply {
            style.placeholder = TeamService.teamToPlaceholderMap(team)
        }
        val teamPlayer = team.getTeamPlayer(player)
        setItem(alliesRequestInbox) { clickEvent ->
            if (teamPlayer?.rank == PlayerRank.DEFAULT) {
                permissionDenied(clickEvent, setting.style)
                return@setItem
            }
            val clickedPlayer = clickEvent.whoClicked as Player
            teamAllyRequests(setting, clickedPlayer, team)
        }

        addPage {
            team.allies.get().forEach { uuid ->
                val allyTeam = Team.getTeam(uuid) ?: return@forEach
                val ownerRank = team.members.getRank(PlayerRank.OWNER)
                if (ownerRank.isNotEmpty()) {
                    val owner = ownerRank.random()
                    val item = TeamAlliesItem.allyItem?.copy()?.apply {
                        texture = "[${owner.playerUUID}]"
                        val placeholder = TeamService.teamToPlaceholderMap(allyTeam).toMutableMap()
                        placeholder["{team_ally}"] = (allyTeam.name ?: "Unknown")
                        style.placeholder = placeholder
                    }

                    if (item != null) {
                        addItem(item) { clickEvent ->
                            if (clickEvent.isShiftClick) {
                                if (teamPlayer?.rank != PlayerRank.OWNER) {
                                    permissionDenied(clickEvent, setting.style)
                                    return@addItem
                                }
                                GUIManager.openTeamNeutralDialog(player, allyTeam)
                            } else {
                                // View ally team logic
//                            GUIManager.openTeamViewGUI(clickedPlayer, allyTeam)
                            }
                        }
                    }
                }
            }
        }
    }.open(player)
}

fun teamAllyRequests(setting: GUISetting, player: Player, team: Team) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }

        nav {
            prevSlot = TeamAlliesItem.prevSlot
            nextSlot = TeamAlliesItem.nextSlot
        }

        TeamAlliesItem.background.forEach { setItem(it) }
        TeamAlliesItem.backSlot.forEach { slot ->
            setItem(TeamButton.back, slot) {
                GUIManager.openTeamAlliesListGUI(it.whoClicked as Player, team)
            }
        }

        TeamAlliesItem.homeSlot.forEach { slot ->
            setItem(TeamButton.home, slot) {
                GUIManager.openTeamGUI(it.whoClicked as Player)
            }
        }

        addPage {
            team.allyRequests.forEach { uuid ->
                val allyRequestTeam = Team.getTeam(uuid) ?: return@forEach
                val ownerRank = team.members.getRank(PlayerRank.OWNER)
                if (ownerRank.isNotEmpty()) {
                    val owner = ownerRank.random()
                    val item = TeamAlliesItem.allyRequestItem?.copy()?.apply {
                        texture = "[${owner.playerUUID}]"
                        val placeholder = TeamService.teamToPlaceholderMap(allyRequestTeam).toMutableMap()
                        placeholder["{team_ally}"] = (allyRequestTeam.name ?: "Unknown")
                        style.placeholder = placeholder
                    } ?: GuiItem(Material.PAPER)
                    addItem(item) { clickEvent ->
                        val clickedPlayer = clickEvent.whoClicked as Player
                        if (clickEvent.isShiftClick) {
                            // Deny ally request logic
                            allyRequestTeam.allyRequests.remove(uuid)
                            GUIManager.openTeamAlliesListGUI(clickEvent.whoClicked as Player, team)
                        } else {
                            if (clickEvent.click.isRightClick) {
                                GUIManager.openTeamViewerGUI(player, team)
                            }
                            if (clickEvent.click.isLeftClick) {
                                // Accept ally request logic
                                TeamService.addAlly(player, allyRequestTeam)
                                clickedPlayer.closeInventory()
                            }
                        }
                    }
                }
            }

        }
    }.open(player)
}
