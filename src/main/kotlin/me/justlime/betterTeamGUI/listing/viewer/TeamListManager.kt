package me.justlime.betterTeamGUI.listing.viewer

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.listing.TeamListState
import me.justlime.betterTeamGUI.models.enums.FilterType
import me.justlime.betterTeamGUI.models.enums.SortOrder
import me.justlime.betterTeamGUI.models.enums.SortType
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import org.bukkit.entity.Player
import java.util.UUID

object TeamListManager {
    private val playerStates = mutableMapOf<UUID, TeamListState>()

    fun getState(player: Player): TeamListState = playerStates.getOrPut(player.uniqueId) { TeamListState() }
    fun updateState(player: Player, state: TeamListState) {
        playerStates[player.uniqueId] = state
    }

    fun registerPopulators() {
        ListPopulatorRegistry.register("teams_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates
            val state = getState(player)
            var teams = Team.getTeamManager().sortTeamsByMembers().mapNotNull { Team.getTeam(it) }

            teams = when (state.filter) {
                FilterType.OPEN_ONLY -> teams.filter { it.isOpen }
                FilterType.CURRENTLY_ONLINE -> teams.filter { it.onlineMembers.isNotEmpty() }
                FilterType.NOT_FULL -> teams.filter { it.members.size() < it.teamLimit }
                FilterType.NONE -> teams
            }

            if (state.searchQuery != null) {
                teams = teams.filter { it.name.contains(state.searchQuery, ignoreCase = true) }
            }

            val selector: (Team) -> Comparable<*> = when (state.sortType) {
                SortType.SCORE -> { t -> t.score }
                SortType.MONEY -> { t -> t.money }
                SortType.LEVEL -> { t -> t.level }
                SortType.MEMBERS -> { t -> t.members.size() }
            }

            teams = teams.sortedWith(compareBy(selector))
            if (state.sortOrder == SortOrder.DESC) teams = teams.reversed()

            // Map Data to the Template
            teams.mapNotNull { team ->
                val ownerRank = team.members.getRank(PlayerRank.OWNER)
                if (ownerRank.isEmpty()) return@mapNotNull null

                val hasDescription = team.description != null && team.description.isNotEmpty()
                val viewerHasTeam = Team.getTeam(player) != null


                val teamPlaceholders = mapOf(
                    "list_team" to team.name,
                    "list_team_color" to team.color.name,
                    "list_team_tag" to (team.tag ?: ""),
                    "list_team_description" to (team.description ?: ""),
                    "list_team_size" to team.members.size().toString(),
                    "list_team_score" to team.score.toString(),
                    "list_team_limit" to team.teamLimit.toString(),
                    "list_team_level" to team.level.toString()
                )

                val templateKey = when {
                    viewerHasTeam && hasDescription -> "team-item-with-description-in-team"
                    viewerHasTeam && !hasDescription -> "team-item-without-description-in-team"
                    !viewerHasTeam && hasDescription -> "team-item-with-description-no-team"
                    else -> "team-item-without-description-no-team"
                }


                val baseTemplate = templatesMap[templateKey] ?: return@mapNotNull null

                val randomOwnerPlayer = ownerRank.random().player

                val item = baseTemplate.clone().apply {
                    this.style.offlinePlayer = randomOwnerPlayer
                    this.style.placeholder.putAll(teamPlaceholders)
                    this.style.viewer = null
                }
                item
            }
        }
    }


}