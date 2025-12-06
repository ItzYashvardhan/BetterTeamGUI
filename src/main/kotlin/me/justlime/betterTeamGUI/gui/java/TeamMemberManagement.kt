package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamMemberManagementItem
import me.justlime.betterTeamGUI.utilities.applyBetterTeamPlaceholderMap
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamMemberManagement(setting: GUISetting, player: Player, targetTeamPlayer: TeamPlayer, team: Team) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }

        val playerInfo = TeamMemberManagementItem.playerInfo?.clone().apply {
            this?.texture = "[${targetTeamPlayer.player.uniqueId}]"
            this?.customPlaceholder = applyBetterTeamPlaceholderMap(team, targetTeamPlayer)
        }

        TeamMemberManagementItem.background.forEach { setItem(it) }
        setItem(TeamButton.back, TeamMemberManagementItem.backSlot) { GUIManager.openTeamMemberGUI(it.whoClicked as Player, team) }
        setItem(TeamButton.home, TeamMemberManagementItem.homeSlot) { GUIManager.openTeamGUI(it.whoClicked as Player) }

        setItem(playerInfo)
        val teamPlayer = team.getTeamPlayer(player) ?: return@ChestGUI


        if ((teamPlayer.rank == PlayerRank.OWNER && teamPlayer.playerUUID == targetTeamPlayer.playerUUID)) {
            setItem(TeamMemberManagementItem.demoteToAdmin) {
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it)
                    return@setItem
                }
                GUIManager.openTeamDemoteToAdminDialog(player, targetTeamPlayer)
            }
        }


        if (teamPlayer.rank == PlayerRank.OWNER && targetTeamPlayer.rank == PlayerRank.ADMIN) {
            setItem(TeamMemberManagementItem.demoteToDefault) {
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it)
                    return@setItem
                }
                GUIManager.openTeamDemoteToDefaultDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank == PlayerRank.OWNER && targetTeamPlayer.rank == PlayerRank.DEFAULT) {
            setItem(TeamMemberManagementItem.promoteToAdmin) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it)
                    return@setItem
                }
                GUIManager.openTeamPromoteToAdminDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank == PlayerRank.OWNER && targetTeamPlayer.rank == PlayerRank.ADMIN) {
            setItem(TeamMemberManagementItem.promoteToOwner) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it)
                    return@setItem
                }
                GUIManager.openTeamPromoteToOwnerDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank != PlayerRank.DEFAULT && teamPlayer.rank != targetTeamPlayer.rank && targetTeamPlayer.rank != PlayerRank.OWNER) {
            setItem(TeamMemberManagementItem.kick) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it)
                    return@setItem
                }
                GUIManager.openTeamKickDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank != PlayerRank.DEFAULT && teamPlayer.rank != targetTeamPlayer.rank && targetTeamPlayer.rank != PlayerRank.OWNER) {
            setItem(TeamMemberManagementItem.ban) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it)
                    return@setItem
                }
                GUIManager.openTeamBanDialog(player, targetTeamPlayer)
            }
        }
    }.open(player)
}