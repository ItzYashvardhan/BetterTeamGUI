package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamLeaveItem {
    // Config handler for leave_view.yml
    val config = ConfigHandler(JFiles.LEAVE_VIEW.filename)

    // Main GUI settings
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    // Specific items for the leave view
    var confirmItem = config.loadItem(JGui.LeaveView.CONFIRM_ITEM)
    var cancelItem = config.loadItem(JGui.LeaveView.CANCEL_ITEM)

    fun reload() {
        config.reload()
        confirmItem = config.loadItem(JGui.LeaveView.CONFIRM_ITEM)
        cancelItem = config.loadItem(JGui.LeaveView.CANCEL_ITEM)
    }

}