package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamSettingItem
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.permissionDenied
import me.justlime.betterTeamGUI.utilities.teamToPlaceholderMap
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamSettingView(setting: GUISetting, player: Player, team: Team, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(setting) {
    onClick {
        it.isCancelled = true

    }

    TeamSettingItem.background.forEach { setItem(it) }
    setItem(TeamButton.back, TeamSettingItem.backSlot) { GUIManager.openTeamGUI(player) }
    setItem(TeamButton.home, TeamSettingItem.homeSlot) { GUIManager.openTeamGUI(player) }

    // Color Picker
    setItem(TeamSettingItem.colorPicker.apply {
        this?.customPlaceholder = mapOf("{color}" to team.color.name)
    }) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }
        GUIManager.openColorPickerGUI(player)
    }

    // Description
    setItem(TeamSettingItem.description) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }
        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.descriptionTitle ?: "")
            val label = applyMiniColor(TeamSettingItem.descriptionLabel ?: "")
            val inputItem = TeamSettingItem.descriptionInputItem ?: GuiItem(Material.PAPER)
            val outputItem = TeamSettingItem.descriptionOutputItem ?: GuiItem(Material.PAPER)
            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, { }, reopenGUI) { newDescription ->
                TeamService.setDescription(player, newDescription)
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }

    // Tag
    setItem(TeamSettingItem.tag) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }
        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.tagTitle ?: "")
            val label = applyMiniColor(TeamSettingItem.tagLabel ?: "")
            val inputItem = TeamSettingItem.tagInputItem ?: GuiItem(Material.NAME_TAG)
            val outputItem = TeamSettingItem.tagOutputItem ?: GuiItem(Material.NAME_TAG)
            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, {}, reopenGUI) { newTag ->
                TeamService.setTag(player, newTag)
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }
    // Status
    val statusItem = if (team.isOpen) TeamSettingItem.statusOpen else TeamSettingItem.statusClosed

    setItem(statusItem) { event ->
        // Toggle status
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }

        team.isOpen = !team.isOpen
        event.item = if (team.isOpen) TeamSettingItem.statusOpen else TeamSettingItem.statusClosed
        event.item?.smallCapsName = setting.smallCapsItemName
        event.item?.smallCapsLore = setting.smallCapsItemLore
        event.update()
    }

    // Anchor
    val anchorItem = TeamSettingItem.anchor.apply {
        this?.customPlaceholder = teamToPlaceholderMap(team)
    }
    setItem(anchorItem) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }

        // Toggle home anchor
        if (team.teamHome != null) {
            team.setAnchored(!team.isAnchored)
            GUIManager.openTeamSettingGUI(player)
        }
    }

    // Title
    setItem(TeamSettingItem.title) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }

        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.titleTitle ?: "")
            val label = applyMiniColor(TeamSettingItem.titleLabel ?: "")
            val inputItem = TeamSettingItem.titleInputItem ?: GuiItem(Material.PAPER)
            val outputItem = TeamSettingItem.titleOutputItem ?: GuiItem(Material.PAPER)

            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, {}, reopenGUI) { newTitle ->
                TeamService.setTitle(player, newTitle)
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }

    // PvP
    setItem(TeamSettingItem.pvp) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }
        // Toggle friendly fire
        TeamService.togglePvp(player)
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            event.update()
        }, 2)
    }

    // Ban List
    setItem(TeamSettingItem.banList) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event)
            return@setItem
        }

        // Open ban list management GUI
//        GUIManager.openBanListGUI(player)
    }

    // Disband
    if (teamPlayer.rank == PlayerRank.OWNER) setItem(TeamSettingItem.disband) {
        // Open disband confirmation GUI
        GUIManager.openTeamDisbandGUI(player)
    }

}





