package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamButton.noPermission
import me.justlime.betterTeamGUI.gui.items.TeamMemberItem
import me.justlime.betterTeamGUI.utilities.applyBetterTeamPlaceholderMap
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamMemberView(setting: GUISetting, team: Team): ChestGUI = ChestGUI(setting.copy()) {

    onClick { it.isCancelled = true }

    TeamMemberItem.background.forEach { setItem(it) }

    setItem(TeamButton.back, TeamMemberItem.back) { GUIManager.openTeamGUI(it.whoClicked as Player) }
    setItem(TeamButton.home, TeamMemberItem.home) { GUIManager.openTeamGUI(it.whoClicked as Player) }

    addPage {
        team.members.get().forEach { member ->
            val item = TeamMemberItem.memberItem.apply {
                this?.texture = "[${member.player.uniqueId}]"
                this?.placeholderOfflinePlayer = member.player
                this?.customPlaceholder = applyBetterTeamPlaceholderMap(team, member)
            }?.copy() ?: GuiItem(Material.PLAYER_HEAD)
            addItem(item) { click ->
                val teamPlayer = team.getTeamPlayer(click.whoClicked as Player) ?: return@addItem
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(click)
                    return@addItem
                }
                // TODO: Open member management GUI
                GUIManager.openTeamMemberManagementGUI(click.whoClicked as Player, team, member)
            }
        }
    }
}
