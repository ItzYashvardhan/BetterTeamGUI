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

    val promoteToOwnerSetting = config.loadInventorySetting(JGui.DialogView.PROMOTE_TO_OWNER_VIEW_MAIN)
    val promoteToOwnerBackground = config.loadItems(JGui.DialogView.PROMOTE_TO_OWNER_VIEW_BACKGROUND)
    var promoteToOwnerConfirmItem = config.loadItem(JGui.DialogView.PROMOTE_TO_OWNER_CONFIRM_ITEM)
    var promoteToOwnerCancelItem = config.loadItem(JGui.DialogView.PROMOTE_TO_OWNER_CANCEL_ITEM)

    val promoteToAdminSetting = config.loadInventorySetting(JGui.DialogView.PROMOTE_TO_ADMIN_VIEW_MAIN)
    val promoteToAdminBackground = config.loadItems(JGui.DialogView.PROMOTE_TO_ADMIN_VIEW_BACKGROUND)
    var promoteToAdminConfirmItem = config.loadItem(JGui.DialogView.PROMOTE_TO_ADMIN_CONFIRM_ITEM)
    var promoteToAdminCancelItem = config.loadItem(JGui.DialogView.PROMOTE_TO_ADMIN_CANCEL_ITEM)

    val demoteToAdminSetting = config.loadInventorySetting(JGui.DialogView.DEMOTE_TO_ADMIN_VIEW_MAIN)
    val demoteToAdminBackground = config.loadItems(JGui.DialogView.DEMOTE_TO_ADMIN_VIEW_BACKGROUND)
    var demoteToAdminConfirmItem = config.loadItem(JGui.DialogView.DEMOTE_TO_ADMIN_CONFIRM_ITEM)
    var demoteToAdminCancelItem = config.loadItem(JGui.DialogView.DEMOTE_TO_ADMIN_CANCEL_ITEM)


    val demoteToDefaultSetting = config.loadInventorySetting(JGui.DialogView.DEMOTE_TO_DEFAULT_VIEW_MAIN)
    val demoteToDefaultBackground = config.loadItems(JGui.DialogView.DEMOTE_TO_DEFAULT_VIEW_BACKGROUND)
    var demoteToDefaultConfirmItem = config.loadItem(JGui.DialogView.DEMOTE_TO_DEFAULT_CONFIRM_ITEM)
    var demoteToDefaultCancelItem = config.loadItem(JGui.DialogView.DEMOTE_TO_DEFAULT_CANCEL_ITEM)

    val kickSetting = config.loadInventorySetting(JGui.DialogView.KICK_VIEW_MAIN)
    val kickBackground = config.loadItems(JGui.DialogView.KICK_VIEW_BACKGROUND)
    var kickConfirmItem = config.loadItem(JGui.DialogView.KICK_CONFIRM_ITEM)
    var kickCancelItem = config.loadItem(JGui.DialogView.KICK_CANCEL_ITEM)

    val banSetting = config.loadInventorySetting(JGui.DialogView.BAN_VIEW_MAIN)
    val banBackground = config.loadItems(JGui.DialogView.BAN_VIEW_BACKGROUND)
    var banConfirmItem = config.loadItem(JGui.DialogView.BAN_CONFIRM_ITEM)
    var banCancelItem = config.loadItem(JGui.DialogView.BAN_CANCEL_ITEM)



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

        promoteToOwnerConfirmItem = config.loadItem(JGui.DialogView.PROMOTE_TO_OWNER_CONFIRM_ITEM)
        promoteToOwnerCancelItem = config.loadItem(JGui.DialogView.PROMOTE_TO_OWNER_CANCEL_ITEM)

        promoteToAdminConfirmItem = config.loadItem(JGui.DialogView.PROMOTE_TO_ADMIN_CONFIRM_ITEM)
        promoteToAdminCancelItem = config.loadItem(JGui.DialogView.PROMOTE_TO_ADMIN_CANCEL_ITEM)

        demoteToDefaultConfirmItem = config.loadItem(JGui.DialogView.DEMOTE_TO_DEFAULT_CONFIRM_ITEM)
        demoteToDefaultCancelItem = config.loadItem(JGui.DialogView.DEMOTE_TO_DEFAULT_CANCEL_ITEM)

        kickConfirmItem = config.loadItem(JGui.DialogView.KICK_CONFIRM_ITEM)
        kickCancelItem = config.loadItem(JGui.DialogView.KICK_CANCEL_ITEM)

        banConfirmItem = config.loadItem(JGui.DialogView.BAN_CONFIRM_ITEM)
        banCancelItem = config.loadItem(JGui.DialogView.BAN_CANCEL_ITEM)


    }

}