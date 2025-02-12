package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.gui.GUIManager.loadItem
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class TeamSelfGUI(row: Int, title: String) : GUIHandler {

    enum class ItemSlot {
        CHAT, HOME, BALANCE, WARP, MEMBERS, ENDERCHEST, PVP, ALLY, LEAVE, LIST_ITEM, SETTING_ITEM
    }

    private val inventory = Bukkit.createInventory(this, row * 9, title)
    private val sections = mutableMapOf(
        ItemSlot.ALLY to Config.TeamSelfView.ally,
        ItemSlot.BALANCE to Config.TeamSelfView.balance,
        ItemSlot.CHAT to Config.TeamSelfView.chat,
        ItemSlot.HOME to Config.TeamSelfView.home,
        ItemSlot.ENDERCHEST to Config.TeamSelfView.enderchest,
        ItemSlot.LEAVE to Config.TeamSelfView.leave,
        ItemSlot.LIST_ITEM to Config.TeamSelfView.listItem,
        ItemSlot.MEMBERS to Config.TeamSelfView.members,
        ItemSlot.PVP to Config.TeamSelfView.pvp,
        ItemSlot.SETTING_ITEM to Config.TeamSelfView.settingItem,
        ItemSlot.WARP to Config.TeamSelfView.warp
    )

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        sections.values.forEach { loadItem(it, inventory, team, mutableListOf(), teamPlayer) }

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val slot = event.slot
        val path = "slot"
        val allySlot = sections[ItemSlot.ALLY]?.getString("slot", " ")?.toIntOrNull()
        val chatSlot = sections[ItemSlot.CHAT]?.getString("slot", " ")?.toIntOrNull()
        val homeSlot = sections[ItemSlot.HOME]?.getString("slot", " ")?.toIntOrNull()
        val balanceSlot = sections[ItemSlot.BALANCE]?.getString("slot", " ")?.toIntOrNull()
        val warpSlot = sections[ItemSlot.WARP]?.getString("slot", " ")?.toIntOrNull()
        val membersSlot = sections[ItemSlot.MEMBERS]?.getString("slot", " ")?.toIntOrNull()
        val enderChestSlot = sections[ItemSlot.ENDERCHEST]?.getString("slot", " ")?.toIntOrNull()
        val pvpSlot = sections[ItemSlot.PVP]?.getString("slot", " ")?.toIntOrNull()
        val leaveSlot = sections[ItemSlot.LEAVE]?.getString("slot", " ")?.toIntOrNull()
        val listItemSlot = sections[ItemSlot.LIST_ITEM]?.getString("slot", " ")?.toIntOrNull()
        val settingItemSlot = sections[ItemSlot.SETTING_ITEM]?.getString("slot", " ")?.toIntOrNull()

        when (slot) {
            chatSlot -> {
                if (event.click.isShiftClick) {
                    player.performCommand("team allychat")
                } else player.performCommand("team chat")
                GUIManager.openTeamGUI(player)
            }

            homeSlot -> {
                player.performCommand("team home")
                GUIManager.closeInventory(player)
            }

            balanceSlot -> {
                GUIManager.openTeamBalanceGUI(player)
            }

            warpSlot -> {
                GUIManager.openTeamWarpGUI(player)
            }

            membersSlot -> {
                GUIManager.openTeamMemberGUI(player, team, teamPlayer)

            }

            enderChestSlot -> {
                player.performCommand("team echest")
            }

            pvpSlot -> {
                player.performCommand("team pvp")
                GUIManager.closeInventory(player)
            }

            allySlot -> {
                GUIManager.openTeamAllyGUI(player, team, teamPlayer)
            }

            leaveSlot -> {
                GUIManager.openTeamLeaveGUI(player)
            }

            listItemSlot -> {
                GUIManager.openTeamListGUI(player)
            }

            settingItemSlot -> {
                player.sendMessage("§c§lComing soon!")
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