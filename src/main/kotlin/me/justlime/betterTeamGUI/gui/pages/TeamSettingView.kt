package me.justlime.betterTeamGUI.gui.pages

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamSettingItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyBackground
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.models.GuiSetting
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamSettingView(setting: GuiSetting, player: Player, team: Team, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(setting) {
    onClick {
        it.isCancelled = true
    }
    applyBackground(TeamSettingItem, this, false) {
        GUIManager.openTeamGUI(player)
    }
    setting.style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer) // TODO ":( didn't work please someone fix it"

    // Color Picker
    setItem(TeamSettingItem.colorPicker?.apply {
        style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    }) { event ->
        if (teamPlayer.rank != PlayerRank.OWNER) {
            permissionDenied(event, setting.style)
            return@setItem
        }
        GUIManager.openColorPickerGUI(player)
    }

    // Description
    setItem(TeamSettingItem.description?.apply {
        style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    }) { event ->
        if (teamPlayer.rank != PlayerRank.OWNER) {
            permissionDenied(event, setting.style)
            return@setItem
        }
        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.descriptionTitle)
            val label = applyMiniColor(TeamSettingItem.descriptionLabel)
            val inputItem = TeamSettingItem.descriptionInputItem ?: GuiItem(Material.PAPER)
            val outputItem = TeamSettingItem.descriptionOutputItem ?: GuiItem(Material.PAPER)
            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, { }, reopenGUI) { newDescription ->
                TeamService.setDescription(player, newDescription)
                foliaLib.scheduler.runLater(Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }

    // Tag
    setItem(TeamSettingItem.tag?.apply {
        style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    }) { event ->
        if (teamPlayer.rank != PlayerRank.OWNER) {
            permissionDenied(event, setting.style)
            return@setItem
        }
        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.tagTitle)
            val label = applyMiniColor(TeamSettingItem.tagLabel)
            val inputItem = TeamSettingItem.tagInputItem ?: GuiItem(Material.NAME_TAG)
            val outputItem = TeamSettingItem.tagOutputItem ?: GuiItem(Material.NAME_TAG)
            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, {}, reopenGUI) { newTag ->
                TeamService.setTag(player, newTag)
                foliaLib.scheduler.runLater(Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }
    // Status
    val statusItem = if (team.isOpen) TeamSettingItem.statusOpen else TeamSettingItem.statusClosed

    setItem(statusItem) { event ->
        // Toggle status
        if (teamPlayer.rank != PlayerRank.OWNER) {
            permissionDenied(event, setting.style)
            return@setItem
        }

        team.isOpen = !team.isOpen
        event.item = if (team.isOpen) TeamSettingItem.statusOpen else TeamSettingItem.statusClosed
        event.update(setting.style)
    }

    // Anchor
    val anchorItem = if (team.teamHome != null) TeamSettingItem.anchor else TeamSettingItem.noAnchor

    setItem(anchorItem?.apply {
        style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    }) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event, setting.style)
            return@setItem
        }

        // Toggle home anchor
        if (team.teamHome != null) {
            team.setAnchored(!team.isAnchored)
            GUIManager.openTeamSettingGUI(player)
        }
    }

    // Title
    setItem(TeamSettingItem.title?.apply {
        style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    }) { event ->
        if (teamPlayer.rank != PlayerRank.OWNER) {
            permissionDenied(event, setting.style)
            return@setItem
        }

        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.titleTitle)
            val label = applyMiniColor(TeamSettingItem.titleLabel)
            val inputItem = TeamSettingItem.titleInputItem ?: GuiItem(Material.PAPER)
            val outputItem = TeamSettingItem.titleOutputItem ?: GuiItem(Material.PAPER)

            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, {}, reopenGUI) { newTitle ->
                TeamService.setTitle(player, newTitle)
                foliaLib.scheduler.runLater(Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }

    // PvP
    setItem(TeamSettingItem.pvp?.apply {
        style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    }) { event ->
        if (teamPlayer.rank == PlayerRank.DEFAULT) {
            permissionDenied(event, setting.style)
            return@setItem
        }
        // Toggle friendly fire
        TeamService.togglePvp(player)
        foliaLib.scheduler.runLater(Runnable {
            GUIManager.openTeamSettingGUI(player)
        }, 4)
    }

    // Rename
    val renameItem = TeamSettingItem.rename?.apply {
        style.placeholder = TeamService.teamToPlaceholderMap(team)
    }
    setItem(renameItem) { event ->
        if (teamPlayer.rank != PlayerRank.OWNER) {
            permissionDenied(event, setting.style)
            return@setItem
        }

        (event.whoClicked as? Player)?.let { player ->
            val title = applyMiniColor(TeamSettingItem.renameTitle)
            val label = applyMiniColor(TeamSettingItem.renameLabel)
            val inputItem = TeamSettingItem.renameInputItem ?: GuiItem(Material.ANVIL)
            val outputItem = TeamSettingItem.renameOutputItem ?: GuiItem(Material.ANVIL)

            val reopenGUI = { GUIManager.openTeamSettingGUI(player) }

            openAnvilGUI(player, title, label, inputItem, outputItem, {}, reopenGUI) { newName ->
                TeamService.rename(player, newName)
                foliaLib.scheduler.runLater(Runnable {
                    reopenGUI()
                }, 2)
            }
        }
    }

    // Disband
    if (teamPlayer.rank == PlayerRank.OWNER) setItem(TeamSettingItem.disband) {
        // Open disband confirmation GUI
        GUIManager.openTeamDisbandGUI(player)
    }

}





