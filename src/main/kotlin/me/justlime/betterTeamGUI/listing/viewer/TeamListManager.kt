package me.justlime.betterTeamGUI.listing.viewer

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.models.enums.FilterType
import me.justlime.betterTeamGUI.models.enums.SortOrder
import me.justlime.betterTeamGUI.models.enums.SortType
import me.justlime.betterTeamGUI.models.state.TeamListState
import net.justlime.limeframegui.manager.GuiManager
import net.justlime.limeframegui.registry.component.PlaceholderRegistry
import net.justlime.limeframegui.registry.gui.ButtonRegistry
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import org.bukkit.entity.Player
import java.util.*

object TeamListManager {

    // ===================================================================
    // STATE MANAGEMENT
    // ===================================================================
    private val playerStates = mutableMapOf<UUID, TeamListState>()
    private const val GUI_ID = "pager/team_list"

    fun getState(player: Player): TeamListState = playerStates.getOrPut(player.uniqueId) { TeamListState() }

    fun removeState(player: Player) {
        playerStates.remove(player.uniqueId)
    }

    // ===================================================================
    // REGISTRY INITIALIZATION
    // ===================================================================
    fun registerAll() {
        registerPopulators()
        registerActions()
        registerPlaceholders()
    }

    // ===================================================================
    // THE POPULATOR
    // ===================================================================
    private fun registerPopulators() {
        ListPopulatorRegistry.register("teams_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates
            val state = getState(player)

            var teams = Team.getTeamManager().sortTeamsByMembers().mapNotNull { Team.getTeam(it) }

            // Apply Filters
            teams = when (state.filter) {
                FilterType.OPEN_ONLY -> teams.filter { it.isOpen }
                FilterType.CURRENTLY_ONLINE -> teams.filter { it.onlineMembers.isNotEmpty() }
                FilterType.NOT_FULL -> teams.filter { it.members.size() < it.teamLimit }
                FilterType.NONE -> teams
            }.toMutableList()

            // Apply Search
            state.searchQuery?.let {
                if (it.isNotBlank()) {
                    teams = teams.filter { team -> team.name.contains(it, ignoreCase = true) }
                }
            }

            // Apply Sort
            val selector: (Team) -> Comparable<*> = when (state.sortType) {
                SortType.SCORE -> { t -> t.score }
                SortType.LEVEL -> { t -> t.level }
                SortType.MEMBERS -> { t -> t.members.size() }
                SortType.MONEY -> { t -> t.money }
            }

            teams = teams.sortedWith(compareBy(selector))
            if (state.sortOrder == SortOrder.DESC) teams = teams.reversed()


            // Map Data to the Templates
            teams.mapNotNull { team ->
                val ownerRank = team.members.getRank(PlayerRank.OWNER)
                if (ownerRank.isEmpty()) return@mapNotNull null

                val hasDescription = !team.description.isNullOrEmpty()
                val viewerHasTeam = Team.getTeam(player) != null

                val teamPlaceholders = mapOf(
                    "team" to team.name,
                    "team_color" to team.color.name,
                    "team_tag" to (team.tag ?: ""),
                    "team_description" to (team.description ?: ""),
                    "team_size" to team.members.size().toString(),
                    "team_score" to team.score.toString(),
                    "team_limit" to team.teamLimit.toString(),
                    "team_level" to team.level.toString()
                )

                val templateKey = when {
                    viewerHasTeam && hasDescription -> "team-item-with-description-in-team"
                    viewerHasTeam && !hasDescription -> "team-item-without-description-in-team"
                    !viewerHasTeam && hasDescription -> "team-item-with-description-no-team"
                    else -> "team-item-without-description-no-team"
                }

                val baseTemplate = templatesMap[templateKey] ?: return@mapNotNull null
                val randomOwnerPlayer = ownerRank.random().player

                baseTemplate.clone().apply {
                    this.style.offlinePlayer = randomOwnerPlayer
                    this.style.placeholder.putAll(teamPlaceholders)
                    this.style.viewer = null
                    this.baseItem = this.baseItem.clone()
                }
            }
        }
    }

    // ===================================================================
    // THE ACTIONS
    // ===================================================================
    private fun registerActions() {

        ButtonRegistry.register("list_action_filter") { response ->
            val state = getState(response.player)
            val nextOrdinal = (state.filter.ordinal + 1) % FilterType.entries.size
            state.filter = FilterType.entries[nextOrdinal]
            GuiManager.open(response.player, GUI_ID, recordHistory = false)
        }

        ButtonRegistry.register("list_action_sort_type") { response ->
            val state = getState(response.player)
            val nextOrdinal = (state.sortType.ordinal + 1) % SortType.entries.size
            state.sortType = SortType.entries[nextOrdinal]
            GuiManager.open(response.player, GUI_ID, recordHistory = false)
        }

        ButtonRegistry.register("list_action_sort_order") { response ->
            val state = getState(response.player)
            state.sortOrder = if (state.sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
            GuiManager.open(response.player, GUI_ID, recordHistory = false)
        }

        ButtonRegistry.register("list_action_search") { response ->
            val state = getState(response.player)
            state.searchQuery = response.payload
            println(state.searchQuery)
            GuiManager.open(response.player, GUI_ID, recordHistory = false)
        }

    }

    private fun registerPlaceholders() {
        PlaceholderRegistry.register("list_sort_type") { player, _ ->
            getState(player).sortType.name
        }

        PlaceholderRegistry.register("list_sort_order") { player, _ ->
            getState(player).sortOrder.name
        }

        PlaceholderRegistry.register("list_filter") { player, _ ->
            getState(player).filter.name
        }
        PlaceholderRegistry.register("list_search") { player, _ ->
            getState(player).searchQuery ?: ""
        }
    }
}