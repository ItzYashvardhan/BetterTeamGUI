package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamWarpItem : BaseGuiItem(JFiles.WARPS_VIEW.filename) {

    var occupiedWarpItem: GuiItem? = null
    var claimableWarpItem: GuiItem? = null
    var noPermissionItem: GuiItem? = null
    var lockedWarpItem: GuiItem? = null

    var setWarpNameTitle = ""
    var setWarpNameLabel = ""
    var setWarpNameInputItem: GuiItem? = null
    var setWarpNameOutputItem: GuiItem? = null

    var enterWarpPasswordTitle = ""
    var enterWarpPasswordLabel = ""
    var enterWarpPasswordInputItem: GuiItem? = null
    var enterWarpPasswordOutputItem: GuiItem? = null

    var enterWarpPasswordToTeleportTitle = ""
    var enterWarpPasswordToTeleportLabel = ""
    var enterWarpPasswordToTeleportInputItem: GuiItem? = null
    var enterWarpPasswordToTeleportOutputItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        occupiedWarpItem = config.loadItem(JGui.WarpView.OCCUPIED_WARP)
        claimableWarpItem = config.loadItem(JGui.WarpView.CLAIMABLE_WARP)
        noPermissionItem = config.loadItem(JGui.WarpView.NO_PERMISSION)
        lockedWarpItem = config.loadItem(JGui.WarpView.LOCKED_WARP)

        setWarpNameTitle = ConfigManager.warpsView.getString(JGui.WarpView.SET_WARP_NAME_TITLE) ?: ""
        setWarpNameLabel = ConfigManager.warpsView.getString(JGui.WarpView.SET_WARP_NAME_LABEL) ?: ""
        setWarpNameInputItem = config.loadItem(JGui.WarpView.SET_WARP_NAME_INPUT_ITEM)
        setWarpNameOutputItem = config.loadItem(JGui.WarpView.SET_WARP_NAME_OUTPUT_ITEM)

        enterWarpPasswordTitle = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TITLE) ?: ""
        enterWarpPasswordLabel = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_LABEL) ?: ""
        enterWarpPasswordInputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_INPUT_ITEM)
        enterWarpPasswordOutputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_OUTPUT_ITEM)

        enterWarpPasswordToTeleportTitle = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_TITLE) ?: ""
        enterWarpPasswordToTeleportLabel = ConfigManager.warpsView.getString(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_LABEL) ?: ""
        enterWarpPasswordToTeleportInputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_INPUT_ITEM)
        enterWarpPasswordToTeleportOutputItem = config.loadItem(JGui.WarpView.ENTER_WARP_PASSWORD_TO_TELEPORT_OUTPUT_ITEM)
    }

}