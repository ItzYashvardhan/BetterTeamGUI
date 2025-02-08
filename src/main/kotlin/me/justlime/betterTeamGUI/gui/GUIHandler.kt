package me.justlime.betterTeamGUI.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.InventoryHolder

interface GUIHandler : InventoryHolder {
    fun onOpen(event: InventoryOpenEvent)
    fun loadInventory(player: Player)
    fun onClick(event: InventoryClickEvent)
    fun onClose(event: InventoryCloseEvent)
    fun onAnvilRename(event: PrepareAnvilEvent) {}
}