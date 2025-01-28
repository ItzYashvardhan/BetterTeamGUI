package me.justlime.betterTeamGUI.gui

import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.InventoryHolder

interface BetterTeamGUIInventory : InventoryHolder {
    fun onOpen(event: InventoryOpenEvent)
    fun loadInventory(player: HumanEntity)
    fun onClick(event: InventoryClickEvent)
    fun onClose(event: InventoryCloseEvent)
}