package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object InvitedPlayersListItem : BaseGuiItem(JFiles.INVITED_PLAYERS_VIEW.filename) {

    var invitedPlayerItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        invitedPlayerItem = config.loadItem(JGui.InviteList.INVITED_PLAYER_ITEM)
    }

}