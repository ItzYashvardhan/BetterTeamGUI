package me.justlime.betterTeamGUI.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamCreateGUI(rows: Int, title: String) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, rows * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
    }

    override fun loadInventory(player: Player) {
    }

    override fun onClick(event: InventoryClickEvent) {
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}