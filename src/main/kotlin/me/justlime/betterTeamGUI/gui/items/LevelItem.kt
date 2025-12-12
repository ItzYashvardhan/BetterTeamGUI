package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object LevelItem : BaseGuiItem(JFiles.LEVELS_VIEW.filename) {

    var unlockedLevelItem: GuiItem? = null
    var currentLevelItem: GuiItem? = null
    var progressLevelItem: GuiItem? = null
    var progressUnlockableLevelItem: GuiItem? = null
    var lockedLevelItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        unlockedLevelItem = config.loadItem(JGui.Levels.LEVEL_ITEM_DEFAULT_UNLOCKED)
        currentLevelItem = config.loadItem(JGui.Levels.LEVEL_ITEM_DEFAULT_CURRENT)
        progressLevelItem = config.loadItem(JGui.Levels.LEVEL_ITEM_DEFAULT_PROGRESS)
        progressUnlockableLevelItem = config.loadItem(JGui.Levels.LEVEL_ITEM_DEFAULT_PROGRESS_UNLOCKABLE)
        lockedLevelItem = config.loadItem(JGui.Levels.LEVEL_ITEM_DEFAULT_LOCKED)
    }

}