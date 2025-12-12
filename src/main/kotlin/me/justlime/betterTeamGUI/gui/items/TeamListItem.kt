package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamListItem : BaseGuiItem(JFiles.LIST_VIEW.filename) {
    var teamItemWithDescription: GuiItem? = null
    var teamItemWithoutDescription: GuiItem? = null
    var teamItemWithDescriptionNoTeam: GuiItem? = null
    var teamItemWithoutDescriptionNoTeam: GuiItem? = null

    var sortOrderAsc: GuiItem? = null
    var sortOrderDesc: GuiItem? = null
    var sortTypeMoney: GuiItem? = null
    var sortTypeScore: GuiItem? = null
    var sortTypeLevel: GuiItem? = null
    var sortTypeMembers: GuiItem? = null

    var filterDefault: GuiItem? = null
    var filterOpenOnly: GuiItem? = null
    var filterCurrentlyOnline: GuiItem? = null
    var filterNotFull: GuiItem? = null

    var searchItem: GuiItem? = null
    var searchTitle: String = ""
    var searchLabel: String = ""
    var searchInputItem: GuiItem? = null
    var searchOutputItem: GuiItem? = null

    var invitationItem: GuiItem? = null
    var noInvitationItem: GuiItem? = null
    var invitationTeamItem: GuiItem? = null

    var createTeamItem: GuiItem? = null
    var createTeamTitle: String = ""
    var createTeamLabel: String = ""
    var createTeamInputItem: GuiItem? = null
    var createTeamOutputItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        teamItemWithDescription = config.loadItem(JGui.ListView.TEAM_ITEM_WITH_DESC)
        teamItemWithoutDescription = config.loadItem(JGui.ListView.TEAM_ITEM_WITHOUT_DESC)
        teamItemWithDescriptionNoTeam = config.loadItem(JGui.ListView.TEAM_ITEM_WITH_DESC_NO_TEAM)
        teamItemWithoutDescriptionNoTeam = config.loadItem(JGui.ListView.TEAM_ITEM_WITHOUT_DESC_NO_TEAM)

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

        invitationItem = config.loadItem(JGui.ListView.INVITATION_ITEM)
        noInvitationItem = config.loadItem(JGui.ListView.NO_INVITATION_ITEM)
        invitationTeamItem = config.loadItem(JGui.ListView.INVITATION_TEAM_ITEM)

        createTeamItem = config.loadItem(JGui.ListView.CREATE_TEAM_ITEM)
        createTeamTitle = ConfigManager.listView.getString(JGui.ListView.CREATE_TEAM_TITLE) ?: ""
        createTeamLabel = ConfigManager.listView.getString(JGui.ListView.CREATE_TEAM_LABEL) ?: ""
        createTeamInputItem = config.loadItem(JGui.ListView.CREATE_TEAM_INPUT_ITEM)
        createTeamOutputItem = config.loadItem(JGui.ListView.CREATE_TEAM_OUTPUT_ITEM)
    }

}