package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Main.plugin
import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.Team.getTeam
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.getPlayerHead
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class TeamListGUI(rows: Int, title: String) : BetterTeamGUIInventory {
    private val inventory = Bukkit.createInventory(this, rows, title)
    override fun getInventory(): Inventory {
        return inventory
    }

    override fun onOpen(event: InventoryOpenEvent) {
        GUIManager.insertBackground(inventory)
    }

    override fun loadInventory(player: HumanEntity) {
        // Insert background
        GUIManager.insertBackground(inventory)

// Retrieve all teams with min 1 members of active
        Team.getTeamManager().loadTeams()
        val teams = Team.getTeamManager().loadedTeamListClone

// Add items for open and closed teams
        teams.forEach { (_, team) ->
            val playerHeadItem = createPlayerHeadItem(team)
            inventory.addItem(playerHeadItem)
        }

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val item = event.currentItem ?: return
        if (item.type != Material.PLAYER_HEAD) return
        val meta = item.itemMeta as? SkullMeta ?: return
        val owner = meta.owningPlayer ?: return
        val player1 = event.whoClicked as? Player ?: return
        val team = getTeam(owner) ?: return

        val existingTeam = getTeam(player1) // Replace with a method that retrieves the team of a player
        if (existingTeam != null) {
            player1.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamAlreadyJoined, existingTeam))
            return
        }
        if (team.getTeamPlayer(player1) != null) {
            player1.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamAlreadyJoined, team))
        } else if (!team.isOpen && !team.isInvited(player1.uniqueId)) {
            player1.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamClosed, team))
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                team.join(player1)
                player1.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamJoin, team))
            })
        }
        player1.closeInventory()

    }

    override fun onClose(event: InventoryCloseEvent) {
        return
    }

    private fun createPlayerHeadItem(team: Team): ItemStack {
        val owner = team.members.getRank(PlayerRank.OWNER).first()
        val playerHeadItem = getPlayerHead(owner.player)
        val meta = playerHeadItem.itemMeta

        // Update meta properties
        meta?.setDisplayName(Service.applyLocalPlaceHolder(Config.TeamInfo.teamName, team))
        meta?.lore = Config.TeamInfo.lore.map { Service.applyLocalPlaceHolder(it, team) }
        playerHeadItem.itemMeta = meta

        return playerHeadItem
    }

}