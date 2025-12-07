package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamMemberManagementItem {
    // Main
    val config = ConfigHandler(JFiles.MEMBER_MANAGEMENT_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.SETTING)
    var background = config.loadItems(JGui.Main.BACKGROUND)

    var homeSlot = ConfigManager.memberManagementView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.memberManagementView.getInt(JGui.Main.HOME_SLOT)) }
    var backSlot = ConfigManager.memberManagementView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.memberManagementView.getInt(JGui.Main.BACK_SLOT)) }

    // Items
    var playerInfo = config.loadItem(JGui.MemberManagement.PLAYER_INFO)
    var promoteToOwner = config.loadItem(JGui.MemberManagement.PROMOTE_TO_OWNER)
    var promoteToAdmin = config.loadItem(JGui.MemberManagement.PROMOTE_TO_ADMIN)
    var demoteToAdmin = config.loadItem(JGui.MemberManagement.DEMOTE_TO_ADMIN)
    var demoteToDefault = config.loadItem(JGui.MemberManagement.DEMOTE_TO_DEFAULT)

    var kick = config.loadItem(JGui.MemberManagement.KICK)
    var ban = config.loadItem(JGui.MemberManagement.BAN)

    fun reload() {
        config.reload()
        setting = config.loadInventorySetting(JGui.Main.SETTING)
        background = config.loadItems(JGui.Main.BACKGROUND)
        homeSlot = ConfigManager.memberManagementView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.memberManagementView.getInt(JGui.Main.HOME_SLOT)) }
        backSlot = ConfigManager.memberManagementView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.memberManagementView.getInt(JGui.Main.BACK_SLOT)) }

        playerInfo = config.loadItem(JGui.MemberManagement.PLAYER_INFO)
        demoteToDefault = config.loadItem(JGui.MemberManagement.DEMOTE_TO_DEFAULT)
        promoteToOwner = config.loadItem(JGui.MemberManagement.PROMOTE_TO_OWNER)
        promoteToAdmin = config.loadItem(JGui.MemberManagement.PROMOTE_TO_ADMIN)
        kick = config.loadItem(JGui.MemberManagement.KICK)
        ban = config.loadItem(JGui.MemberManagement.BAN)
    }

}