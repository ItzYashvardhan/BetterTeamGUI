package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamButton {

    private val config = ConfigHandler(JFiles.BUTTONS.filename)
    val back = config.loadItem("back")
    val home = config.loadItem("home")
    val next = config.loadItem("next")
    val prev = config.loadItem("prev")

}