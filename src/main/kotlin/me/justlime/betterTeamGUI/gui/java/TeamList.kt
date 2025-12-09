package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Main
import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamListItem
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.TeamService
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

fun teamList(setting: GUISetting, player: Player, state: TeamListState = TeamListState()) {
    ChestGUI(setting) {

        onClick { it.isCancelled = true }
        nav {
            prevSlot = TeamListItem.prevSlot
            nextSlot = TeamListItem.nextSlot
            nextItem = TeamButton.next ?: GuiItem(Material.ARROW)
            prevItem = TeamButton.prev ?: GuiItem(Material.ARROW)
        }
        TeamListItem.background.forEach { setItem(it) }

        setItem(TeamButton.home, TeamListItem.homeSlot) { GUIManager.openTeamGUI(it.whoClicked as Player) }

        var teams: List<Team> = Team.getTeamManager().sortTeamsByMembers().mapNotNull { Team.getTeam(it) }

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

        val currentOrderIcon = if (state.sortOrder == SortOrder.ASC) TeamListItem.sortOrderAsc else TeamListItem.sortOrderDesc

        setItem(currentOrderIcon) {
            val newOrder = if (state.sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
            val newState = state.copy(sortOrder = newOrder)
            teamList(setting, player, newState)
        }

        // Sort Type Button (Cycle: Score -> Money -> Level -> Members -> Score)
        val currentTypeIcon = when (state.sortType) {
            SortType.MONEY -> TeamListItem.sortTypeMoney
            SortType.SCORE -> TeamListItem.sortTypeScore
            SortType.LEVEL -> TeamListItem.sortTypeLevel
            SortType.MEMBERS -> TeamListItem.sortTypeMembers
        }


        setItem(currentTypeIcon) {
            val nextType = when (state.sortType) {
                SortType.SCORE -> SortType.MONEY
                SortType.MONEY -> SortType.LEVEL
                SortType.LEVEL -> SortType.MEMBERS
                SortType.MEMBERS -> SortType.SCORE
            }
            val newState = state.copy(sortType = nextType)
            teamList(setting, player, newState)
        }

        val filterIcon = when (state.filter) {
            FilterType.OPEN_ONLY -> TeamListItem.filterOpenOnly
            FilterType.CURRENTLY_ONLINE -> TeamListItem.filterCurrentlyOnline
            FilterType.NOT_FULL -> TeamListItem.filterNotFull
            FilterType.NONE -> TeamListItem.filterDefault
        }


        setItem(filterIcon) {
            val nextFilter = when (state.filter) {
                FilterType.NONE -> FilterType.OPEN_ONLY
                FilterType.OPEN_ONLY -> FilterType.CURRENTLY_ONLINE
                FilterType.CURRENTLY_ONLINE -> FilterType.NOT_FULL
                FilterType.NOT_FULL -> FilterType.NONE
            }
            val newState = state.copy(filter = nextFilter)
            teamList(setting, player, newState)
        }

        setItem(TeamListItem.searchItem) { click ->
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
                        teamList(setting, player, newState)
                    })
            }
        }
        if (!Team.getTeamManager().isInTeam(player)) setItem(TeamListItem.createTeamItem) { click ->
            val player = click.whoClicked as Player
            openAnvilGUI(
                player = player,
                title = applyMiniColor(TeamListItem.createTeamTitle),
                label = applyMiniColor(TeamListItem.createTeamLabel),
                inputItem = TeamListItem.createTeamInputItem ?: GuiItem(Material.STONE),
                outputItem = TeamListItem.createTeamOutputItem ?: GuiItem(Material.STONE),
                onInvalidInput = { },
                onCancel = { GUIManager.openTeamListGUI(player) },
                onConfirm = { teamName ->
                    TeamService.createTeam(player, teamName)
                    Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                        GUIManager.openTeamGUI(player)
                    }, 4)
                })
        }

        val isInTeam = Team.getTeamManager().isInTeam(player)


        Main.plugin.teamCommand

        addPage {

            teams.forEach { team ->
                val isTeamOpen = team.isOpen
                val isInvited = team.isInvited(player.uniqueId)

                val ownerRank = team.members.getRank(PlayerRank.OWNER)
                if (ownerRank.isNotEmpty()) {
                    val owner = ownerRank.random()
                    val offlinePlayer = Bukkit.getOfflinePlayer(owner.playerUUID)
                    val item = when {
                        team.description.isNotBlank() && isInTeam -> TeamListItem.teamItemWithDescription

                        team.description.isNotBlank() && !isInTeam && (isTeamOpen || isInvited) -> TeamListItem.teamItemWithDescriptionNoTeam

                        !isInTeam && (isTeamOpen || isInvited) -> TeamListItem.teamItemWithoutDescriptionNoTeam

                        else -> TeamListItem.teamItemWithoutDescription
                    }

                    val finalItem = item?.apply {
                        texture = "[${offlinePlayer.uniqueId}]"
                        style.offlinePlayer = offlinePlayer
                        style.placeholder = teamToPlaceholderMap(team)
                    } ?: GuiItem(Material.STONE)

                    addItem(finalItem) {
                        if (!isInTeam and it.click.isRightClick) {
                            TeamService.joinTeam(player, team.name)
                            player.closeInventory()
                        }
                    }
                }
            }
        }
    }.open(player)
}