package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamViewerItems {
    var config = ConfigHandler(JFiles.TEAM_VIEWER.filename)

    var teamViewerSetting = config.loadInventorySetting(JGui.TeamViewer.MAIN)
    var teamViewerBackground = config.loadItems(JGui.TeamViewer.BACKGROUND)
    var teamViewerBackSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.BACK_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.BACK_SLOT)) }
    var teamViewerHomeSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.HOME_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.HOME_SLOT)) }
    var teamViewerInfoWithDescription = config.loadItem(JGui.TeamViewer.INFO_WITH_DESC)
    var teamViewerInfoWithoutDescription = config.loadItem(JGui.TeamViewer.INFO_WITHOUT_DESC)
    var teamViewerBalance = config.loadItem(JGui.TeamViewer.BALANCE)
    var teamViewerMembers = config.loadItem(JGui.TeamViewer.MEMBERS)
    var teamViewerAllies = config.loadItem(JGui.TeamViewer.ALLIES)

    var teamViewerMembersSetting = config.loadInventorySetting(JGui.TeamViewer.Members.MAIN)
    var teamViewerMembersBackground = config.loadItems(JGui.TeamViewer.Members.BACKGROUND)
    var teamViewerMembersBackSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Members.BACK_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.BACK_SLOT)) }
    var teamViewerMembersHomeSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Members.HOME_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.HOME_SLOT)) }
    var teamViewerMembersPrevSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.PREV_SLOT)
    var teamViewerMembersNextSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.NEXT_SLOT)
    var teamViewerMemberItem = config.loadItem(JGui.TeamViewer.Members.MEMBER_ITEM)

    var teamViewerAlliesSetting = config.loadInventorySetting(JGui.TeamViewer.Allies.MAIN)
    var teamViewerAlliesBackground = config.loadItems(JGui.TeamViewer.Allies.BACKGROUND)
    var teamViewerAlliesBackSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Allies.BACK_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.BACK_SLOT)) }
    var teamViewerAlliesHomeSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Allies.HOME_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.HOME_SLOT)) }
    var teamViewerAlliesPrevSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.PREV_SLOT)
    var teamViewerAlliesNextSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.NEXT_SLOT)
    var teamViewerAllyItem = config.loadItem(JGui.TeamViewer.Allies.ALLY_ITEM)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.TEAM_VIEWER.filename)

        teamViewerSetting = config.loadInventorySetting(JGui.TeamViewer.MAIN)
        teamViewerBackground = config.loadItems(JGui.TeamViewer.BACKGROUND)
        teamViewerBackSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.BACK_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.BACK_SLOT)) }
        teamViewerHomeSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.HOME_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.HOME_SLOT)) }
        teamViewerInfoWithDescription = config.loadItem(JGui.TeamViewer.INFO_WITH_DESC)
        teamViewerInfoWithoutDescription = config.loadItem(JGui.TeamViewer.INFO_WITHOUT_DESC)
        teamViewerBalance = config.loadItem(JGui.TeamViewer.BALANCE)
        teamViewerMembers = config.loadItem(JGui.TeamViewer.MEMBERS)
        teamViewerAllies = config.loadItem(JGui.TeamViewer.ALLIES)

        teamViewerMembersSetting = config.loadInventorySetting(JGui.TeamViewer.Members.MAIN)
        teamViewerMembersBackground = config.loadItems(JGui.TeamViewer.Members.BACKGROUND)
        teamViewerMembersBackSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Members.BACK_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.BACK_SLOT)) }
        teamViewerMembersHomeSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Members.HOME_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.HOME_SLOT)) }
        teamViewerMembersPrevSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.PREV_SLOT)
        teamViewerMembersNextSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Members.NEXT_SLOT)
        teamViewerMemberItem = config.loadItem(JGui.TeamViewer.Members.MEMBER_ITEM)

        teamViewerAlliesSetting = config.loadInventorySetting(JGui.TeamViewer.Allies.MAIN)
        teamViewerAlliesBackground = config.loadItems(JGui.TeamViewer.Allies.BACKGROUND)
        teamViewerAlliesBackSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Allies.BACK_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.BACK_SLOT)) }
        teamViewerAlliesHomeSlot = ConfigManager.teamViewer.getIntegerList(JGui.TeamViewer.Allies.HOME_SLOT).ifEmpty { listOf(ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.HOME_SLOT)) }
        teamViewerAlliesPrevSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.PREV_SLOT)
        teamViewerAlliesNextSlot = ConfigManager.teamViewer.getInt(JGui.TeamViewer.Allies.NEXT_SLOT)
        teamViewerAllyItem = config.loadItem(JGui.TeamViewer.Allies.ALLY_ITEM)

    }
}