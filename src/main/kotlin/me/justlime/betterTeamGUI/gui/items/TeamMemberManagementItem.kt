package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamMemberManagementItem : BaseGuiItem(JFiles.MEMBER_MANAGEMENT_VIEW.filename) {
    var playerInfo: GuiItem? = null
    var promoteToOwner: GuiItem? = null
    var promoteToAdmin: GuiItem? = null
    var demoteToAdmin: GuiItem? = null
    var demoteToDefault: GuiItem? = null
    var kick: GuiItem? = null
    var ban: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        playerInfo = config.loadItem(JGui.MemberManagement.PLAYER_INFO)
        promoteToOwner = config.loadItem(JGui.MemberManagement.PROMOTE_TO_OWNER)
        promoteToAdmin = config.loadItem(JGui.MemberManagement.PROMOTE_TO_ADMIN)
        demoteToAdmin = config.loadItem(JGui.MemberManagement.DEMOTE_TO_ADMIN)
        demoteToDefault = config.loadItem(JGui.MemberManagement.DEMOTE_TO_DEFAULT)
        kick = config.loadItem(JGui.MemberManagement.KICK)
        ban = config.loadItem(JGui.MemberManagement.BAN)
    }

}