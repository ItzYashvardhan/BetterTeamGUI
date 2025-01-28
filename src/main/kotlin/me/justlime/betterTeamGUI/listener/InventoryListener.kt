package me.justlime.betterTeamGUI.listener

import me.justlime.betterTeamGUI.gui.BetterTeamGUIInventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryListener : Listener {

    @EventHandler
    fun onOpen(event: InventoryOpenEvent) {
        val inventory = event.inventory
        val holder = inventory.holder
        if (holder !is BetterTeamGUIInventory) return
            holder.onOpen(event)
            holder.loadInventory(event.player)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inventory = event.inventory
        val holder = inventory.holder
        if (holder !is BetterTeamGUIInventory) return
        holder.onClick(event)

    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val inventory = event.inventory
        val holder = inventory.holder
        if (holder !is BetterTeamGUIInventory) return
        holder.onClose(event)
    }

}