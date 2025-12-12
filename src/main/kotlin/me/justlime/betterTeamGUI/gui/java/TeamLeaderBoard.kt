package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.LeaderBoardItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyBackground
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamLeaderBoard(setting: GUISetting, player: Player, team: Team) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        applyBackground(LeaderBoardItem, this) {
            GUIManager.openTeamGUI(player)
        }
        addPage {
            val teams = Team.getTeamManager().sortTeamsByScore().mapNotNull { Team.getTeam(it) }
            teams.forEachIndexed { index, team ->

                val owners = team.members.get().filter { it.rank == PlayerRank.OWNER }
                val owner = owners.random()
                val item = LeaderBoardItem.teamLeaderboardItem?.apply {
                    style.placeholder = TeamService.teamToPlaceholderMap(team).toMutableMap().apply {
                        put("{team_position}", (index + 1).toString())
                    }
                    texture = "[${owner.playerUUID}]"
                } ?: GuiItem(Material.STONE)
                addItem(item) { clickEvent ->
                    if (clickEvent.click.isLeftClick) {
                        GUIManager.openTeamViewerGUI(player, team)
                    }
                }
            }
        }
    }.open(player)

}