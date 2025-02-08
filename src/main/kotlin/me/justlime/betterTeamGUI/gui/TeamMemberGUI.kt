package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamMemberGUI(rows: Int, title: String,val team: Team,val teamPlayer: TeamPlayer) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, rows * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val teamMembers = team.members.get()
        teamMembers.forEach {
            val item = GUIManager.createHeadItem(team, it.player)
            val itemMeta = item.itemMeta
            itemMeta?.apply {
                setDisplayName("§" + team.color.char.toString() + it.player.name)
                lore = listOf(if (it.rank == PlayerRank.DEFAULT) "§fMember" else "§c" + it.rank.name)
            }
            item.itemMeta = itemMeta
            inventory.addItem(item)
        }
        val backSlot = Config.TeamMemberItem.backSlot
        val backSlots = Config.TeamMemberItem.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val backSlot = Config.TeamMemberItem.backSlot
        val backSlots = Config.TeamMemberItem.backSlots
        when (event.slot) {
            in backSlots, backSlot -> {
                GUIManager.openTeamGUI(event.whoClicked as Player)
            }
        }
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}