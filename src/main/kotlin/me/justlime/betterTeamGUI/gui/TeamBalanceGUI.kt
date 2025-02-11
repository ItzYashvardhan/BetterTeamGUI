package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team.getTeam
import me.justlime.betterTeamGUI.config.Config
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamBalanceGUI(rows: Int, title: String) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, rows * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
    }

    override fun loadInventory(player: Player) {
        val team = getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        GUIManager.insertBackground(inventory)
        val withdrawItem = Config.TeamBalanceView.withdraw
        val depositItem = Config.TeamBalanceView.deposit
        GUIManager.loadItem(withdrawItem, inventory, team, mutableListOf(), teamPlayer)
        GUIManager.loadItem(depositItem, inventory, team, mutableListOf(), teamPlayer)
        val backSlot = Config.TeamBalanceView.backSlot
        val backSlots = Config.TeamBalanceView.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, getTeam(player.name), if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val team = getTeam(event.whoClicked.name) ?: return
        val teamPlayer = team.getTeamPlayer(event.whoClicked as Player) ?: return
        val backSlot = Config.TeamBalanceView.backSlot
        val backSlots = Config.TeamBalanceView.backSlots
        val withdrawItem = Config.TeamBalanceView.withdraw
        val depositItem = Config.TeamBalanceView.deposit
        val depositSlot = GUIManager.loadItem(withdrawItem, inventory, team, mutableListOf(), teamPlayer)
        val withdrawSlot = GUIManager.loadItem(depositItem, inventory, team, mutableListOf(), teamPlayer)
        val player = event.whoClicked as Player
        when (event.slot) {
            in depositSlot -> {
                player.spigot().sendMessage(
                    TextComponent("§6Click here to deposit!").apply {
                        isBold = true
                        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("§7Click to type /team deposit"))
                        clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/team deposit ")
                    }
                )
                GUIManager.closeInventory(player)

            }

            in withdrawSlot -> {
                player.spigot().sendMessage(
                    TextComponent("§6Click here to withdraw!").apply {
                        isBold = true
                        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("§7Click to type /team withdraw"))
                        clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/team withdraw ")
                    }
                )
                GUIManager.closeInventory(player)

            }

            in backSlots, backSlot -> {
                GUIManager.openTeamGUI(event.whoClicked as Player)
            }

            else -> {
                event.isCancelled = true
            }
        }
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}