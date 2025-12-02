package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamButton {

    private val config = ConfigHandler(JFiles.CONFIG.filename)
    val back = config.loadItem("back")
    val backSlot = config.loadItem(JGui.Main.SETTING)
}