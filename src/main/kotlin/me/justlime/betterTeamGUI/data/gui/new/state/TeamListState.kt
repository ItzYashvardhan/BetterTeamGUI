package me.justlime.betterTeamGUI.data.gui.new.state

import me.justlime.betterTeamGUI.models.enums.FilterType
import me.justlime.betterTeamGUI.models.enums.SortOrder
import me.justlime.betterTeamGUI.models.enums.SortType

data class TeamListState(
    val sortOrder: SortOrder = SortOrder.DESC, // Default to Highest first
    val sortType: SortType = SortType.SCORE,   // Default to Score
    val filter: FilterType = FilterType.NONE,   // Default to No Filter
    val searchQuery: String? = null
)