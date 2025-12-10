package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamMemberItem {

    var config = ConfigHandler(JFiles.MEMBERS_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.MAIN)
    var background = config.loadItems(JGui.Main.BACKGROUND)

    var next = ConfigManager.membersView.getInt(JGui.Main.NEXT_SLOT)
    var prev = ConfigManager.membersView.getInt(JGui.Main.PREV_SLOT)
    var back = ConfigManager.membersView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.BACK_SLOT)) }
    var home = ConfigManager.membersView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.HOME_SLOT)) }

    var memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)
    var memberItemNoAdmin = config.loadItem(JGui.MemberView.MEMBER_ITEM_NO_ADMIN)
    var invite = config.loadItem(JGui.MemberView.INVITE)
    var lockedInvite = config.loadItem(JGui.MemberView.LOCKED_INVITE)
    var banList = config.loadItem(JGui.MemberView.BAN_LIST)

    var inviteTitle = ConfigManager.membersView.getString(JGui.MemberView.INVITE_TITLE)
    var inviteLabel = ConfigManager.membersView.getString(JGui.MemberView.INVITE_LABEL)
    var inviteInputItem = config.loadItem(JGui.MemberView.INVITE_INPUT_ITEM)
    var inviteOutputItem = config.loadItem(JGui.MemberView.INVITE_OUTPUT_ITEM)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.MEMBERS_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.MAIN)
        background = config.loadItems(JGui.Main.BACKGROUND)

        next = ConfigManager.membersView.getInt(JGui.Main.NEXT_SLOT)
        prev = ConfigManager.membersView.getInt(JGui.Main.PREV_SLOT)
        back = ConfigManager.membersView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.BACK_SLOT)) }
        home = ConfigManager.membersView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.HOME_SLOT)) }

        memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)
        memberItemNoAdmin = config.loadItem(JGui.MemberView.MEMBER_ITEM_NO_ADMIN)
        invite = config.loadItem(JGui.MemberView.INVITE)
        lockedInvite = config.loadItem(JGui.MemberView.LOCKED_INVITE)
        banList = config.loadItem(JGui.MemberView.BAN_LIST)

        inviteTitle = ConfigManager.membersView.getString(JGui.MemberView.INVITE_TITLE)
        inviteLabel = ConfigManager.membersView.getString(JGui.MemberView.INVITE_LABEL)
        inviteInputItem = config.loadItem(JGui.MemberView.INVITE_INPUT_ITEM)
        inviteOutputItem = config.loadItem(JGui.MemberView.INVITE_OUTPUT_ITEM)
    }

}