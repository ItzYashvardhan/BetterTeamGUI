package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object TeamSettingItem : BaseGuiItem(JFiles.SETTING_VIEW.filename) {

    var colorPicker: GuiItem? = null
    var description: GuiItem? = null
    var tag: GuiItem? = null
    var statusOpen: GuiItem? = null
    var statusClosed: GuiItem? = null
    var anchor: GuiItem? = null
    var title: GuiItem? = null
    var pvp: GuiItem? = null
    var disband: GuiItem? = null
    var rename: GuiItem? = null

    var descriptionTitle = ""
    var descriptionLabel = ""
    var descriptionInputItem: GuiItem? = null
    var descriptionOutputItem: GuiItem? = null

    var tagTitle = ""
    var tagLabel = ""
    var tagInputItem: GuiItem? = null
    var tagOutputItem: GuiItem? = null

    var titleTitle = ""
    var titleLabel = ""
    var titleInputItem: GuiItem? = null
    var titleOutputItem: GuiItem? = null

    var renameTitle = ""
    var renameLabel = ""
    var renameInputItem: GuiItem? = null
    var renameOutputItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
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

        descriptionTitle = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_TITLE) ?: ""
        descriptionLabel = ConfigManager.settingView.getString(JGui.SettingView.DESCRIPTION_LABEL) ?: ""
        descriptionInputItem = config.loadItem(JGui.SettingView.DESCRIPTION_INPUT_ITEM)
        descriptionOutputItem = config.loadItem(JGui.SettingView.DESCRIPTION_OUTPUT_ITEM)

        tagTitle = ConfigManager.settingView.getString(JGui.SettingView.TAG_TITLE) ?: ""
        tagLabel = ConfigManager.settingView.getString(JGui.SettingView.TAG_LABEL) ?: ""
        tagInputItem = config.loadItem(JGui.SettingView.TAG_INPUT_ITEM)
        tagOutputItem = config.loadItem(JGui.SettingView.TAG_OUTPUT_ITEM)

        titleTitle = ConfigManager.settingView.getString(JGui.SettingView.TITLE_TITLE) ?: ""
        titleLabel = ConfigManager.settingView.getString(JGui.SettingView.TITLE_LABEL) ?: ""
        titleInputItem = config.loadItem(JGui.SettingView.TITLE_INPUT_ITEM)
        titleOutputItem = config.loadItem(JGui.SettingView.TITLE_OUTPUT_ITEM)

        renameTitle = ConfigManager.settingView.getString(JGui.SettingView.RENAME_TITLE) ?: ""
        renameLabel = ConfigManager.settingView.getString(JGui.SettingView.RENAME_LABEL) ?: ""
        renameInputItem = config.loadItem(JGui.SettingView.RENAME_INPUT_ITEM)
        renameOutputItem = config.loadItem(JGui.SettingView.RENAME_OUTPUT_ITEM)
    }

}