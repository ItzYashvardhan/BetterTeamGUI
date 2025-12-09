package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamDialogItem {
    // Config handler for leave_view.yml
    val config = ConfigHandler(JFiles.DIALOG_VIEW.filename)

    var leaveSetting = config.loadInventorySetting(JGui.DialogView.LEAVE_VIEW_MAIN)
    var leaveBackground = config.loadItems(JGui.DialogView.LEAVE_VIEW_BACKGROUND)
    var leaveConfirmItem = config.loadItem(JGui.DialogView.LEAVE_CONFIRM_ITEM)
    var leaveCancelItem = config.loadItem(JGui.DialogView.LEAVE_CANCEL_ITEM)

    var disbandSetting = config.loadInventorySetting(JGui.DialogView.DISBAND_VIEW_MAIN)
    var disbandBackground = config.loadItems(JGui.DialogView.DISBAND_VIEW_BACKGROUND)
    var disbandConfirmItem = config.loadItem(JGui.DialogView.DISBAND_CONFIRM_ITEM)
    var disbandCancelItem = config.loadItem(JGui.DialogView.DISBAND_CANCEL_ITEM)

    var deleteHomeSetting = config.loadInventorySetting(JGui.DialogView.DELETE_HOME_VIEW_MAIN)
    var deleteHomeBackground = config.loadItems(JGui.DialogView.DELETE_HOME_VIEW_BACKGROUND)
    var deleteHomeConfirmItem = config.loadItem(JGui.DialogView.DELETE_HOME_CONFIRM_ITEM)
    var deleteHomeCancelItem = config.loadItem(JGui.DialogView.DELETE_HOME_CANCEL_ITEM)

    var updateHomeSetting = config.loadInventorySetting(JGui.DialogView.UPDATE_HOME_VIEW_MAIN)
    var updateHomeBackground = config.loadItems(JGui.DialogView.UPDATE_HOME_VIEW_BACKGROUND)
    var updateHomeConfirmItem = config.loadItem(JGui.DialogView.UPDATE_HOME_CONFIRM_ITEM)
    var updateHomeCancelItem = config.loadItem(JGui.DialogView.UPDATE_HOME_CANCEL_ITEM)

    var promoteToOwnerSetting = config.loadInventorySetting(JGui.DialogView.PROMOTE_TO_OWNER_VIEW_MAIN)
    var promoteToOwnerBackground = config.loadItems(JGui.DialogView.PROMOTE_TO_OWNER_VIEW_BACKGROUND)
    var promoteToOwnerConfirmItem = config.loadItem(JGui.DialogView.PROMOTE_TO_OWNER_CONFIRM_ITEM)
    var promoteToOwnerCancelItem = config.loadItem(JGui.DialogView.PROMOTE_TO_OWNER_CANCEL_ITEM)

    var promoteToAdminSetting = config.loadInventorySetting(JGui.DialogView.PROMOTE_TO_ADMIN_VIEW_MAIN)
    var promoteToAdminBackground = config.loadItems(JGui.DialogView.PROMOTE_TO_ADMIN_VIEW_BACKGROUND)
    var promoteToAdminConfirmItem = config.loadItem(JGui.DialogView.PROMOTE_TO_ADMIN_CONFIRM_ITEM)
    var promoteToAdminCancelItem = config.loadItem(JGui.DialogView.PROMOTE_TO_ADMIN_CANCEL_ITEM)

    var demoteToAdminSetting = config.loadInventorySetting(JGui.DialogView.DEMOTE_TO_ADMIN_VIEW_MAIN)
    var demoteToAdminBackground = config.loadItems(JGui.DialogView.DEMOTE_TO_ADMIN_VIEW_BACKGROUND)
    var demoteToAdminConfirmItem = config.loadItem(JGui.DialogView.DEMOTE_TO_ADMIN_CONFIRM_ITEM)
    var demoteToAdminCancelItem = config.loadItem(JGui.DialogView.DEMOTE_TO_ADMIN_CANCEL_ITEM)


    var demoteToDefaultSetting = config.loadInventorySetting(JGui.DialogView.DEMOTE_TO_DEFAULT_VIEW_MAIN)
    var demoteToDefaultBackground = config.loadItems(JGui.DialogView.DEMOTE_TO_DEFAULT_VIEW_BACKGROUND)
    var demoteToDefaultConfirmItem = config.loadItem(JGui.DialogView.DEMOTE_TO_DEFAULT_CONFIRM_ITEM)
    var demoteToDefaultCancelItem = config.loadItem(JGui.DialogView.DEMOTE_TO_DEFAULT_CANCEL_ITEM)

    var kickSetting = config.loadInventorySetting(JGui.DialogView.KICK_VIEW_MAIN)
    var kickBackground = config.loadItems(JGui.DialogView.KICK_VIEW_BACKGROUND)
    var kickConfirmItem = config.loadItem(JGui.DialogView.KICK_CONFIRM_ITEM)
    var kickCancelItem = config.loadItem(JGui.DialogView.KICK_CANCEL_ITEM)

    var banSetting = config.loadInventorySetting(JGui.DialogView.BAN_VIEW_MAIN)
    var banBackground = config.loadItems(JGui.DialogView.BAN_VIEW_BACKGROUND)
    var banConfirmItem = config.loadItem(JGui.DialogView.BAN_CONFIRM_ITEM)
    var banCancelItem = config.loadItem(JGui.DialogView.BAN_CANCEL_ITEM)

    var neutralSetting = config.loadInventorySetting(JGui.DialogView.NEUTRAL_VIEW_MAIN)
    var neutralBackground = config.loadItems(JGui.DialogView.NEUTRAL_VIEW_BACKGROUND)
    var neutralConfirmItem = config.loadItem(JGui.DialogView.NEUTRAL_CONFIRM_ITEM)
    var neutralCancelItem = config.loadItem(JGui.DialogView.NEUTRAL_CANCEL_ITEM)

    fun reload() {
        config.reload()

        leaveSetting = config.loadInventorySetting(JGui.DialogView.LEAVE_VIEW_MAIN)
        leaveBackground = config.loadItems(JGui.DialogView.LEAVE_VIEW)

        disbandSetting = config.loadInventorySetting(JGui.DialogView.DISBAND_VIEW_MAIN)
        disbandBackground = config.loadItems(JGui.DialogView.DISBAND_VIEW_BACKGROUND)

        deleteHomeSetting = config.loadInventorySetting(JGui.DialogView.DELETE_HOME_VIEW_MAIN)
        deleteHomeBackground = config.loadItems(JGui.DialogView.DELETE_HOME_VIEW_BACKGROUND)

        updateHomeSetting = config.loadInventorySetting(JGui.DialogView.UPDATE_HOME_VIEW_MAIN)
        updateHomeBackground = config.loadItems(JGui.DialogView.UPDATE_HOME_VIEW_BACKGROUND)

        promoteToOwnerSetting = config.loadInventorySetting(JGui.DialogView.PROMOTE_TO_OWNER_VIEW_MAIN)
        promoteToOwnerBackground = config.loadItems(JGui.DialogView.PROMOTE_TO_OWNER_VIEW_BACKGROUND)

        promoteToAdminSetting = config.loadInventorySetting(JGui.DialogView.PROMOTE_TO_ADMIN_VIEW_MAIN)
        promoteToAdminBackground = config.loadItems(JGui.DialogView.PROMOTE_TO_ADMIN_VIEW_BACKGROUND)

        demoteToAdminSetting = config.loadInventorySetting(JGui.DialogView.DEMOTE_TO_ADMIN_VIEW_MAIN)
        demoteToAdminBackground = config.loadItems(JGui.DialogView.DEMOTE_TO_ADMIN_VIEW_BACKGROUND)

        demoteToDefaultSetting = config.loadInventorySetting(JGui.DialogView.DEMOTE_TO_DEFAULT_VIEW_MAIN)
        demoteToDefaultBackground = config.loadItems(JGui.DialogView.DEMOTE_TO_DEFAULT_VIEW_BACKGROUND)

        kickSetting = config.loadInventorySetting(JGui.DialogView.KICK_VIEW_MAIN)
        kickBackground = config.loadItems(JGui.DialogView.KICK_VIEW_BACKGROUND)

        banSetting = config.loadInventorySetting(JGui.DialogView.BAN_VIEW_MAIN)
        banBackground = config.loadItems(JGui.DialogView.BAN_VIEW_BACKGROUND)

        neutralSetting = config.loadInventorySetting(JGui.DialogView.NEUTRAL_VIEW_MAIN)
        neutralBackground = config.loadItems(JGui.DialogView.NEUTRAL_VIEW_BACKGROUND)

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

        neutralConfirmItem = config.loadItem(JGui.DialogView.NEUTRAL_CONFIRM_ITEM)
        neutralCancelItem = config.loadItem(JGui.DialogView.NEUTRAL_CANCEL_ITEM)

    }

}