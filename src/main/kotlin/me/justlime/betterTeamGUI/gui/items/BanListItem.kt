package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object BanListItem {
    var config = ConfigHandler(JFiles.BAN_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.MAIN)
    var background = config.loadItems(JGui.Main.BACKGROUND)
    var backSlot = ConfigManager.banView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.banView.getInt(JGui.Main.BACK_SLOT)) }
    var homeSlot = ConfigManager.banView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.banView.getInt(JGui.Main.HOME_SLOT)) }
    var prevSlot = ConfigManager.banView.getInt(JGui.Main.PREV_SLOT)
    var nextSlot = ConfigManager.banView.getInt(JGui.Main.NEXT_SLOT)

    var bannedPlayerItem = config.loadItem(JGui.BanList.BANNED_PLAYER_ITEM)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.BAN_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.MAIN)
        background = config.loadItems(JGui.Main.BACKGROUND)
        backSlot = ConfigManager.banView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.banView.getInt(JGui.Main.BACK_SLOT)) }
        homeSlot = ConfigManager.banView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.banView.getInt(JGui.Main.HOME_SLOT)) }
        prevSlot = ConfigManager.banView.getInt(JGui.Main.PREV_SLOT)
        nextSlot = ConfigManager.banView.getInt(JGui.Main.NEXT_SLOT)

        bannedPlayerItem = config.loadItem(JGui.BanList.BANNED_PLAYER_ITEM)

    }

}