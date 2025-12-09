package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object ColorPickerItem {
    private var config = ConfigHandler(JFiles.COLORS.filename)
    var setting = config.loadInventorySetting(JGui.Main.SETTING)
    var background = config.loadItems(JGui.Main.BACKGROUND)
    var backSlot = ConfigManager.colorsView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.colorsView.getInt(JGui.Main.BACK_SLOT)) }
    var homeSlot = ConfigManager.colorsView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.colorsView.getInt(JGui.Main.HOME_SLOT)) }

    var aqua = config.loadItem(JGui.ColorPicker.AQUA)
    var black = config.loadItem(JGui.ColorPicker.BLACK)
    var blue = config.loadItem(JGui.ColorPicker.BLUE)
    var darkAqua = config.loadItem(JGui.ColorPicker.DARK_AQUA)
    var darkBlue = config.loadItem(JGui.ColorPicker.DARK_BLUE)
    var darkGray = config.loadItem(JGui.ColorPicker.DARK_GRAY)
    var darkGreen = config.loadItem(JGui.ColorPicker.DARK_GREEN)
    var darkPurple = config.loadItem(JGui.ColorPicker.DARK_PURPLE)
    var darkRed = config.loadItem(JGui.ColorPicker.DARK_RED)
    var gold = config.loadItem(JGui.ColorPicker.GOLD)
    var gray = config.loadItem(JGui.ColorPicker.GRAY)
    var green = config.loadItem(JGui.ColorPicker.GREEN)
    var red = config.loadItem(JGui.ColorPicker.RED)
    var strikethrough = config.loadItem(JGui.ColorPicker.STRIKETHROUGH)
    var white = config.loadItem(JGui.ColorPicker.WHITE)
    var yellow = config.loadItem(JGui.ColorPicker.YELLOW)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.COLORS.filename)
        setting = config.loadInventorySetting(JGui.Main.SETTING)
        background = config.loadItems(JGui.Main.BACKGROUND)
        backSlot = ConfigManager.colorsView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.colorsView.getInt(JGui.Main.BACK_SLOT)) }
        homeSlot = ConfigManager.colorsView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.colorsView.getInt(JGui.Main.HOME_SLOT)) }

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