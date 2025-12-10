package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamButton {

    private val config = ConfigHandler(JFiles.BUTTONS.filename)
    var back = config.loadItem(JGui.TeamButton.BACK)
    var home = config.loadItem(JGui.TeamButton.HOME)
    var next = config.loadItem(JGui.TeamButton.NEXT)
    var prev = config.loadItem(JGui.TeamButton.PREV)
    var noPermission = config.loadItem(JGui.TeamButton.NO_PERMISSION_ITEM)

    fun reload() {
        config.reload()
        back = config.loadItem(JGui.TeamButton.BACK)
        home = config.loadItem(JGui.TeamButton.HOME)
        next = config.loadItem(JGui.TeamButton.NEXT)
        prev = config.loadItem(JGui.TeamButton.PREV)
        noPermission = config.loadItem(JGui.TeamButton.NO_PERMISSION_ITEM)
    }

}