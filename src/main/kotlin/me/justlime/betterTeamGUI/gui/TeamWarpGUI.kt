package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.Warp
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class TeamWarpGUI(row: Int, title: String) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, row * 9, title)
    private val warpItem = Config.TeamWarpView.item
    private val warpSlot: MutableMap<Int, Warp> = mutableMapOf()

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val warps = team.warps.get()
        val material = Material.valueOf(Config.TeamWarpView.item.getString("item") ?: Material.ENDER_PEARL.name)
        warps.forEach { warp ->
            val name = Service.applyLocalPlaceHolder(warpItem.getString("name")?.replace("{warp}", warp.name) ?: warp.name, team, teamPlayer)
            val lore = warpItem.getStringList("lore").map { it.replace("{warp}", warp.name); Service.applyLocalPlaceHolder(it, team, teamPlayer) }
            val glint = warpItem.getBoolean("glint")
            val flags = warpItem.getStringList("flags")
            val item = GUIManager.createItem(material, name, lore, glint, flags)
            val slot = inventory.firstEmpty()
            inventory.setItem(slot, item)
            warpSlot[slot] = warp
        }
        val backSlot = Config.TeamWarpView.backSlot
        val backSlots = Config.TeamWarpView.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val backSlot = Config.TeamWarpView.backSlot
        val backSlots = Config.TeamWarpView.backSlots
        val warpSlots = warpSlot.keys
        if (event.slot in backSlots || event.slot == backSlot) {
            GUIManager.openTeamGUI(event.whoClicked as Player)
            return
        }
        when (event.slot) {
            in warpSlots -> {
                val warp = warpSlot[event.slot]
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    player.performCommand("team:team warp ${warp?.name}")
                }, 2)
                GUIManager.closeInventory(player)
            }
        }
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}