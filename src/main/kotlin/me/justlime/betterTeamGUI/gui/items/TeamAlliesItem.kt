package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamAlliesItem : BaseGuiItem(JFiles.ALLIES_VIEW.filename) {

    var allyItem: GuiItem? = null
    var allyRequestInbox: GuiItem? = null
    var allyRequestItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        allyItem = config.loadItem(JGui.AllyView.ALLY_ITEM)
        allyRequestInbox = config.loadItem(JGui.AllyView.ALLY_REQUEST_INBOX)
        allyRequestItem = config.loadItem(JGui.AllyView.ALLY_REQUEST_ITEM)
    }

}