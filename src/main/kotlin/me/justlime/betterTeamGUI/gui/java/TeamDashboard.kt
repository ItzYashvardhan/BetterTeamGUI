package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
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
import org.bukkit.event.inventory.ClickType

fun teamDashboard(setting: GUISetting, player: Player, team: Team, teamPlayer: TeamPlayer) {
    ChestGUI(setting) {
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
//            event.item?.styleSheet?.stylishName = setting.styleSheet?.stylishName ?: LimeFrameAPI.keys.stylishName
//            event.item?.styleSheet?.stylishLore = setting.styleSheet?.stylishLore ?: LimeFrameAPI.keys.stylishLore
            event.update(setting.style)
        }

//    setItem(TeamViewItem.infoItem)
        val infoItem = if (team.description.isBlank()) TeamViewItem.infoItemWithoutDesc else TeamViewItem.infoItemWithDesc
        setItem(infoItem)


        setItem(TeamViewItem.homeItem) {
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

        setItem(TeamViewItem.balanceItem) { clickEvent ->
            if (clickEvent.isLeftClick) {
                depositOrWithdrawMoneyAnvilUI(clickEvent.whoClicked as Player, true)
            }
            if (clickEvent.isRightClick) {
                depositOrWithdrawMoneyAnvilUI(clickEvent.whoClicked as Player, false)
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

        if (teamPlayer.rank != PlayerRank.OWNER) setItem(TeamViewItem.leaveItem) {
            GUIManager.openTeamLeaveGUI(it.whoClicked as Player)
        }

        setItem(TeamViewItem.listItem) {
            GUIManager.openTeamListGUI(it.whoClicked as Player)
        }

        setItem(TeamViewItem.settingItem) {
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



