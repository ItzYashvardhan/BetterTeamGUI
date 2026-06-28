package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry

object TeamLeaderboardManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("leaderboard_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            val baseTemplate = templatesMap["leaderboard"] ?: return@register emptyList()
            val teams = Team.getTeamManager().sortTeamsByScore().mapNotNull { Team.getTeam(it) }
            teams.mapIndexed { index, team ->
                val randomTeamOwner = team.members.get().filter { it.rank == PlayerRank.OWNER }.random()
                val leaderboardPlaceholders = mapOf(
                    "team_position" to (index + 1).toString(),
                    "team" to team.name,
                    "team_score" to team.score.toString(),
                    "color" to team.color.toString().lowercase()
                )
                val item = baseTemplate.clone().apply {
                    this.style.offlinePlayer = randomTeamOwner.player
                    this.style.placeholder.putAll(leaderboardPlaceholders)
                    this.style.viewer = null
                }
                item
            }
        }
    }
}