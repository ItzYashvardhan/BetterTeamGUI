package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JGui
import me.justlime.betterTeamGUI.enums.TeamItem
import net.justlime.limeframegui.impl.ConfigHandler
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem

abstract class BaseGuiItem(private val fileName: String) : TeamItem {

    override lateinit var config: ConfigHandler
    override lateinit var setting: GUISetting
    override lateinit var background: List<GuiItem>

    override var backSlot: List<Int> = emptyList()
    override var homeSlot: List<Int> = emptyList()
    override var prevSlot: Int = 0
    override var nextSlot: Int = 0

    // Initialize data immediately when the object is created
    init {
        refreshCommonData()
    }

    /**
     * handles the redundant logic.
     */
    protected fun refreshCommonData() {
        config = ConfigHandler(fileName)

        // Load the standard settings
        setting = config.loadInventorySetting(JGui.Main.MAIN)
        background = config.loadItems(JGui.Main.BACKGROUND)

        val configuration = config.config

        backSlot = configuration.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(configuration.getInt(JGui.Main.BACK_SLOT, -1)).filter { it != -1 } }
        homeSlot = configuration.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(configuration.getInt(JGui.Main.HOME_SLOT, -1)).filter { it != -1 } }
        prevSlot = configuration.getInt(JGui.Main.PREV_SLOT)
        nextSlot = configuration.getInt(JGui.Main.NEXT_SLOT)
    }

    fun reload() {
        refreshCommonData()
        reloadItems()
    }

    abstract fun reloadItems()
}