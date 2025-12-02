package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamWarpItem {
    val config = ConfigHandler(JFiles.WARPS_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val warpItem = config.loadItem(JGui.TeamView.WARP)

    fun reload() {
        config.reload()
        val warpItem = config.loadItem(JGui.TeamView.WARP)
    }

}