package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamListItem {

    val config = ConfigHandler(JFiles.LIST_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)
    var teamItem = config.loadItem(JGui.ListView.TEAM_ITEM)
    var sortOrderAsc = config.loadItem(JGui.ListView.SORT_ORDER_ASC)
    var sortOrderDesc = config.loadItem(JGui.ListView.SORT_ORDER_DESC)
    var sortTypeMoney = config.loadItem(JGui.ListView.SORT_TYPE_MONEY)
    var sortTypeScore = config.loadItem(JGui.ListView.SORT_TYPE_SCORE)
    var sortTypeLevel = config.loadItem(JGui.ListView.SORT_TYPE_LEVEL)
    var sortTypeMembers = config.loadItem(JGui.ListView.SORT_TYPE_MEMBERS)
    var filterOpenOnly = config.loadItem(JGui.ListView.FILTER_OPEN_ONLY)
    var filterCurrentlyOnline = config.loadItem(JGui.ListView.FILTER_CURRENTLY_ONLINE)
    var filterNotFull = config.loadItem(JGui.ListView.FILTER_NOT_FULL)

    fun reload() {
        config.reload()
        teamItem = config.loadItem(JGui.ListView.TEAM_ITEM)
        sortOrderAsc = config.loadItem(JGui.ListView.SORT_ORDER_ASC)
        sortOrderDesc = config.loadItem(JGui.ListView.SORT_ORDER_DESC)
        sortTypeMoney = config.loadItem(JGui.ListView.SORT_TYPE_MONEY)
        sortTypeScore = config.loadItem(JGui.ListView.SORT_TYPE_SCORE)
        sortTypeLevel = config.loadItem(JGui.ListView.SORT_TYPE_LEVEL)
        sortTypeMembers = config.loadItem(JGui.ListView.SORT_TYPE_MEMBERS)
        filterOpenOnly = config.loadItem(JGui.ListView.FILTER_OPEN_ONLY)
        filterCurrentlyOnline = config.loadItem(JGui.ListView.FILTER_CURRENTLY_ONLINE)
        filterNotFull = config.loadItem(JGui.ListView.FILTER_NOT_FULL)
    }

}