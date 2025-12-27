package me.justlime.betterTeamGUI.gui.pages

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.InviteViewItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyBackground
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import net.justlime.limeframegui.models.GuiSetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

fun inviteView(setting: GuiSetting, player: Player, team: Team, searchQuery: String? = null) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }

        applyBackground(InviteViewItem, this, true) {
            GUIManager.openTeamMemberGUI(player, team)
        }

        val onlinePlayers = Bukkit.getOnlinePlayers().filter {
            !Team.getTeamManager().isInTeam(it) && it.uniqueId != player.uniqueId
        }.filter { searchQuery == null || it.name.contains(searchQuery, ignoreCase = true) }

        val invitedPlayers = team.invitedPlayers
        val invitedListItem = InviteViewItem.invitedListItem?.copy()?.apply {
            style.placeholder = mutableMapOf("{team_invites}" to invitedPlayers.size.toString())
        } ?: GuiItem(Material.WRITABLE_BOOK)
        setItem(invitedListItem) {
            GUIManager.openInviteListGUI(player, team)
        }

        val inviteBtn = InviteViewItem.inviteItemBtn?.copy()
        if (inviteBtn != null) {
            setItem(inviteBtn) {
                openAnvilGUI(
                    player = player,
                    title = applyMiniColor(InviteViewItem.inviteTitle),
                    label = applyMiniColor(InviteViewItem.inviteLabel),
                    inputItem = InviteViewItem.inviteInputItem ?: GuiItem(Material.PAPER),
                    outputItem = InviteViewItem.inviteOutputItem ?: GuiItem(Material.LIME_DYE),
                    onInvalidInput = { inviteView(setting, player, team, searchQuery) },
                    onCancel = { inviteView(setting, player, team, searchQuery) },
                    onConfirm = { targetName ->
                        TeamService.invitePlayer(player, targetName)
                        GUIManager.closeInventory(player)
                    })
            }
        }

        val searchBtn = if (searchQuery == null) InviteViewItem.searchItem?.copy() else InviteViewItem.searchItemClear?.copy()

        if (searchBtn != null) {
            setItem(InviteViewItem.searchItem) { click ->
                if (click.isRightClick || click.isShiftClick) {
                    inviteView(setting, player, team, null)
                } else {
                    openAnvilGUI(
                        player = player,
                        title = applyMiniColor(InviteViewItem.searchTitle),
                        label = applyMiniColor(InviteViewItem.searchLabel),
                        inputItem = InviteViewItem.searchInputItem ?: GuiItem(Material.PAPER),
                        outputItem = InviteViewItem.searchOutputItem ?: GuiItem(Material.NAME_TAG),
                        onInvalidInput = { inviteView(setting, player, team, searchQuery) },
                        onCancel = { inviteView(setting, player, team, searchQuery) },
                        onConfirm = { query ->
                            inviteView(setting, player, team, query)
                        })
                }
            }
        }

        addPage {
            if (onlinePlayers.isEmpty()) {
                return@addPage
            }

            onlinePlayers.forEach { targetPlayer ->
                val inviteItem = if (!team.isInvited(targetPlayer.uniqueId)) InviteViewItem.inviteItem?.copy()?.apply {
                    texture = "[${targetPlayer.uniqueId}]"
                    style.placeholder = mutableMapOf("{player}" to targetPlayer.name)
                } ?: GuiItem(Material.PAPER) else InviteViewItem.playerInvitedItem?.copy()?.apply {
                    texture = "[${targetPlayer.uniqueId}]"
                    style.placeholder = mutableMapOf("{player}" to targetPlayer.name)
                } ?: GuiItem(Material.PAPER)

                addItem(inviteItem) {
                    if (!team.isInvited(targetPlayer.uniqueId)) {
                        TeamService.invitePlayer(player, targetPlayer.name)
                        foliaLib.scheduler.runLater(Runnable {
                            GUIManager.openInvitePlayerGUI(player, team)
                        }, 3)
                    }
                }
            }
        }

    }.open(player)
}