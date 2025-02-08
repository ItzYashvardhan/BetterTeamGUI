package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamLeaveGUI(rows: Int, title: String) : GUIHandler {
    private val inventory: Inventory = Bukkit.createInventory(this, rows * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {

    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val team = Team.getTeam(player.name) ?: return player.closeInventory()
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val confirmSection = Config.TeamLeaveItem.confirm
        val cancelSection = Config.TeamLeaveItem.cancel
        val backSection = Config.backItem
        val backSlot = Config.TeamLeaveItem.backSlot
        val backSlots = Config.TeamLeaveItem.backSlots
        GUIManager.loadItem(confirmSection, inventory, team, mutableListOf(), teamPlayer)
        GUIManager.loadItem(cancelSection, inventory, team, mutableListOf(), teamPlayer)
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val team = Team.getTeam(event.whoClicked.name) ?: return event.whoClicked.closeInventory()
        val teamPlayer = team.getTeamPlayer(event.whoClicked as Player) ?: return event.whoClicked.closeInventory()
        val confirmSection = Config.TeamLeaveItem.confirm
        val cancelSection = Config.TeamLeaveItem.cancel
        val backSection = Config.backItem
        val backSlot = Config.TeamLeaveItem.backSlot
        val backSlots = Config.TeamLeaveItem.backSlots
        val confirmSlot = GUIManager.loadItem(confirmSection, inventory, team, mutableListOf(), teamPlayer)
        val cancelSlot = GUIManager.loadItem(cancelSection, inventory, team, mutableListOf(), teamPlayer)
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
        val command = mutableListOf<String>()
        when (event.slot) {
            in confirmSlot -> {
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    event.whoClicked.closeInventory()
                }, 2)
                command.add("team leave")
            }

            in cancelSlot -> {
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    event.whoClicked.closeInventory()
                }, 2)
            }

            in backSlots, backSlot -> {
                GUIManager.openTeamGUI(event.whoClicked as Player)
            }
        }
        command.forEach {
            Bukkit.dispatchCommand(event.whoClicked, it)
        }
        command.clear()
    }

    override fun onClose(event: InventoryCloseEvent) {

    }

    override fun getInventory(): Inventory {
        return inventory
    }

}