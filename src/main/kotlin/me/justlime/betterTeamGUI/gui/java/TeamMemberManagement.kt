package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.ColorPickerItem
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamMemberManagementItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyBackground
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamMemberManagement(player: Player, targetTeamPlayer: TeamPlayer, team: Team) {

    val setting = TeamMemberManagementItem.setting.copy()
    setting.style.placeholder = TeamService.applyPlaceHolder(team, targetTeamPlayer)

    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        applyBackground(TeamMemberManagementItem, this, false){
            GUIManager.openTeamMemberGUI(player,team)
        }

        val playerInfo = TeamMemberManagementItem.playerInfo?.clone().apply {
            this?.texture = "[${targetTeamPlayer.player.uniqueId}]"
        }


        setItem(playerInfo)
        val teamPlayer = team.getTeamPlayer(player) ?: return@ChestGUI


        if ((teamPlayer.rank == PlayerRank.OWNER && teamPlayer.playerUUID == targetTeamPlayer.playerUUID)) {
            setItem(TeamMemberManagementItem.demoteToAdmin) {
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it,setting.style)
                    return@setItem
                }
                GUIManager.openTeamDemoteToAdminDialog(player, targetTeamPlayer)
            }
        }


        if (teamPlayer.rank == PlayerRank.OWNER && targetTeamPlayer.rank == PlayerRank.ADMIN) {
            setItem(TeamMemberManagementItem.demoteToDefault) {
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it,setting.style)
                    return@setItem
                }
                GUIManager.openTeamDemoteToDefaultDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank == PlayerRank.OWNER && targetTeamPlayer.rank == PlayerRank.DEFAULT) {
            setItem(TeamMemberManagementItem.promoteToAdmin) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it,setting.style)
                    return@setItem
                }
                GUIManager.openTeamPromoteToAdminDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank == PlayerRank.OWNER && targetTeamPlayer.rank == PlayerRank.ADMIN) {
            setItem(TeamMemberManagementItem.promoteToOwner) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it,setting.style)
                    return@setItem
                }
                GUIManager.openTeamPromoteToOwnerDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank != PlayerRank.DEFAULT && teamPlayer.rank != targetTeamPlayer.rank && targetTeamPlayer.rank != PlayerRank.OWNER) {
            setItem(TeamMemberManagementItem.kick) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it,setting.style)
                    return@setItem
                }
                GUIManager.openTeamKickDialog(player, targetTeamPlayer)
            }
        }

        if (teamPlayer.rank != PlayerRank.DEFAULT && teamPlayer.rank != targetTeamPlayer.rank && targetTeamPlayer.rank != PlayerRank.OWNER) {
            setItem(TeamMemberManagementItem.ban) {
                if (targetTeamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(it,setting.style)
                    return@setItem
                }
                GUIManager.openTeamBanDialog(player, targetTeamPlayer)
            }
        }
    }.open(player)


}