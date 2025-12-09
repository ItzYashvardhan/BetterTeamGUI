package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamSettingItem {
    var config = ConfigHandler(JFiles.SETTING_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.SETTING)
    var background = config.loadItems(JGui.Main.BACKGROUND)

    var backSlot = ConfigManager.settingView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.settingView.getInt(JGui.Main.BACK_SLOT))  }
    var homeSlot = ConfigManager.settingView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.settingView.getInt(JGui.Main.HOME_SLOT))  }

    var colorPicker = config.loadItem(JGui.SettingView.COLOR_PICKER)
    var description = config.loadItem(JGui.SettingView.DESCRIPTION)
    var tag = config.loadItem(JGui.SettingView.TAG)
    var statusOpen = config.loadItem(JGui.SettingView.STATUS_OPEN)
    var statusClosed = config.loadItem(JGui.SettingView.STATUS_CLOSED)
    var anchor = config.loadItem(JGui.SettingView.ANCHOR)
    var title = config.loadItem(JGui.SettingView.TITLE)
    var pvp = config.loadItem(JGui.SettingView.PVP)
    var disband = config.loadItem(JGui.SettingView.DISBAND)
    var rename = config.loadItem(JGui.SettingView.RENAME)

    var descriptionTitle = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_TITLE)
    var descriptionLabel = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_LABEL)
    var descriptionInputItem = config.loadItem(JGui.SettingView.DESCRIPTION_INPUT_ITEM)
    var descriptionOutputItem = config.loadItem(JGui.SettingView.DESCRIPTION_OUTPUT_ITEM)

    var tagTitle = ConfigManager.settingView.getString(JGui.SettingView.TAG_TITLE)
    var tagLabel = ConfigManager.settingView.getString(JGui.SettingView.TAG_LABEL)
    var tagInputItem = config.loadItem(JGui.SettingView.TAG_INPUT_ITEM)
    var tagOutputItem = config.loadItem(JGui.SettingView.TAG_OUTPUT_ITEM)

    var titleTitle = ConfigManager.settingView.getString(JGui.SettingView.TITLE_TITLE)
    var titleLabel = ConfigManager.settingView.getString(JGui.SettingView.TITLE_LABEL)
    var titleInputItem = config.loadItem(JGui.SettingView.TITLE_INPUT_ITEM)
    var titleOutputItem = config.loadItem(JGui.SettingView.TITLE_OUTPUT_ITEM)

    var renameTitle = ConfigManager.settingView.getString(JGui.SettingView.RENAME_TITLE)
    var renameLabel = ConfigManager.settingView.getString(JGui.SettingView.RENAME_LABEL)
    var renameInputItem = config.loadItem(JGui.SettingView.RENAME_INPUT_ITEM)
    var renameOutputItem = config.loadItem(JGui.SettingView.RENAME_OUTPUT_ITEM)



    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.SETTING_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.SETTING)
        background = config.loadItems(JGui.Main.BACKGROUND)

        backSlot = ConfigManager.settingView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.settingView.getInt(JGui.Main.BACK_SLOT))  }
        homeSlot = ConfigManager.settingView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.settingView.getInt(JGui.Main.HOME_SLOT))  }

        colorPicker = config.loadItem(JGui.SettingView.COLOR_PICKER)
        description = config.loadItem(JGui.SettingView.DESCRIPTION)
        tag = config.loadItem(JGui.SettingView.TAG)
        statusOpen = config.loadItem(JGui.SettingView.STATUS_OPEN)
        statusClosed = config.loadItem(JGui.SettingView.STATUS_CLOSED)
        anchor = config.loadItem(JGui.SettingView.ANCHOR)
        title = config.loadItem(JGui.SettingView.TITLE)
        pvp = config.loadItem(JGui.SettingView.PVP)
        disband = config.loadItem(JGui.SettingView.DISBAND)
        rename = config.loadItem(JGui.SettingView.RENAME)

        descriptionTitle = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_TITLE)
        descriptionLabel = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_LABEL)
        descriptionInputItem = config.loadItem(JGui.SettingView.DESCRIPTION_INPUT_ITEM)
        descriptionOutputItem = config.loadItem(JGui.SettingView.DESCRIPTION_OUTPUT_ITEM)

        tagTitle = ConfigManager.settingView.getString(JGui.SettingView.TAG_TITLE)
        tagLabel = ConfigManager.settingView.getString(JGui.SettingView.TAG_LABEL)
        tagInputItem = config.loadItem(JGui.SettingView.TAG_INPUT_ITEM)
        tagOutputItem = config.loadItem(JGui.SettingView.TAG_OUTPUT_ITEM)

        titleTitle = ConfigManager.settingView.getString(JGui.SettingView.TITLE_TITLE)
        titleLabel = ConfigManager.settingView.getString(JGui.SettingView.TITLE_LABEL)
        titleInputItem = config.loadItem(JGui.SettingView.TITLE_INPUT_ITEM)
        titleOutputItem = config.loadItem(JGui.SettingView.TITLE_OUTPUT_ITEM)

        renameTitle = ConfigManager.settingView.getString(JGui.SettingView.RENAME_TITLE)
        renameLabel = ConfigManager.settingView.getString(JGui.SettingView.RENAME_LABEL)
        renameInputItem = config.loadItem(JGui.SettingView.RENAME_INPUT_ITEM)
        renameOutputItem = config.loadItem(JGui.SettingView.RENAME_OUTPUT_ITEM)
    }

}