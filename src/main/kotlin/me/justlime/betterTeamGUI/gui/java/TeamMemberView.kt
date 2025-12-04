package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamMemberItem
import me.justlime.betterTeamGUI.utilities.applyBetterTeamPlaceholderMap
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamMemberView(setting: GUISetting, team: Team): ChestGUI = ChestGUI(setting) {

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
            }
            item?.let { guiItem ->
                addItem(guiItem) { click ->
                    // TODO: Open member management GUI
                    GUIManager.openTeamMemberManagementGUI(click.whoClicked as Player, team, member)
                }
            }
        }
    }
}
