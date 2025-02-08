package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.gui.GUIManager.loadItem
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory

class TeamSelfGUI(row: Int, title: String) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, row * 9, title)
    override fun onOpen(event: InventoryOpenEvent) {
    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val sections = mutableListOf(
            Config.TeamSelfItem.chat,
            Config.TeamSelfItem.home,
            Config.TeamSelfItem.balance,
            Config.TeamSelfItem.warp,
            Config.TeamSelfItem.members,
            Config.TeamSelfItem.enderchest,
            Config.TeamSelfItem.pvp,
            Config.TeamSelfItem.ally,
            Config.TeamSelfItem.leave,
            Config.TeamSelfItem.listItem,
            Config.TeamSelfItem.settingItem
        )
        sections.forEach { loadItem(it, inventory, team, mutableListOf(), teamPlayer) }

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val slot = event.slot
        val path = "slot"
        val chatSlot = Config.TeamSelfItem.chat.getInt(path)
        val homeSlot = Config.TeamSelfItem.home.getInt(path)
        val balanceSlot = Config.TeamSelfItem.balance.getInt(path)
        val warpSlot = Config.TeamSelfItem.warp.getInt(path)
        val membersSlot = Config.TeamSelfItem.members.getInt(path)
        val enderChestSlot = Config.TeamSelfItem.enderchest.getInt(path)
        val pvpSlot = Config.TeamSelfItem.pvp.getInt(path)
        val allySlot = Config.TeamSelfItem.ally.getInt(path)
        val leaveSlot = Config.TeamSelfItem.leave.getInt("slot")
        val listItemSlot = Config.TeamSelfItem.listItem.getInt(path)
        val settingItemSlot = Config.TeamSelfItem.settingItem.getInt(path)

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