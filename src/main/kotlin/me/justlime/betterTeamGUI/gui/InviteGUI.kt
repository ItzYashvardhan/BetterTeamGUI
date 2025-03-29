package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.SkullMeta

class InviteGUI(rows: Int, title: String, val team: Team, val teamPlayer: TeamPlayer) : GUIHandler {
    private val inventory: Inventory = Bukkit.createInventory(this, rows * 9, title)
    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val teamManager = Team.getTeamManager()
        val players = Bukkit.getOnlinePlayers().filter { !teamManager.isInTeam(it) }
        players.forEach {
            val skull = GUIManager.createHeadItem(team, it)
            val itemMeta = skull.itemMeta
            itemMeta?.setDisplayName("§a${it.name}")
            skull.itemMeta = itemMeta
            inventory.addItem(skull)

        }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val item = event.currentItem ?: return
        val clickedPlayer = (item.itemMeta as? SkullMeta)?.owningPlayer ?: return
        if (clickedPlayer is Player) {
            team.invite(clickedPlayer.uniqueId)
            player.sendMessage("§aInvited ${clickedPlayer.name}")
            clickedPlayer.sendMessage("§aYou have been invited to ${team.name}")
            GUIManager.openTeamMemberGUI(player, team, teamPlayer)
            return
        } else {
            player.sendMessage("§cPlayer in Currently not online")
        }

    }

    override fun getInventory(): Inventory {
        return inventory
    }
}