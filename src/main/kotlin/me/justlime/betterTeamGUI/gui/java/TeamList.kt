package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamListItem
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.teamToPlaceholderMap
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

enum class SortOrder { ASC, DESC }
enum class SortType { MONEY, SCORE, LEVEL, MEMBERS }
enum class FilterType { NONE, OPEN_ONLY, CURRENTLY_ONLINE, NOT_FULL }

data class TeamListState(
    val sortOrder: SortOrder = SortOrder.DESC, // Default to Highest first
    val sortType: SortType = SortType.SCORE,   // Default to Score
    val filter: FilterType = FilterType.NONE,   // Default to No Filter
    val searchQuery: String? = null
)

fun teamList(setting: GUISetting, state: TeamListState = TeamListState()): ChestGUI = ChestGUI(setting.rows, setting.title) {

    onClick { it.isCancelled = true }
    nav {
        prevSlot = TeamListItem.prevSlot
        nextSlot = TeamListItem.nextSlot
        nextItem = TeamButton.next ?: GuiItem(Material.ARROW)
        prevItem = TeamButton.prev ?: GuiItem(Material.ARROW)
    }
    TeamListItem.background.forEach { setItem(it) }

    setItem(TeamButton.back, TeamListItem.backSlot) { GUIManager.openTeamGUI(it.whoClicked as Player) }
    setItem(TeamButton.home, TeamListItem.homeSlot) { GUIManager.openTeamGUI(it.whoClicked as Player) }

    var teams: List<Team> = Team.getTeamManager().sortTeamsByMembers().map { Team.getTeam(it) }

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
    if (state.sortOrder == SortOrder.DESC) {
        teams = teams.reversed()
    }

    // Sort Order Button (Toggle ASC/DESC)
    val currentOrderIcon = if (state.sortOrder == SortOrder.ASC) TeamListItem.sortOrderAsc else TeamListItem.sortOrderDesc
    currentOrderIcon?.let { item ->
        setItem(item) { click ->
            val newOrder = if (state.sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
            val newState = state.copy(sortOrder = newOrder)
            teamList(setting, newState).open(click.whoClicked as Player)
        }
    }

    // Sort Type Button (Cycle: Score -> Money -> Level -> Members -> Score)
    val currentTypeIcon = when (state.sortType) {
        SortType.MONEY -> TeamListItem.sortTypeMoney
        SortType.SCORE -> TeamListItem.sortTypeScore
        SortType.LEVEL -> TeamListItem.sortTypeLevel
        SortType.MEMBERS -> TeamListItem.sortTypeMembers
    }

    currentTypeIcon?.let { item ->
        setItem(item) { click ->
            // Cycle to next type
            val nextType = when (state.sortType) {
                SortType.SCORE -> SortType.MONEY
                SortType.MONEY -> SortType.LEVEL
                SortType.LEVEL -> SortType.MEMBERS
                SortType.MEMBERS -> SortType.SCORE
            }
            val newState = state.copy(sortType = nextType)
            teamList(setting, newState).open(click.whoClicked as Player)
        }
    }

    val filterIcon = when (state.filter) {
        FilterType.OPEN_ONLY -> TeamListItem.filterOpenOnly
        FilterType.CURRENTLY_ONLINE -> TeamListItem.filterCurrentlyOnline
        FilterType.NOT_FULL -> TeamListItem.filterNotFull
        FilterType.NONE -> TeamListItem.filterDefault
    }

    filterIcon?.let { item ->

        setItem(item) { click ->
            val nextFilter = when (state.filter) {
                FilterType.NONE -> FilterType.OPEN_ONLY
                FilterType.OPEN_ONLY -> FilterType.CURRENTLY_ONLINE
                FilterType.CURRENTLY_ONLINE -> FilterType.NOT_FULL
                FilterType.NOT_FULL -> FilterType.NONE
            }
            val newState = state.copy(filter = nextFilter)
            teamList(setting, newState).open(click.whoClicked as Player)
        }
    }

    val searchItem = TeamListItem.searchItem ?: GuiItem(Material.STONE)
    setItem(searchItem) { click ->
        if (click.isShiftClick || click.isRightClick) {
            //clear search
            GUIManager.openTeamListGUI(click.whoClicked as Player)
        } else {
            val player = click.whoClicked as Player
            openAnvilGUI(
                player = player,
                title = applyMiniColor(TeamListItem.searchTitle),
                label = applyMiniColor(TeamListItem.searchLabel),
                inputItem = TeamListItem.searchInputItem ?: GuiItem(Material.STONE),
                outputItem = TeamListItem.searchOutputItem ?: GuiItem(Material.STONE),
                onInvalidInput = { GUIManager.openTeamListGUI(player) },
                onCancel = { GUIManager.openTeamListGUI(player) },
                onConfirm = { query ->
                    val newState = state.copy(searchQuery = query)
                    teamList(setting, newState).open(player)
                })
        }
    }

    addPage {
        teams.forEach { team ->

            val ownerRank = team.members.getRank(PlayerRank.OWNER)
            if (ownerRank.isNotEmpty()) {
                val owner = ownerRank.random()
                val offlinePlayer = Bukkit.getOfflinePlayer(owner.playerUUID)

                val item = TeamListItem.teamItem.apply {
                    this?.texture = "[${offlinePlayer.uniqueId}]"
                    this?.placeholderOfflinePlayer = offlinePlayer
                    this?.customPlaceholder = teamToPlaceholderMap(team)
                } ?: GuiItem(Material.STONE)
                addItem(item) {
                    // TODO: Action when clicking a specific team
                }
            }
        }
    }
}