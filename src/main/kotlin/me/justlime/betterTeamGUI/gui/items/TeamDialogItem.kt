package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamDialogItem {
    // Config handler for leave_view.yml
    val config = ConfigHandler(JFiles.DIALOG_VIEW.filename)

    // Main GUI settings
    val leaveSetting = config.loadInventorySetting(JGui.DialogView.LEAVE_VIEW_MAIN)
    val leaveBackground = config.loadItems(JGui.DialogView.LEAVE_VIEW)
    val disbandSetting = config.loadInventorySetting(JGui.DialogView.DISBAND_VIEW_MAIN)
    val disbandBackground = config.loadItems(JGui.DialogView.DISBAND_VIEW_BACKGROUND)

    // Specific items for the leave view
    var leaveConfirmItem = config.loadItem(JGui.DialogView.LEAVE_CONFIRM_ITEM)
    var leaveCancelItem = config.loadItem(JGui.DialogView.LEAVE_CANCEL_ITEM)

    // Specific items for the disband view
    var disbandConfirmItem = config.loadItem(JGui.DialogView.DISBAND_CONFIRM_ITEM)
    var disbandCancelItem = config.loadItem(JGui.DialogView.DISBAND_CANCEL_ITEM)

    val deleteHomeSetting = config.loadInventorySetting(JGui.DialogView.DELETE_HOME_VIEW_MAIN)
    val deleteHomeBackground = config.loadItems(JGui.DialogView.DELETE_HOME_VIEW_BACKGROUND)
    var deleteHomeConfirmItem = config.loadItem(JGui.DialogView.DELETE_HOME_CONFIRM_ITEM)
    var deleteHomeCancelItem = config.loadItem(JGui.DialogView.DELETE_HOME_CANCEL_ITEM)

    val updateHomeSetting = config.loadInventorySetting(JGui.DialogView.UPDATE_HOME_VIEW_MAIN)
    val updateHomeBackground = config.loadItems(JGui.DialogView.UPDATE_HOME_VIEW_BACKGROUND)
    var updateHomeConfirmItem = config.loadItem(JGui.DialogView.UPDATE_HOME_CONFIRM_ITEM)
    var updateHomeCancelItem = config.loadItem(JGui.DialogView.UPDATE_HOME_CANCEL_ITEM)


    fun reload() {
        config.reload()
        leaveConfirmItem = config.loadItem(JGui.DialogView.LEAVE_CONFIRM_ITEM)
        leaveCancelItem = config.loadItem(JGui.DialogView.LEAVE_CANCEL_ITEM)

        disbandConfirmItem = config.loadItem(JGui.DialogView.DISBAND_CONFIRM_ITEM)
        disbandCancelItem = config.loadItem(JGui.DialogView.DISBAND_CANCEL_ITEM)

        deleteHomeConfirmItem = config.loadItem(JGui.DialogView.DELETE_HOME_CONFIRM_ITEM)
        deleteHomeCancelItem = config.loadItem(JGui.DialogView.DELETE_HOME_CANCEL_ITEM)

        updateHomeConfirmItem = config.loadItem(JGui.DialogView.UPDATE_HOME_CONFIRM_ITEM)
        updateHomeCancelItem = config.loadItem(JGui.DialogView.UPDATE_HOME_CANCEL_ITEM)

    }

}