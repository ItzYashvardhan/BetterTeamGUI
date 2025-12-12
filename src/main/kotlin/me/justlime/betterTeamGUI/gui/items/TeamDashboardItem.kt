package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamDashboardItem : BaseGuiItem(JFiles.DASHBOARD_VIEW.filename) {

    var infoItemWithDesc: GuiItem? = null
    var infoItemWithoutDesc: GuiItem? = null
    var homeItem: GuiItem? = null
    var chatItem: GuiItem? = null
    var teamChatItem: GuiItem? = null
    var allyChatItem: GuiItem? = null
    var balanceItem: GuiItem? = null
    var warpItem: GuiItem? = null
    var membersItem: GuiItem? = null
    var enderChestItem: GuiItem? = null
    var allyItem: GuiItem? = null
    var leaveItem: GuiItem? = null
    var listItem: GuiItem? = null
    var settingItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        infoItemWithDesc = config.loadItem(JGui.TeamView.INFO_WITH_DESC)
        infoItemWithoutDesc = config.loadItem(JGui.TeamView.INFO_WITHOUT_DESC)
        homeItem = config.loadItem(JGui.TeamView.HOME)
        chatItem = config.loadItem(JGui.TeamView.GLOBAL_CHAT)
        teamChatItem = config.loadItem(JGui.TeamView.TEAM_CHAT) ?: config.loadItem(JGui.TeamView.GLOBAL_CHAT)
        allyChatItem = config.loadItem(JGui.TeamView.ALLY_CHAT) ?: config.loadItem(JGui.TeamView.GLOBAL_CHAT)
        balanceItem = config.loadItem(JGui.TeamView.BALANCE)
        warpItem = config.loadItem(JGui.TeamView.WARP)
        membersItem = config.loadItem(JGui.TeamView.MEMBERS_GLOBAL)
        enderChestItem = config.loadItem(JGui.TeamView.TEAM_CHEST)
        allyItem = config.loadItem(JGui.TeamView.ALLY)
        leaveItem = config.loadItem(JGui.TeamView.LEAVE)
        listItem = config.loadItem(JGui.TeamView.LIST)
        settingItem = config.loadItem(JGui.TeamView.SETTING)
    }
}