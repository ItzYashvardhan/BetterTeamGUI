package me.justlime.betterTeamGUI.enums

import net.justlime.limeframegui.impl.ConfigHandler
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem

interface TeamItem {
    val config: ConfigHandler
    var setting: GUISetting
    var background: List<GuiItem>
    var backSlot: List<Int>
    var homeSlot: List<Int>
    var prevSlot: Int
    var nextSlot: Int

}