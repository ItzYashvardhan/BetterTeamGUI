package me.justlime.betterTeamGUI.models.cached

import net.justlime.limeframegui.models.GuiItem

data class CachedGui(
    val id: String,
    val title: String,
    val rows: Int,
    val background: List<GuiItem>,
    val buttons: Map<Int, CachedButton> // Map of Slot -> Button
)