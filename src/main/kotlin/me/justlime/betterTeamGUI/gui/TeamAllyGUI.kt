package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.SkullMeta

class TeamAllyGUI(row: Int, val title: String, val team: Team, val teamPlayer: TeamPlayer) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, row * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val allies = team.allies.get()
        allies.forEach {
            val alliesTeam = Team.getTeam(it) ?: return@forEach
            val owner = alliesTeam.members.getRank(PlayerRank.OWNER).firstOrNull() ?: return@forEach
            val item = GUIManager.createHeadItem(alliesTeam, owner.player)
            inventory.addItem(item)
        }
        val backSlot = Config.TeamAllyItem.backSlot
        val backSlots = Config.TeamAllyItem.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val backSlot = Config.TeamAllyItem.backSlot
        val backSlots = Config.TeamAllyItem.backSlots
        if (event.slot in backSlots || event.slot == backSlot) {
            GUIManager.openTeamGUI(player)
            return
        }

        val item = event.currentItem ?: return
        if (item.type != Material.PLAYER_HEAD ) return
        val owner = (item.itemMeta as? SkullMeta)?.owningPlayer ?: return
        val alliesTeam = Team.getTeam(owner) ?: return
        GUIManager.openTeamOtherGUI(player, alliesTeam)
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}