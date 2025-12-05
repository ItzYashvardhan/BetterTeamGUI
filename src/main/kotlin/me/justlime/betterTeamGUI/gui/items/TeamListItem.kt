package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamListItem {

    val config = ConfigHandler(JFiles.LIST_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    val backSlot = ConfigManager.listView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.listView.getInt(JGui.Main.BACK_SLOT)) }
    val homeSlot = ConfigManager.listView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.listView.getInt(JGui.Main.HOME_SLOT)) }
    val prevSlot = ConfigManager.listView.getInt(JGui.Main.PREV_SLOT)
    val nextSlot = ConfigManager.listView.getInt(JGui.Main.NEXT_SLOT)

    var teamItemWithDescription = config.loadItem(JGui.ListView.TEAM_ITEM_WITH_DESC)
    var teamItemWithoutDescription = config.loadItem(JGui.ListView.TEAM_ITEM_WITHOUT_DESC)

    var sortOrderAsc = config.loadItem(JGui.ListView.SORT_ORDER_ASC)
    var sortOrderDesc = config.loadItem(JGui.ListView.SORT_ORDER_DESC)
    var sortTypeMoney = config.loadItem(JGui.ListView.SORT_TYPE_MONEY)
    var sortTypeScore = config.loadItem(JGui.ListView.SORT_TYPE_SCORE)
    var sortTypeLevel = config.loadItem(JGui.ListView.SORT_TYPE_LEVEL)
    var sortTypeMembers = config.loadItem(JGui.ListView.SORT_TYPE_MEMBERS)

    var filterDefault = config.loadItem(JGui.ListView.FILTER_DEFAULT)
    var filterOpenOnly = config.loadItem(JGui.ListView.FILTER_OPEN_ONLY)
    var filterCurrentlyOnline = config.loadItem(JGui.ListView.FILTER_CURRENTLY_ONLINE)
    var filterNotFull = config.loadItem(JGui.ListView.FILTER_NOT_FULL)

    var searchItem = config.loadItem(JGui.ListView.SEARCH_ITEM)
    var searchTitle = ConfigManager.listView.getString(JGui.ListView.SEARCH_TITLE) ?: ""
    var searchLabel = ConfigManager.listView.getString(JGui.ListView.SEARCH_LABEL) ?: ""
    var searchInputItem = config.loadItem(JGui.ListView.SEARCH_INPUT_ITEM)
    var searchOutputItem = config.loadItem(JGui.ListView.SEARCH_OUTPUT_ITEM)

    var createTeamItem = config.loadItem(JGui.ListView.CREATE_TEAM_ITEM)
    var createTeamTitle = ConfigManager.listView.getString(JGui.ListView.CREATE_TEAM_TITLE) ?: ""
    var createTeamLabel = ConfigManager.listView.getString(JGui.ListView.CREATE_TEAM_LABEL) ?: ""
    var createTeamInputItem = config.loadItem(JGui.ListView.CREATE_TEAM_INPUT_ITEM)
    var createTeamOutputItem = config.loadItem(JGui.ListView.CREATE_TEAM_OUTPUT_ITEM)


    fun reload() {
        config.reload()
        teamItemWithDescription = config.loadItem(JGui.ListView.TEAM_ITEM_WITH_DESC)
        teamItemWithoutDescription = config.loadItem(JGui.ListView.TEAM_ITEM_WITHOUT_DESC)

        sortOrderAsc = config.loadItem(JGui.ListView.SORT_ORDER_ASC)
        sortOrderDesc = config.loadItem(JGui.ListView.SORT_ORDER_DESC)
        sortTypeMoney = config.loadItem(JGui.ListView.SORT_TYPE_MONEY)
        sortTypeScore = config.loadItem(JGui.ListView.SORT_TYPE_SCORE)
        sortTypeLevel = config.loadItem(JGui.ListView.SORT_TYPE_LEVEL)
        sortTypeMembers = config.loadItem(JGui.ListView.SORT_TYPE_MEMBERS)

        filterDefault = config.loadItem(JGui.ListView.FILTER_DEFAULT)
        filterOpenOnly = config.loadItem(JGui.ListView.FILTER_OPEN_ONLY)
        filterCurrentlyOnline = config.loadItem(JGui.ListView.FILTER_CURRENTLY_ONLINE)
        filterNotFull = config.loadItem(JGui.ListView.FILTER_NOT_FULL)

        searchItem = config.loadItem(JGui.ListView.SEARCH_ITEM)
        searchTitle = ConfigManager.listView.getString(JGui.ListView.SEARCH_TITLE) ?: ""
        searchLabel = ConfigManager.listView.getString(JGui.ListView.SEARCH_LABEL) ?: ""
        searchInputItem = config.loadItem(JGui.ListView.SEARCH_INPUT_ITEM)
        searchOutputItem = config.loadItem(JGui.ListView.SEARCH_OUTPUT_ITEM)

        createTeamItem = config.loadItem(JGui.ListView.CREATE_TEAM_ITEM)
        createTeamTitle = ConfigManager.listView.getString(JGui.ListView.CREATE_TEAM_TITLE) ?: ""
        createTeamLabel = ConfigManager.listView.getString(JGui.ListView.CREATE_TEAM_LABEL) ?: ""
        createTeamInputItem = config.loadItem(JGui.ListView.CREATE_TEAM_INPUT_ITEM)
        createTeamOutputItem = config.loadItem(JGui.ListView.CREATE_TEAM_OUTPUT_ITEM)

    }

}