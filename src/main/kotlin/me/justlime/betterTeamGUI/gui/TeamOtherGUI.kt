package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamOtherGUI(row: Int, val title: String, private val otherTeam: Team) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, row * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
        return
    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val sections = mutableListOf(
            Config.TeamOtherItem.info, Config.TeamOtherItem.ally, Config.TeamOtherItem.member, Config.TeamOtherItem.balance
        )
        sections.forEach {
            GUIManager.loadItem(it, inventory, otherTeam, mutableListOf(), teamPlayer)
        }

        val backSlot = Config.TeamOtherItem.backSlot
        val backSlots = Config.TeamOtherItem.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, otherTeam, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val slot = event.slot
        val infoSlot = Config.TeamOtherItem.info.getInt("slot")
        val allySlot = Config.TeamOtherItem.ally.getInt("slot")
        val memberSlot = Config.TeamOtherItem.member.getInt("slot")
        val balanceSlot = Config.TeamOtherItem.balance.getInt("slot")
        val backSlot = Config.TeamOtherItem.backSlot
        val backSlots = Config.TeamOtherItem.backSlots
        val teamPlayer = Team.getTeam(player.name)?.getTeamPlayer(player) ?: return

        when (slot) {
            allySlot -> {
                GUIManager.openTeamAllyGUI(player, otherTeam,teamPlayer)
            }

            memberSlot -> {
                GUIManager.openTeamMemberGUI(player, otherTeam,teamPlayer)
            }

            in backSlots, backSlot -> {
                GUIManager.openTeamGUI(player)
            }

            else -> {
                return
            }
        }

    }

    override fun onClose(event: InventoryCloseEvent) {
        return
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}