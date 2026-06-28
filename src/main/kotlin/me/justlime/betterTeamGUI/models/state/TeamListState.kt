package me.justlime.betterTeamGUI.models.state

import me.justlime.betterTeamGUI.models.enums.FilterType
import me.justlime.betterTeamGUI.models.enums.SortOrder
import me.justlime.betterTeamGUI.models.enums.SortType

data class TeamListState(
    var sortOrder: SortOrder = SortOrder.DESC, // Default to Highest first
    var sortType: SortType = SortType.SCORE,   // Default to Score
    var filter: FilterType = FilterType.NONE,   // Default to No Filter
    var searchQuery: String? = null
)