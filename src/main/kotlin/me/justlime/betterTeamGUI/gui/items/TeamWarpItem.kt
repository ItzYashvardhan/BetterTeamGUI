package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamWarpItem {
    val config = ConfigHandler(JFiles.WARPS_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    var occupiedWarpItem = config.loadItem(JGui.WarpView.OCCUPIED_WARP)
    var claimableWarpItem = config.loadItem(JGui.WarpView.CLAIMABLE_WARP)
    var noPermissionItem = config.loadItem(JGui.WarpView.NO_PERMISSION)

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
        occupiedWarpItem = config.loadItem(JGui.WarpView.OCCUPIED_WARP)
        claimableWarpItem = config.loadItem(JGui.WarpView.CLAIMABLE_WARP)
        noPermissionItem = config.loadItem(JGui.WarpView.NO_PERMISSION)
    }

}