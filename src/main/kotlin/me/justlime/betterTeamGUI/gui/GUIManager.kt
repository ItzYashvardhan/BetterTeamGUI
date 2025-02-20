package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.getPlayerHead
import me.justlime.betterTeamGUI.isBedrockPlayer
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
import javax.swing.text.html.HTML.Tag.S

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

    fun loadItem(
        section: ConfigurationSection,
        inventory: Inventory,
        team: Team,
        slots: List<Int> = listOf(),
        player: TeamPlayer,
        lore: MutableList<String> = mutableListOf()
    ): List<Int> {
        val material = Material.valueOf(section.getString("item") ?: "PAPER")
        val name = Service.applyLocalPlaceHolder(section.getString("name") ?: "&aItem", team, player)
        val newLore = if (lore.isEmpty()) section.getStringList("lore").map { Service.applyLocalPlaceHolder(it, team, player) } else lore
        val glow = section.getBoolean("glow")
        val slotList = section.getIntegerList("slot")
        val slot = section.getString("slot", " ")?.toIntOrNull()
        val item = createItem(material, name, newLore, glow)
        if (slots.isNotEmpty()) {
            slots.forEach { inventory.setItem(it, item) }
            return slots
        }
        if (slotList.isNotEmpty()) {
            slotList.forEach { inventory.setItem(it, item) }
            return slotList
        }
        if (slot == null) return listOf()
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

    fun createCertainItem(itemConfiguration: ConfigurationSection, itemSlot: Int, itemSlots: List<Int>, inventory: Inventory) {
        val backMaterial = Material.valueOf(itemConfiguration.getString("item") ?: "PAPER")
        val backName = Service.applyColors(itemConfiguration.getString("name") ?: " ")
        val backLore = itemConfiguration.getStringList("lore").map { Service.applyColors(it) }
        val backGlow = itemConfiguration.getBoolean("glow")
        if (itemSlots.isNotEmpty()) {
            itemSlots.forEach { inventory.setItem(it, createItem(backMaterial, backName, backLore, backGlow)) }
        }
        inventory.setItem(itemSlot, createItem(backMaterial, backName, backLore, backGlow))

    }

    fun openTeamGUI(sender: Player) {
        val isInTeam = Team.getTeamManager().isInTeam(sender)

        if (isInTeam) {
            if (isBedrockPlayer(sender)) {
                BForm.openTeamForm(sender)
                return
            }
            val team = Team.getTeam(sender.name) ?: return
            val teamPlayer = team.getTeamPlayer(sender) ?: return
            val row = Config.TeamSelfView.row
            val title = Service.applyLocalPlaceHolder(Config.TeamSelfView.title, team, teamPlayer)
            val inventory = TeamSelfGUI(row, title)
            sender.openInventory(inventory.inventory)
        } else {
            if (isBedrockPlayer(sender)) {
                BForm.openTeamListForm(sender)
                return
            }
            val title = Config.TeamListView.title
            val row = Config.TeamListView.row
            val gui = TeamListGUI(row, title).inventory
            sender.openInventory(gui)
        }
    }

    fun openTeamListGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamSelfView.title, team, teamPlayer)
        val row = Config.TeamListView.row
        val gui = TeamListGUI(row, title).inventory
        Bukkit.getPlayer(sender.name)?.openInventory(gui)
    }

    fun openTeamMemberGUI(sender: Player, team: Team, teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamMemberForm(sender, team)
            return
        }
        val title = Service.applyLocalPlaceHolder(Config.TeamMemberView.title, team, teamPlayer)
        val row = Config.TeamMemberView.row
        val memberInventory = TeamMemberGUI(row, title, team, teamPlayer)
        sender.openInventory(memberInventory.inventory)
    }

    fun openTeamInviteGUI(sender: Player, team: Team,teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamMemberForm(sender, team)
            return
        }
        val title = "§lInvite Player"
        val row = 6
        val inviteInventory = InviteGUI(row, title, team, teamPlayer)
        sender.openInventory(inviteInventory.inventory)
    }

    fun openTeamMemberManagementGUI(sender: Player, team: Team, teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamMemberForm(sender, team)
            return
        }
        val title = Service.applyLocalPlaceHolder(Config.TeamMemberManagementView.title, team, teamPlayer)
        val row = Config.TeamMemberManagementView.row
        val memberInventory = TeamMemberManagementGUI(row, title, team, teamPlayer)
        sender.openInventory(memberInventory.inventory)
    }

    fun openTeamAllyGUI(sender: Player, team: Team, teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamAllyForm(sender, team)
            return
        }
        val title = Service.applyLocalPlaceHolder(Config.TeamAllyView.title, team, teamPlayer)
        val row = Config.TeamAllyView.row
        val allyInventory = TeamAllyGUI(row, title, team, teamPlayer)
        sender.openInventory(allyInventory.inventory)
    }

    fun openTeamWarpGUI(sender: Player) {

        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        if (isBedrockPlayer(sender)) {
            BForm.openTeamWarpForm(team, teamPlayer)
            return
        }
        val title = Service.applyLocalPlaceHolder(Config.TeamWarpView.title, team, teamPlayer)
        val row = Config.TeamWarpView.row
        val warpInventory = TeamWarpGUI(row, title)
        sender.openInventory(warpInventory.inventory)
    }

    fun openTeamLeaveGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        val title = Service.applyLocalPlaceHolder(Config.TeamLeaveView.title, team, teamPlayer)
        val row = Config.TeamLeaveView.row
        val leaveInventory = TeamLeaveGUI(row, title)
        sender.openInventory(leaveInventory.inventory)
    }

    fun openTeamBalanceGUI(sender: Player) {
        val team = Team.getTeam(sender.name) ?: return
        val teamPlayer = team.getTeamPlayer(sender) ?: return
        if (isBedrockPlayer(sender)) {
            BForm.openTeamBalanceForm(team, teamPlayer)
            return
        }
        val title = Service.applyLocalPlaceHolder(Config.TeamBalanceView.title, team, teamPlayer)
        val row = Config.TeamBalanceView.row
        val balanceInventory = TeamBalanceGUI(row, title)
        sender.openInventory(balanceInventory.inventory)
    }

    fun openTeamOtherGUI(sender: Player, oTeam: Team, teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamOtherForm(sender, oTeam)
            return
        }
        val title = Service.applyLocalPlaceHolder(Config.TeamOtherView.title, oTeam, teamPlayer)
        val row = Config.TeamOtherView.row
        val otherInventory = TeamOtherGUI(row, title, oTeam, teamPlayer)
        sender.openInventory(otherInventory.inventory)
    }

    fun openTeamLeaderBoardGUI(sender: Player, teamPlayer: TeamPlayer) {
        val title = Service.applyColors(Config.TeamLBView.title)
        val row = Config.TeamLBView.row
        val leaderBoardInventory = TeamLeaderBoard(row, title, teamPlayer)
        sender.openInventory(leaderBoardInventory.inventory)
    }

    fun closeInventory(player: Player) {
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.closeInventory()
        }, 2)
    }

}