package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamDashboardItem
import me.justlime.betterTeamGUI.gui.items.TeamMoneyItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

fun teamDashboard(setting: GUISetting, player: Player, team: Team, teamPlayer: TeamPlayer) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        TeamDashboardItem.background.forEach { setItem(it) }

        val chatItem = when {
            teamPlayer.isInAllyChat -> TeamDashboardItem.allyChatItem
            teamPlayer.isInTeamChat -> TeamDashboardItem.teamChatItem
            else -> TeamDashboardItem.chatItem
        }
        setItem(chatItem) { event ->
            if (event.click.isRightClick) {
                // Toggle Ally Chat
                val newState = !teamPlayer.isInAllyChat
                teamPlayer.setAllyChat(newState)
                teamPlayer.setTeamChat(false)
            }
            if (event.click.isLeftClick) {
                // Toggle Team Chat
                val newState = !teamPlayer.isInTeamChat
                teamPlayer.setTeamChat(newState)
                teamPlayer.setAllyChat(false)
            }

            event.item = when {
                teamPlayer.isInAllyChat -> TeamDashboardItem.allyChatItem
                teamPlayer.isInTeamChat -> TeamDashboardItem.teamChatItem
                else -> TeamDashboardItem.chatItem
            }
            event.update(setting.style)
        }

        val infoItem = if (team.description.isBlank()) TeamDashboardItem.infoItemWithoutDesc else TeamDashboardItem.infoItemWithDesc
        setItem(infoItem) {
            if (it.click.isLeftClick) GUIManager.openTeamLeaderBoardGUI(it.whoClicked as Player, team)
            if (it.click.isRightClick) GUIManager.openTeamLevelGUI(player, team)
        }


        setItem(TeamDashboardItem.homeItem) {
            when (it.click) {
                ClickType.LEFT -> {
                    TeamService.teleportToHome(player)
                    player.closeInventory()
                }

                ClickType.RIGHT -> {
                    if (team.teamHome != null) GUIManager.openTeamUpdateHomeGUI(player)
                    else {
                        TeamService.setHome(player)
                        player.closeInventory()
                    }
                }

                ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT -> {
                    GUIManager.openTeamDeleteHomeGUI(player)
                }

                else -> Unit

            }

        }

        setItem(TeamDashboardItem.balanceItem) { clickEvent ->
            if (clickEvent.isLeftClick) {
                depositOrWithdrawMoneyAnvilUI(clickEvent.whoClicked as Player, true)
            }
            if (clickEvent.isRightClick) {
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(clickEvent, setting.style)
                    return@setItem
                }
                depositOrWithdrawMoneyAnvilUI(clickEvent.whoClicked as Player, false)
            }
        }

        setItem(TeamDashboardItem.warpItem) {
            val player = it.whoClicked as? Player ?: return@setItem
            GUIManager.openTeamWarpGUI(player)
        }

        setItem(TeamDashboardItem.membersItem) {
            GUIManager.openTeamMemberGUI(it.whoClicked as Player, team)
        }

        setItem(TeamDashboardItem.enderChestItem) {
            TeamService.openTeamEnderChest(it.whoClicked as Player)
        }

        setItem(TeamDashboardItem.allyItem) {
            GUIManager.openTeamAlliesListGUI(player, team)
        }

        if (teamPlayer.rank != PlayerRank.OWNER) setItem(TeamDashboardItem.leaveItem) {
            GUIManager.openTeamLeaveGUI(it.whoClicked as Player)
        }

        setItem(TeamDashboardItem.listItem) {
            GUIManager.openTeamListGUI(it.whoClicked as Player)
        }

        setItem(TeamDashboardItem.settingItem) {
            GUIManager.openTeamSettingGUI(it.whoClicked as Player)
        }

    }.open(player)
}

fun depositOrWithdrawMoneyAnvilUI(player: Player, isReceiving: Boolean = false) {
    val title = applyMiniColor((if (isReceiving) TeamMoneyItem.depositTitle else TeamMoneyItem.withdrawTitle) ?: "")
    val label = applyMiniColor((if (isReceiving) TeamMoneyItem.depositLabel else TeamMoneyItem.withdrawLabel) ?: "")
    val inputItem = if (isReceiving) TeamMoneyItem.depositInputItem else TeamMoneyItem.withdrawInputItem
    val outputItem = if (isReceiving) TeamMoneyItem.depositOutputItem else TeamMoneyItem.withdrawOutputItem

    openAnvilGUI(
        player = player,
        title = title,
        label = label,
        inputItem = inputItem ?: GuiItem(Material.ANVIL),
        outputItem = outputItem ?: GuiItem(Material.ANVIL),
        onInvalidInput = { GUIManager.openTeamGUI(player) },
        onCancel = { GUIManager.openTeamGUI(player) }) { amountString ->
        val amount = amountString.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            // Handle invalid amount input
            val msg = ConfigManager.messages.getString("money.invalid-amount") ?: ""
            player.sendMessage(msg)
            GUIManager.openTeamGUI(player)
            return@openAnvilGUI
        }

        if (isReceiving) TeamService.depositAmount(player, amountString) else TeamService.withdrawAmount(player, amountString)
        GUIManager.openTeamGUI(player)
    }

}



