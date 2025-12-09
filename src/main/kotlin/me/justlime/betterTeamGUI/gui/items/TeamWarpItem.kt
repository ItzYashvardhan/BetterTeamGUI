package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamWarpItem {
    var config = ConfigHandler(JFiles.WARPS_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.SETTING)
    var background = config.loadItems(JGui.Main.BACKGROUND)
    var backSlot = ConfigManager.warpsView.getInt(JGui.Main.BACK_SLOT)
    var homeSlot = ConfigManager.warpsView.getInt(JGui.Main.HOME_SLOT)

    var occupiedWarpItem = config.loadItem(JGui.WarpView.OCCUPIED_WARP)
    var claimableWarpItem = config.loadItem(JGui.WarpView.CLAIMABLE_WARP)
    var noPermissionItem = config.loadItem(JGui.WarpView.NO_PERMISSION)
    var lockedWarpItem = config.loadItem(JGui.WarpView.LOCKED_WARP)

    var setWarpNameTitle = ConfigManager.warpsView.getString(JGui.WarpView.SET_WARP_NAME_TITLE)
    var setWarpNameLabel = ConfigManager.warpsView.getString(JGui.WarpView.SET_WARP_NAME_LABEL)
    var setWarpNameInputItem = config.loadItem(JGui.WarpView.SET_WARP_NAME_INPUT_ITEM)
    var setWarpNameOutputItem = config.loadItem(JGui.WarpView.SET_WARP_NAME_OUTPUT_ITEM)

    var enterWarpPasswordTitle = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TITLE)
    var enterWarpPasswordLabel = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_LABEL)
    var enterWarpPasswordInputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_INPUT_ITEM)
    var enterWarpPasswordOutputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_OUTPUT_ITEM)

    var enterWarpPasswordToTeleportTitle = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_TITLE)
    var enterWarpPasswordToTeleportLabel = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_LABEL)
    var enterWarpPasswordToTeleportInputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_INPUT_ITEM)
    var enterWarpPasswordToTeleportOutputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_OUTPUT_ITEM)


    fun reload() {
        config.reload()

        config = ConfigHandler(JFiles.WARPS_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.SETTING)
        background = config.loadItems(JGui.Main.BACKGROUND)
        backSlot = ConfigManager.warpsView.getInt(JGui.Main.BACK_SLOT)
        homeSlot = ConfigManager.warpsView.getInt(JGui.Main.HOME_SLOT)

        occupiedWarpItem = config.loadItem(JGui.WarpView.OCCUPIED_WARP)
        claimableWarpItem = config.loadItem(JGui.WarpView.CLAIMABLE_WARP)
        noPermissionItem = config.loadItem(JGui.WarpView.NO_PERMISSION)
        lockedWarpItem = config.loadItem(JGui.WarpView.LOCKED_WARP)

        setWarpNameTitle = ConfigManager.warpsView.getString(JGui.WarpView.SET_WARP_NAME_TITLE)
        setWarpNameLabel = ConfigManager.warpsView.getString(JGui.WarpView.SET_WARP_NAME_LABEL)
        setWarpNameInputItem = config.loadItem(JGui.WarpView.SET_WARP_NAME_INPUT_ITEM)
        setWarpNameOutputItem = config.loadItem(JGui.WarpView.SET_WARP_NAME_OUTPUT_ITEM)

        enterWarpPasswordTitle = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TITLE)
        enterWarpPasswordLabel = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_LABEL)
        enterWarpPasswordInputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_INPUT_ITEM)
        enterWarpPasswordOutputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_OUTPUT_ITEM)

        enterWarpPasswordToTeleportTitle = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_TITLE)
        enterWarpPasswordToTeleportLabel = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_LABEL)
        enterWarpPasswordToTeleportInputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_INPUT_ITEM)
        enterWarpPasswordToTeleportOutputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_OUTPUT_ITEM)
    }

}