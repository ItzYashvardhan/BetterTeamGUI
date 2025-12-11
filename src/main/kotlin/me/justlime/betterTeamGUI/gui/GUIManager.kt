package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.FormService
import me.justlime.betterTeamGUI.gui.bedrock.BForm
import me.justlime.betterTeamGUI.gui.items.BanListItem
import me.justlime.betterTeamGUI.gui.items.ColorPickerItem
import me.justlime.betterTeamGUI.gui.items.LeaderBoardItem
import me.justlime.betterTeamGUI.gui.items.TeamAlliesItem
import me.justlime.betterTeamGUI.gui.items.TeamDialogItem
import me.justlime.betterTeamGUI.gui.items.TeamListItem
import me.justlime.betterTeamGUI.gui.items.TeamMemberItem
import me.justlime.betterTeamGUI.gui.items.TeamSettingItem
import me.justlime.betterTeamGUI.gui.items.TeamDashboardItem
import me.justlime.betterTeamGUI.gui.items.TeamViewerItems
import me.justlime.betterTeamGUI.gui.items.TeamWarpItem
import me.justlime.betterTeamGUI.gui.java.*
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.isBedrockPlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object GUIManager {

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
                        } catch (_: IllegalArgumentException) {
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
        } catch (_: Exception) {
            pluginInstance.logger.warning("Invalid material: ${section.getString("item")} at item: ${section.getString("name")}")
            Material.PAPER
        }
        val name = FormService.applyLocalPlaceHolder(section.getString("name") ?: "&aItem", team, player)
        val newLore = if (lore.isEmpty()) section.getStringList("lore").map { FormService.applyLocalPlaceHolder(it, team, player) } else lore
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
            } catch (_: Exception) {
                pluginInstance.logger.warning("Invalid slot: $slotList at item: $name")
            }
        }
        try {
            val slot = section.getString("slot", " ")?.toIntOrNull() ?: return listOf()
            inventory.setItem(slot, item)
            return listOf(slot)
        } catch (_: Exception) {
            pluginInstance.logger.warning("Invalid slot: ${section.getString("slot")} at item: ${section.getString("name")}")
            return listOf()
        }
    }

    fun closeInventory(player: Player) {
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.closeInventory()
        }, 2)
    }

    //USING New LimeFrameGUI

    fun openTeamGUI(player: Player) {
        val isInTeam = Team.getTeamManager().isInTeam(player)

        if (isInTeam) {
            if (isBedrockPlayer(player)) {
                BForm.openTeamForm(player)
                return
            }
            val team = Team.getTeam(player.name) ?: return
            val teamPlayer = team.getTeamPlayer(player) ?: return
            teamDashboard(TeamDashboardItem.setting, player, team, teamPlayer)
            return
        }
        if (isBedrockPlayer(player)) {
            BForm.openTeamListForm(player)
            return
        }
        //TODO "Add Team Create Option"
        openTeamListGUI(player)

    }

    fun openTeamListGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamListForm(player)
            return
        }
        teamList(TeamListItem.setting, player)
    }

    fun openTeamLeaveGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamLeaveForm(player)
            return
        }
        teamLeaveDialog(TeamDialogItem.leaveSetting).open(player)
    }

    fun openTeamUpdateHomeGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for update home
            return
        }
        teamUpdateHomeDialog(TeamDialogItem.updateHomeSetting).open(player)
    }

    fun openTeamDeleteHomeGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for delete home
            return
        }
        teamDeleteHomeDialog(TeamDialogItem.deleteHomeSetting).open(player)
    }

    fun openTeamWarpGUI(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        if (isBedrockPlayer(player)) {
            BForm.openTeamWarpForm(team, teamPlayer)
            return
        }
        teamWarp(TeamWarpItem.setting, team, teamPlayer, player).open(player)
    }

    fun openTeamMemberGUI(player: Player, team: Team) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamMemberForm(player, team)
            return
        }
        val setting = TeamMemberItem.setting
        teamMemberView(setting,player, team)
    }

    fun openTeamMemberManagementGUI(player: Player, targetTeamPlayer: TeamPlayer, team: Team) {

        if (isBedrockPlayer(player)) {
            BForm.openTeamMemberForm(player, team)
            return
        }
        teamMemberManagement(player, targetTeamPlayer, team)
    }

    fun openTeamPromoteToOwnerDialog(player: Player, targetTeamPlayer: TeamPlayer) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for promote to owner
            return
        }
        teamPromoteToOwnerDialog(TeamDialogItem.promoteToOwnerSetting.clone(), targetTeamPlayer).open(player)
    }

    fun openTeamPromoteToAdminDialog(player: Player, targetTeamPlayer: TeamPlayer) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for promote to admin
            return
        }
        teamPromoteToAdminDialog(TeamDialogItem.promoteToAdminSetting.clone(), targetTeamPlayer).open(player)
    }

    fun openTeamDemoteToAdminDialog(player: Player, targetTeamPlayer: TeamPlayer) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for demote to admin
            return
        }
        teamDemoteToAdminDialog(TeamDialogItem.demoteToAdminSetting.clone(), targetTeamPlayer).open(player)
    }

    fun openTeamDemoteToDefaultDialog(player: Player, targetTeamPlayer: TeamPlayer) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for demote to default
            return
        }
        teamDemoteToDefaultDialog(TeamDialogItem.demoteToDefaultSetting.clone(), targetTeamPlayer).open(player)
    }

    fun openTeamKickDialog(player: Player, targetTeamPlayer: TeamPlayer) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for kick
            return
        }
        teamKickDialog(TeamDialogItem.kickSetting.clone(), targetTeamPlayer).open(player)
    }

    fun openTeamBanDialog(player: Player, targetTeamPlayer: TeamPlayer) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for ban
            return
        }
        teamBanDialog(TeamDialogItem.banSetting.clone(), targetTeamPlayer).open(player)
    }

    //Setting
    fun openTeamSettingGUI(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        teamSettingView(TeamSettingItem.setting.clone(), player, team, teamPlayer).open(player)
    }

    fun openColorPickerGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for color picker
            return
        }
        colorPickerView(ColorPickerItem.setting.clone(), player).open(player)
    }

    fun openTeamDisbandGUI(player: Player) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for disband
            return
        }
        teamDisbandDialog(setting = TeamDialogItem.disbandSetting.clone()).open(player)
    }

    fun openTeamBanListGUI(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        teamBanList(setting = BanListItem.setting.clone(), player, team)
    }

    fun openTeamAlliesListGUI(player: Player, team: Team) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamAllyForm(player, team)
            return
        }
        val team = Team.getTeam(player.name) ?: return
        teamAlliesList(setting = TeamAlliesItem.setting.clone(), player, team)
    }

    fun openTeamNeutralDialog(player: Player, targetTeam: Team) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for neutral
            return
        }
        teamNeutralDialog(TeamDialogItem.neutralSetting.clone(), targetTeam).open(player)
    }

    fun openTeamLeaderBoardGUI(player: Player, team: Team) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for leaderboard
            return
        }
        teamLeaderBoard(setting = LeaderBoardItem.setting.clone(), player, team)
    }

    fun openTeamViewerGUI(player: Player, targetTeam: Team) {
        if (isBedrockPlayer(player)) {
            BForm.openTeamOtherForm(player, targetTeam)
            return
        }
        val  setting = TeamViewerItems.teamViewerSetting.clone()
        teamViewer(setting = setting, player, targetTeam)
    }

    fun openTeamViewerMembersGUI(player: Player, team: Team) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for team viewer members
            return
        }
        teamViewerMembers(setting = TeamViewerItems.teamViewerMembersSetting.clone(), player, team)
    }

    fun openTeamViewerAlliesGUI(player: Player, team: Team) {
        if (isBedrockPlayer(player)) {
            // TODO: Add Bedrock form for team viewer allies
            return
        }
        teamViewerAllies(setting = TeamViewerItems.teamViewerAlliesSetting.clone(), player, team)
    }



}