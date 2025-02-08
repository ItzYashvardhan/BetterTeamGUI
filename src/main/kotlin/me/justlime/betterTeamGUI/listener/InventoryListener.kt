package me.justlime.betterTeamGUI.listener

import me.justlime.betterTeamGUI.gui.GUIHandler
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.PrepareAnvilEvent

class InventoryListener : Listener {

    @EventHandler
    fun onOpen(event: InventoryOpenEvent) {
        val inventory = event.inventory
        val holder = inventory.holder
        if (holder !is GUIHandler) return
        if (event.player !is Player) return
        holder.onOpen(event)
        holder.loadInventory(event.player as Player)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inventory = event.inventory
        val holder = inventory.holder
        if (holder !is GUIHandler) return
        holder.onClick(event)

    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val inventory = event.inventory
        val holder = inventory.holder
        if (holder !is GUIHandler) return
        holder.onClose(event)
    }
}