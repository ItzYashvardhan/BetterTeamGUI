package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamSettingItem {
    val config = ConfigHandler(JFiles.SETTING_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    val backSlot = ConfigManager.settingView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.settingView.getInt(JGui.Main.BACK_SLOT))  }
    val homeSlot = ConfigManager.settingView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.settingView.getInt(JGui.Main.HOME_SLOT))  }

    val colorPicker = config.loadItem(JGui.SettingView.COLOR_PICKER)
    val description = config.loadItem(JGui.SettingView.DESCRIPTION)
    val tag = config.loadItem(JGui.SettingView.TAG)
    val statusOpen = config.loadItem(JGui.SettingView.STATUS_OPEN)
    val statusClosed = config.loadItem(JGui.SettingView.STATUS_CLOSED)
    val anchor = config.loadItem(JGui.SettingView.ANCHOR)
    val title = config.loadItem(JGui.SettingView.TITLE)
    val pvp = config.loadItem(JGui.SettingView.PVP)
    val banList = config.loadItem(JGui.SettingView.BAN_LIST)
    val disband = config.loadItem(JGui.SettingView.DISBAND)

    val descriptionTitle = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_TITLE)
    val descriptionLabel = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_LABEL)
    val descriptionInputItem = config.loadItem(JGui.SettingView.DESCRIPTION_INPUT_ITEM)
    val descriptionOutputItem = config.loadItem(JGui.SettingView.DESCRIPTION_OUTPUT_ITEM)

    val tagTitle = ConfigManager.settingView.getString(JGui.SettingView.TAG_TITLE)
    val tagLabel = ConfigManager.settingView.getString(JGui.SettingView.TAG_LABEL)
    val tagInputItem = config.loadItem(JGui.SettingView.TAG_INPUT_ITEM)
    val tagOutputItem = config.loadItem(JGui.SettingView.TAG_OUTPUT_ITEM)

    val titleTitle = ConfigManager.settingView.getString(JGui.SettingView.TITLE_TITLE)
    val titleLabel = ConfigManager.settingView.getString(JGui.SettingView.TITLE_LABEL)
    val titleInputItem = config.loadItem(JGui.SettingView.TITLE_INPUT_ITEM)
    val titleOutputItem = config.loadItem(JGui.SettingView.TITLE_OUTPUT_ITEM)


    fun reload() {
        config.reload()
    }

}