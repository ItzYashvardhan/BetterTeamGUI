package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamMoneyItem
import me.justlime.betterTeamGUI.gui.items.TeamViewItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamView(setting: GUISetting, team: Team, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(setting) {

    onClick { it.isCancelled = true }
    TeamViewItem.background.forEach { setItem(it) }

    val chatItem = when {
        teamPlayer.isInAllyChat -> TeamViewItem.allyChatItem
        teamPlayer.isInTeamChat -> TeamViewItem.teamChatItem
        else -> TeamViewItem.chatItem
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
            teamPlayer.isInAllyChat -> TeamViewItem.allyChatItem
            teamPlayer.isInTeamChat -> TeamViewItem.teamChatItem
            else -> TeamViewItem.chatItem
        }
        event.update()
    }

    setItem(TeamViewItem.infoItem)

    setItem(TeamViewItem.homeItem) {
        val player = it.whoClicked as? Player ?: return@setItem
        if (!it.click.isShiftClick) {
            TeamService.teleportToHome(player)
            player.closeInventory()
        }
    }

    setItem(TeamViewItem.balanceItem) { clickEvent ->
        if (clickEvent.isLeftClick) {
            depositOrWithdrawMoneyAnvilUI(team, teamPlayer, clickEvent.whoClicked as Player, true)
        }
        if (clickEvent.isRightClick) {
            depositOrWithdrawMoneyAnvilUI(team, teamPlayer, clickEvent.whoClicked as Player, false)
        }
    }

    setItem(TeamViewItem.warpItem) {
        val player = it.whoClicked as? Player ?: return@setItem
        GUIManager.openTeamWarpGUI(player)
    }

    setItem(TeamViewItem.membersItem) {
        GUIManager.openTeamMemberGUI(it.whoClicked as Player, team)
    }

    setItem(TeamViewItem.enderChestItem) {
        TeamService.openTeamEnderChest(it.whoClicked as Player)
    }

    setItem(TeamViewItem.allyItem) {}

    setItem(TeamViewItem.leaveItem) {
        GUIManager.openTeamLeaveGUI(it.whoClicked as Player)
    }

    setItem(TeamViewItem.listItem) {
        GUIManager.openTeamListGUI(it.whoClicked as Player)
    }

    setItem(TeamViewItem.settingItem) {}

}

fun depositOrWithdrawMoneyAnvilUI(team: Team, teamPlayer: TeamPlayer, player: Player, isReceiving: Boolean = false) {
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



