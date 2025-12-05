package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamButton {

    private val config = ConfigHandler(JFiles.BUTTONS.filename)
    val back = config.loadItem(JGui.TeamButton.BACK)
    val home = config.loadItem(JGui.TeamButton.HOME)
    val next = config.loadItem(JGui.TeamButton.NEXT)
    val prev = config.loadItem(JGui.TeamButton.PREV)
    val noPermission = config.loadItem(JGui.TeamButton.NO_PERMISSION_ITEM)

}