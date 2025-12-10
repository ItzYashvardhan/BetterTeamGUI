package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamAlliesItem {
    var config = ConfigHandler(JFiles.ALLIES_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.MAIN)
    var background = config.loadItems(JGui.Main.BACKGROUND)

    var backSlot = ConfigManager.alliesView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.alliesView.getInt(JGui.Main.BACK_SLOT)) }
    var homeSlot = ConfigManager.alliesView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.alliesView.getInt(JGui.Main.HOME_SLOT)) }
    var prevSlot = ConfigManager.alliesView.getInt(JGui.Main.PREV_SLOT)
    var nextSlot = ConfigManager.alliesView.getInt(JGui.Main.NEXT_SLOT)

    var allyItem = config.loadItem(JGui.AllyView.ALLY_ITEM)
    var allyRequestInbox = config.loadItem(JGui.AllyView.ALLY_REQUEST_INBOX)
    var allyRequestItem = config.loadItem(JGui.AllyView.ALLY_REQUEST_ITEM)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.ALLIES_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.MAIN)
        background = config.loadItems(JGui.Main.BACKGROUND)

        backSlot = ConfigManager.alliesView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.alliesView.getInt(JGui.Main.BACK_SLOT)) }
        homeSlot = ConfigManager.alliesView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.alliesView.getInt(JGui.Main.HOME_SLOT)) }
        prevSlot = ConfigManager.alliesView.getInt(JGui.Main.PREV_SLOT)
        nextSlot = ConfigManager.alliesView.getInt(JGui.Main.NEXT_SLOT)

        allyItem = config.loadItem(JGui.AllyView.ALLY_ITEM)
        allyRequestInbox = config.loadItem(JGui.AllyView.ALLY_REQUEST_INBOX)
        allyRequestItem = config.loadItem(JGui.AllyView.ALLY_REQUEST_ITEM)

    }

}