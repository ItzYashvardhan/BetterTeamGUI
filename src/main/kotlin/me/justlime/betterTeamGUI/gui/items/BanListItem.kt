package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object BanListItem {
    val config = ConfigHandler(JFiles.BAN_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    val backSlot = ConfigManager.banView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.banView.getInt(JGui.Main.BACK_SLOT)) }
    val homeSlot = ConfigManager.banView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.banView.getInt(JGui.Main.HOME_SLOT)) }
    val prevSlot = ConfigManager.banView.getInt(JGui.Main.PREV_SLOT)
    val nextSlot = ConfigManager.banView.getInt(JGui.Main.NEXT_SLOT)

    var bannedPlayerItem = config.loadItem(JGui.BanList.BANNED_PLAYER_ITEM)

    fun reload() {
        config.reload()
        bannedPlayerItem = config.loadItem(JGui.BanList.BANNED_PLAYER_ITEM)
    }

}