package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object InviteViewItem: BaseGuiItem(JFiles.INVITE_VIEW.filename) {
    var inviteItem: GuiItem? = null
    var searchItem: GuiItem? = null
    var searchItemClear: GuiItem? = null
    var invitedListItem: GuiItem? = null
    var playerInvitedItem: GuiItem? = null
    var searchTitle: String = ""
    var searchLabel: String = ""
    var searchInputItem: GuiItem? = null
    var searchOutputItem: GuiItem? = null

    var inviteItemBtn: GuiItem? = null
    var inviteTitle: String = ""
    var inviteLabel: String = ""
    var inviteInputItem: GuiItem? = null
    var inviteOutputItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        inviteItem = config.loadItem(JGui.InviteView.INVITE_ITEM)
        searchItem = config.loadItem(JGui.InviteView.SEARCH_ITEM)
        searchItemClear = config.loadItem(JGui.InviteView.SEARCH_ITEM_CLEAR)
        invitedListItem = config.loadItem(JGui.InviteView.INVITED_LIST_ITEM)
        playerInvitedItem = config.loadItem(JGui.InviteView.PLAYER_INVITED_ITEM)
        searchTitle = ConfigManager.inviteView.getString(JGui.InviteView.SEARCH_TITLE) ?: ""
        searchLabel = ConfigManager.inviteView.getString(JGui.InviteView.SEARCH_LABEL) ?: ""
        searchInputItem = config.loadItem(JGui.InviteView.SEARCH_INPUT_ITEM)
        searchOutputItem = config.loadItem(JGui.InviteView.SEARCH_OUTPUT_ITEM)
        inviteItemBtn = config.loadItem(JGui.InviteView.INVITE)
        inviteTitle = ConfigManager.inviteView.getString(JGui.InviteView.INVITE_TITLE) ?: ""
        inviteLabel = ConfigManager.inviteView.getString(JGui.InviteView.INVITE_LABEL) ?: ""
        inviteInputItem = config.loadItem(JGui.InviteView.INVITE_INPUT_ITEM)
        inviteOutputItem = config.loadItem(JGui.InviteView.INVITE_OUTPUT_ITEM)

    }


}