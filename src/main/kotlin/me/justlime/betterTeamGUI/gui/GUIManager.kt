package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.getPlayerHead
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object GUIManager {

    fun insertBackground(inventory: Inventory) {
        val item = Material.valueOf(Config.background.name)
        val itemStack = ItemStack(item)
        val itemMeta = itemStack.itemMeta.apply {
            this?.itemFlags?.clear()
            this?.isHideTooltip = true
        }
        itemStack.itemMeta = itemMeta
        for (i in 0 until inventory.size) {
            if (i in 0..8 || i >= inventory.size - 9 || i % 9 == 0 || (i + 1) % 9 == 0) {
                inventory.setItem(i, itemStack)
            }
        }

    }

    fun createItem(material: Material, name: String, lore: List<String>, glint: Boolean): ItemStack {
        return ItemStack(material).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(name)
                setLore(lore) // Use setLore() for better compatibility

                // Apply enchantment for glint effect
                if (glint) {
                    addEnchant(Enchantment.UNBREAKING, 1, true)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                }
                addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                addItemFlags(ItemFlag.HIDE_ATTRIBUTES)

            }
            }
    }


    fun loadItem(section: ConfigurationSection, inventory: Inventory, team: Team, slots: List<Int> = listOf(), player: TeamPlayer): List<Int> {
        val material = Material.valueOf(section.getString("item") ?: "PAPER")
        val name = Service.applyLocalPlaceHolder(section.getString("name") ?: "&aItem", team, player)
        val lore = section.getStringList("lore").map { Service.applyLocalPlaceHolder(it, team, player) }
        val glow = section.getBoolean("glow")
        val slotList = section.getIntegerList("slot")
        val slot = section.getInt("slot")
        val item = createItem(material, name, lore, glow)
        if (slots.isNotEmpty()) {
            slots.forEach { inventory.setItem(it, item) }
            return slots
        }
        if (slotList.isNotEmpty()) {
            slotList.forEach { inventory.setItem(it, item) }
            return slotList
        }
        inventory.setItem(slot, item)
        return listOf(slot)
    }

    fun createHeadItem(team: Team, offlinePlayer: OfflinePlayer, itemLore: MutableList<String> = mutableListOf()): ItemStack {
        val playerHeadItem = getPlayerHead(offlinePlayer)
        val meta = playerHeadItem.itemMeta

        // Update meta properties
        meta?.setDisplayName(Service.applyLocalPlaceHolder(Config.TeamInfo.teamName, team, team.members.getRank(PlayerRank.OWNER).first()))

        meta?.lore = itemLore
        playerHeadItem.itemMeta = meta

        return playerHeadItem
    }

    fun openTeamGUI(sender: Player) {
        val isInTeam = Team.getTeamManager().isInTeam(sender)
        if (isInTeam) {
            val team = Team.getTeam(sender.name) ?: return
            val teamPlayer = team.getTeamPlayer(sender) ?: return
            val row = Config.TeamSelfItem.row
            val title = Service.applyLocalPlaceHolder(Config.TeamSelfItem.title, team, teamPlayer)
            val inventory = TeamSelfGUI(row, title)
            sender.openInventory(inventory.inventory)
        } else {
            val title = Config.TeamListItem.title
            val row = Config.TeamListItem.row
            val gui = TeamListGUI(row, title).inventory
            sender.openInventory(gui)
        }
    }

    fun openTeamListGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamSelfItem.title, team, teamPlayer)
        val row = Config.TeamListItem.row
        val gui = TeamListGUI(row, title).inventory
        Bukkit.getPlayer(sender.name)?.openInventory(gui)
    }

    fun openTeamCreateGUI(sender: Player) {

    }

    fun openTeamMemberGUI(sender: Player,team: Team, teamPlayer: TeamPlayer) {
        val title = Service.applyLocalPlaceHolder(Config.TeamMemberItem.title, team, teamPlayer)
        val row = Config.TeamMemberItem.row
        val memberInventory = TeamMemberGUI(row, title, team, teamPlayer)
        sender.openInventory(memberInventory.inventory)
    }

    fun openTeamAllyGUI(sender: Player, team: Team, teamPlayer: TeamPlayer) {
        val title = Service.applyLocalPlaceHolder(Config.TeamAllyItem.title, team, teamPlayer)
        val row = Config.TeamAllyItem.row
        val allyInventory = TeamAllyGUI(row, title, team, teamPlayer)
        sender.openInventory(allyInventory.inventory)
    }

    fun openTeamWarpGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamWarpItem.title, team, teamPlayer)
        val row = Config.TeamWarpItem.row
        val warpInventory = TeamWarpGUI(row, title)
        sender.openInventory(warpInventory.inventory)
    }

    fun openTeamLeaveGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamLeaveItem.title, team, teamPlayer)
        val row = Config.TeamLeaveItem.row
        val leaveInventory = TeamLeaveGUI(row, title)
        sender.openInventory(leaveInventory.inventory)
    }

    fun openTeamBalanceGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamBalanceItem.title, team, teamPlayer)
        val row = Config.TeamBalanceItem.row
        val balanceInventory = TeamBalanceGUI(row, title)
        sender.openInventory(balanceInventory.inventory)
    }

    fun openTeamOtherGUI(sender: Player, oTeam: Team) {

        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamOtherItem.title, oTeam, teamPlayer)
        val row = Config.TeamOtherItem.row
        val otherInventory = TeamOtherGUI(row, title, oTeam)
        sender.openInventory(otherInventory.inventory)
    }

    fun closeInventory(player: Player) {
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.closeInventory()
        }, 2)
    }

}