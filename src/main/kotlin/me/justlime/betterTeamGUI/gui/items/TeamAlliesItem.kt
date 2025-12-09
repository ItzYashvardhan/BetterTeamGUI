package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamAlliesItem {
    val config = ConfigHandler(JFiles.ALLIES_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    val backSlot = ConfigManager.alliesView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.alliesView.getInt(JGui.Main.BACK_SLOT)) }
    val homeSlot = ConfigManager.alliesView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.alliesView.getInt(JGui.Main.HOME_SLOT)) }
    val prevSlot = ConfigManager.alliesView.getInt(JGui.Main.PREV_SLOT)
    val nextSlot = ConfigManager.alliesView.getInt(JGui.Main.NEXT_SLOT)

    var allyItem = config.loadItem(JGui.AllyView.ALLY_ITEM)
    var allyRequestInbox = config.loadItem(JGui.AllyView.ALLY_REQUEST_INBOX)
    var allyRequestItem = config.loadItem(JGui.AllyView.ALLY_REQUEST_ITEM)

    fun reload() {
        config.reload()
        allyItem = config.loadItem(JGui.AllyView.ALLY_ITEM)
    }

}