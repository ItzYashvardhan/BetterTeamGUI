package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui

object TeamMemberItem : BaseGuiItem(JFiles.MEMBERS_VIEW.filename) {

    var memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)
    var memberItemNoAdmin = config.loadItem(JGui.MemberView.MEMBER_ITEM_NO_ADMIN)
    var invite = config.loadItem(JGui.MemberView.INVITE)
    var lockedInvite = config.loadItem(JGui.MemberView.LOCKED_INVITE)
    var banList = config.loadItem(JGui.MemberView.BAN_LIST)

    var inviteTitle = ConfigManager.membersView.getString(JGui.MemberView.INVITE_TITLE)
    var inviteLabel = ConfigManager.membersView.getString(JGui.MemberView.INVITE_LABEL)
    var inviteInputItem = config.loadItem(JGui.MemberView.INVITE_INPUT_ITEM)
    var inviteOutputItem = config.loadItem(JGui.MemberView.INVITE_OUTPUT_ITEM)

    init {
        reloadItems()
    }

    override fun reloadItems() {
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