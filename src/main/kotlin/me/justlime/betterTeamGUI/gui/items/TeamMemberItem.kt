package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamMemberItem : BaseGuiItem(JFiles.MEMBERS_VIEW.filename) {

    var memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)
    var memberItemNoAdmin = config.loadItem(JGui.MemberView.MEMBER_ITEM_NO_ADMIN)
    var lockedInvite = config.loadItem(JGui.MemberView.LOCKED_INVITE)
    var banList = config.loadItem(JGui.MemberView.BAN_LIST)
    var invite: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)
        memberItemNoAdmin = config.loadItem(JGui.MemberView.MEMBER_ITEM_NO_ADMIN)
        invite = config.loadItem(JGui.MemberView.INVITE_BUTTON)
        lockedInvite = config.loadItem(JGui.MemberView.LOCKED_INVITE)
        banList = config.loadItem(JGui.MemberView.BAN_LIST)

    }

}