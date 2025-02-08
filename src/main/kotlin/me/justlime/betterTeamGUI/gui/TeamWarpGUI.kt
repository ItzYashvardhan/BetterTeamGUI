package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamWarpGUI(row: Int, title: String) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, row * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val warps = team.warps.get()
        val material = Material.valueOf(Config.TeamWarpItem.item)
        warps.forEach {
            val name = Service.applyColors("&f" + it.name)
            val item = GUIManager.createItem(material, name, mutableListOf(), false)
            inventory.addItem(item)

        }
        val backSlot = Config.TeamWarpItem.backSlot
        val backSlots = Config.TeamWarpItem.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val backSlot = Config.TeamWarpItem.backSlot
        val backSlots = Config.TeamWarpItem.backSlots
        val warpSlots = mutableListOf<Int>()
        val team = Team.getTeam(event.whoClicked.name) ?: return

        if (event.slot in backSlots || event.slot == backSlot) {
            GUIManager.openTeamGUI(event.whoClicked as Player)
            return
        }
        inventory.contents.forEach {
            if (it == null) return@forEach
            val material = it.type
            if (material != Material.valueOf(Config.TeamWarpItem.item)) return@forEach
            warpSlots.add(inventory.contents.indexOf(it))
        }
        when (event.slot) {
            in warpSlots -> {
                val warp = team.warps.get(event.currentItem?.itemMeta?.displayName?.removePrefix("§f"))
                event.isCancelled = true
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    player.teleport(warp.location)
                }, 2)
                GUIManager.closeInventory(player)
            }
        }
        event.isCancelled = true
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}