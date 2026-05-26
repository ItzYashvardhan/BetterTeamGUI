package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.models.enums.FilterType
import me.justlime.betterTeamGUI.models.enums.SortOrder
import me.justlime.betterTeamGUI.models.enums.SortType
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import org.bukkit.entity.Player
import java.util.UUID

object TeamListManager {
    //Manage State per player
    private val playerStates = mutableMapOf<UUID, TeamListState>()

    fun getState(player: Player): TeamListState = playerStates.getOrPut(player.uniqueId) { TeamListState() }
    fun updateState(player: Player, state: TeamListState) {
        playerStates[player.uniqueId] = state
    }

    // 2. Register the Populator during your plugin's onEnable!
    fun registerPopulators() {
        ListPopulatorRegistry.register("teams_list") { player, mask ->
            val templatesMap = mask.templates
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

                val templateKey = when {
                    viewerHasTeam && hasDescription -> "in-team-has-description"
                    viewerHasTeam && !hasDescription -> "in-team"
                    !viewerHasTeam && hasDescription -> "no-team-has-description"
                    else -> "no-team"
                }

                val baseTemplate =
                    templatesMap[templateKey] ?: templatesMap["default"] ?: templatesMap.values.firstOrNull()
                if (baseTemplate == null) return@mapNotNull null

                val randomOwnerPlayer = ownerRank.random().player
                val item = baseTemplate.clone().apply {
                    this.style.offlinePlayer = randomOwnerPlayer
                    this.style.viewer = null
                }
                item
            }
        }
    }
}