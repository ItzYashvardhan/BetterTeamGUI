package me.justlime.betterTeamGUI.gui

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object GUIManager {

    fun insertBackground(inventory: Inventory) {
        val item = Material.WHITE_STAINED_GLASS_PANE
        val itemStack = ItemStack(item)
        val itemMeta = itemStack.itemMeta.apply {
            this?.itemFlags?.clear()
            this?.isHideTooltip = true
        }
        itemStack.itemMeta = itemMeta
        for (i in 0 until inventory.size) {
            if (i in 0..8 || i >= inventory.size - 9 || i % 9 == 0 || (i + 1) % 9 == 0) {
                inventory.setItem(i, itemStack)
            }
        }


    }

}