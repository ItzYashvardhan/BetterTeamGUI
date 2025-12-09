package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamDashboardItem {

    var config = ConfigHandler(JFiles.TEAM_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.SETTING)
    var background = config.loadItems(JGui.Main.BACKGROUND)

    var infoItemWithDesc = config.loadItem(JGui.TeamView.INFO_WITH_DESC)
    var infoItemWithoutDesc = config.loadItem(JGui.TeamView.INFO_WITHOUT_DESC)
    var homeItem = config.loadItem(JGui.TeamView.HOME)

    var chatItem = config.loadItem(JGui.TeamView.GLOBAL_CHAT)
    var teamChatItem = config.loadItem(JGui.TeamView.TEAM_CHAT) ?: config.loadItem(JGui.TeamView.GLOBAL_CHAT)
    var allyChatItem = config.loadItem(JGui.TeamView.ALLY_CHAT) ?: config.loadItem(JGui.TeamView.GLOBAL_CHAT)
    var balanceItem = config.loadItem(JGui.TeamView.BALANCE)
    var warpItem = config.loadItem(JGui.TeamView.WARP)
    var membersItem = config.loadItem(JGui.TeamView.MEMBERS_GLOBAL)
    var enderChestItem = config.loadItem(JGui.TeamView.TEAM_CHEST)
    var allyItem = config.loadItem(JGui.TeamView.ALLY)
    var leaveItem = config.loadItem(JGui.TeamView.LEAVE)
    var listItem = config.loadItem(JGui.TeamView.LIST)
    var settingItem = config.loadItem(JGui.TeamView.SETTING)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.TEAM_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.SETTING)
        background = config.loadItems(JGui.Main.BACKGROUND)

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