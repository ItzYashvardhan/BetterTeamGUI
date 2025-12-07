package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamMemberItem {

    val config = ConfigHandler(JFiles.MEMBERS_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    val next = ConfigManager.membersView.getInt(JGui.Main.NEXT_SLOT)
    val prev = ConfigManager.membersView.getInt(JGui.Main.PREV_SLOT)

    val back = ConfigManager.membersView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.BACK_SLOT)) }
    val home = ConfigManager.membersView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.HOME_SLOT)) }

    val memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)
    val memberItemNoAdmin = config.loadItem(JGui.MemberView.MEMBER_ITEM_NO_ADMIN)
    val invite = config.loadItem(JGui.MemberView.INVITE)
    val lockedInvite = config.loadItem(JGui.MemberView.LOCKED_INVITE)

    val inviteTitle = ConfigManager.membersView.getString(JGui.MemberView.INVITE_TITLE)
    val inviteLabel = ConfigManager.membersView.getString(JGui.MemberView.INVITE_LABEL)
    val inviteInputItem = config.loadItem(JGui.MemberView.INVITE_INPUT_ITEM)
    val inviteOutputItem = config.loadItem(JGui.MemberView.INVITE_OUTPUT_ITEM)

}