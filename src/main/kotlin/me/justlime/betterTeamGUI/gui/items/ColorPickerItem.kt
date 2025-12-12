package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object ColorPickerItem : BaseGuiItem(JFiles.COLORS.filename) {

    var aqua: GuiItem? = null
    var black: GuiItem? = null
    var blue: GuiItem? = null
    var darkAqua: GuiItem? = null
    var darkBlue: GuiItem? = null
    var darkGray: GuiItem? = null
    var darkGreen: GuiItem? = null
    var darkPurple: GuiItem? = null
    var darkRed: GuiItem? = null
    var gold: GuiItem? = null
    var gray: GuiItem? = null
    var green: GuiItem? = null
    var red: GuiItem? = null
    var strikethrough: GuiItem? = null
    var white: GuiItem? = null
    var yellow: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        aqua = config.loadItem(JGui.ColorPicker.AQUA)
        black = config.loadItem(JGui.ColorPicker.BLACK)
        blue = config.loadItem(JGui.ColorPicker.BLUE)
        darkAqua = config.loadItem(JGui.ColorPicker.DARK_AQUA)
        darkBlue = config.loadItem(JGui.ColorPicker.DARK_BLUE)
        darkGray = config.loadItem(JGui.ColorPicker.DARK_GRAY)
        darkGreen = config.loadItem(JGui.ColorPicker.DARK_GREEN)
        darkPurple = config.loadItem(JGui.ColorPicker.DARK_PURPLE)
        darkRed = config.loadItem(JGui.ColorPicker.DARK_RED)
        gold = config.loadItem(JGui.ColorPicker.GOLD)
        gray = config.loadItem(JGui.ColorPicker.GRAY)
        green = config.loadItem(JGui.ColorPicker.GREEN)
        red = config.loadItem(JGui.ColorPicker.RED)
        strikethrough = config.loadItem(JGui.ColorPicker.STRIKETHROUGH)
        white = config.loadItem(JGui.ColorPicker.WHITE)
        yellow = config.loadItem(JGui.ColorPicker.YELLOW)
    }

}