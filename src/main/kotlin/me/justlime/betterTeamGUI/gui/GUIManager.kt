package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.gui.bedrock.BForm
import me.justlime.betterTeamGUI.gui.items.ColorPickerItem
import me.justlime.betterTeamGUI.gui.items.TeamDialogItem
import me.justlime.betterTeamGUI.gui.items.TeamListItem
import me.justlime.betterTeamGUI.gui.items.TeamMemberItem
import me.justlime.betterTeamGUI.gui.items.TeamSettingItem
import me.justlime.betterTeamGUI.gui.items.TeamViewItem
import me.justlime.betterTeamGUI.gui.items.TeamWarpItem
import me.justlime.betterTeamGUI.gui.java.colorPickerView
import me.justlime.betterTeamGUI.gui.java.teamDeleteHomeDialog
import me.justlime.betterTeamGUI.gui.java.teamDisbandDialog
import me.justlime.betterTeamGUI.gui.java.teamLeaveDialog
import me.justlime.betterTeamGUI.gui.java.teamList
import me.justlime.betterTeamGUI.gui.java.teamMemberView
import me.justlime.betterTeamGUI.gui.java.teamSettingView
import me.justlime.betterTeamGUI.gui.java.teamUpdateHomeDialog
import me.justlime.betterTeamGUI.gui.java.teamView
import me.justlime.betterTeamGUI.gui.java.teamWarp
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.getPlayerHead
import me.justlime.betterTeamGUI.utilities.isBedrockPlayer
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
        }
        itemStack.itemMeta = itemMeta
        for (i in 0 until inventory.size) {
            if (i in 0..8 || i >= inventory.size - 9 || i % 9 == 0 || (i + 1) % 9 == 0) {
                inventory.setItem(i, itemStack)
            }
        }
    }

    fun openTeamGUI(player: Player) {
        val isInTeam = Team.getTeamManager().isInTeam(player)

        if (isInTeam) {
            if (isBedrockPlayer(player)) {
                BForm.openTeamForm(player)
                return
            }
            val team = Team.getTeam(player.name) ?: return
            val teamPlayer = team.getTeamPlayer(player) ?: return
            teamView(TeamViewItem.setting,player, team, teamPlayer)
            return
        }
        if (isBedrockPlayer(player)) {
            BForm.openTeamListForm(player)
            return
        }
        //TODO "Add Team Create Option"
        openTeamListGUI(player)

    }

    fun createItem(material: Material, name: String, lore: List<String>, glint: Boolean, flags: MutableList<String>): ItemStack {
        return ItemStack(material).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(name)
                setLore(lore) // Use setLore() for better compatibility

                // Apply enchantment for glint effect
                if (glint) {
                    addEnchant(Enchantment.DURABILITY, 1, true)
                }
                if (flags.isNotEmpty()) {
                    flags.forEach {
                        try {
                            addItemFlags(ItemFlag.valueOf(it))
                        } catch (e: IllegalArgumentException) {
                            pluginInstance.logger.warning("Unknown flag: $it at item: $name")
                        }
                    }
                }

            }
        }
    }

    fun loadItem(
        section: ConfigurationSection, inventory: Inventory, team: Team, slots: List<Int> = listOf(), player: TeamPlayer, lore: MutableList<String> = mutableListOf()
    ): List<Int> {
        val flags = section.getStringList("flags")
        val material = try {
            Material.valueOf(section.getString("item") ?: "PAPER")
        } catch (e: Exception) {
            pluginInstance.logger.warning("Invalid material: ${section.getString("item")} at item: ${section.getString("name")}")
            Material.PAPER
        }
        val name = Service.applyLocalPlaceHolder(section.getString("name") ?: "&aItem", team, player)
        val newLore = if (lore.isEmpty()) section.getStringList("lore").map { Service.applyLocalPlaceHolder(it, team, player) } else lore
        val glow = section.getBoolean("glow")
        val slotList = section.getIntegerList("slot")
        val item = createItem(material, name, newLore, glow, flags)
        if (slots.isNotEmpty()) {
            slots.forEach { inventory.setItem(it, item) }
            return slots
        }
        if (slotList.isNotEmpty()) {
            try {
                slotList.forEach {
                    inventory.setItem(it, item)
                }
                return slotList
            } catch (e: Exception) {
                pluginInstance.logger.warning("Invalid slot: $slotList at item: $name")
            }
        }
        try {
            val slot = section.getString("slot", " ")?.toIntOrNull()
            if (slot == null) return listOf()
            inventory.setItem(slot, item)
            return listOf(slot)
        } catch (e: Exception) {
            pluginInstance.logger.warning("Invalid slot: ${section.getString("slot")} at item: ${section.getString("name")}")
            return listOf()
        }
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
        val flags = itemConfiguration.getStringList("flags")
        if (itemSlots.isNotEmpty()) {
            itemSlots.forEach { inventory.setItem(it, createItem(backMaterial, backName, backLore, backGlow, flags)) }
        }
        inventory.setItem(itemSlot, createItem(backMaterial, backName, backLore, backGlow, flags))

    }

    fun openTeamListGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamListForm(player)
            return
        }

        TeamListItem.setting.placeholderPlayer = player
        teamList(TeamListItem.setting, player)
    }

    fun openTeamMemberGUI(player: Player, team: Team) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamMemberForm(player, team)
            return
        }
        teamMemberView(TeamMemberItem.setting, team).open(player)
    }

    fun openTeamInviteGUI(sender: Player, team: Team, teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamMemberForm(sender, team)
            return
        }

    }

    fun openTeamMemberManagementGUI(sender: Player, team: Team, teamPlayer: TeamPlayer) {
        if (isBedrockPlayer(sender)) {
            BForm.openTeamMemberForm(sender, team)
            return
        }
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

    fun openTeamWarpGUI(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        if (isBedrockPlayer(player)) {
            BForm.openTeamWarpForm(team, teamPlayer)
            return
        }
        TeamWarpItem.setting.placeholderPlayer = player
        teamWarp(TeamWarpItem.setting, team, teamPlayer, player).open(player)
    }

    fun openTeamLeaveGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamLeaveForm(player)
            return
        }
        teamLeaveDialog(TeamDialogItem.leaveSetting).open(player)
    }

    fun openTeamDisbandGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for disband
            return
        }
        teamDisbandDialog(setting = TeamDialogItem.disbandSetting).open(player)
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

    fun closeInventory(player: Player) {
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.closeInventory()
        }, 2)
    }

    fun openTeamSettingGUI(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        teamSettingView(TeamSettingItem.setting, player, team, teamPlayer).open(player)
    }

    fun openTeamDeleteHomeGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for delete home
            return
        }
        teamDeleteHomeDialog(TeamDialogItem.deleteHomeSetting).open(player)
    }

    fun openTeamUpdateHomeGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for update home
            return
        }
        teamUpdateHomeDialog(TeamDialogItem.updateHomeSetting).open(player)
    }

    fun openColorPickerGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for color picker
            return
        }
       colorPickerView(ColorPickerItem.setting, player).open(player)
    }

}