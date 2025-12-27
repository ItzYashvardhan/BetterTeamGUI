package me.justlime.betterTeamGUI.gui.pages

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamDashboardItem
import me.justlime.betterTeamGUI.gui.items.TeamMoneyItem
import me.justlime.betterTeamGUI.gui.items.TeamSettingItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GuiSetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

fun teamDashboard(setting: GuiSetting, player: Player, team: Team, teamPlayer: TeamPlayer) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        TeamDashboardItem.background.forEach { setItem(it) }

        val chatItem = when {
            teamPlayer.isInAllyChat -> TeamDashboardItem.allyChatItem
            teamPlayer.isInTeamChat -> TeamDashboardItem.teamChatItem
            else -> TeamDashboardItem.chatItem
        }
        setItem(chatItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) { event ->
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
        setItem(infoItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            if (it.click.isLeftClick) GUIManager.openTeamLeaderBoardGUI(it.whoClicked as Player, team)
            if (it.click.isRightClick) GUIManager.openTeamLevelGUI(player, team)
        }


        setItem(TeamDashboardItem.homeItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            when (it.click) {

                ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT -> {
                    if (teamPlayer.rank == PlayerRank.DEFAULT) {
                        permissionDenied(it, setting.style)
                        return@setItem
                    }
                    if (team.teamHome == null) {
                        val homeItem = TeamSettingItem.noAnchor?.clone()?.apply {
                            slot = TeamDashboardItem.homeItem?.slot
                            slotList = TeamDashboardItem.homeItem?.slotList.orEmpty()
                        }
                        val oldItem = it.item
                        it.item = homeItem
                        it.update()
                        foliaLib.scheduler.runLater(Runnable {
                            it.item = oldItem
                            it.update(setting.style)
                        }, 30)
                        return@setItem
                    }
                    GUIManager.openTeamDeleteHomeGUI(player)
                    return@setItem

                }

                ClickType.LEFT -> {
                    if (team.teamHome == null) {
                        val homeItem = TeamSettingItem.noAnchor?.clone()?.apply {
                            slot = TeamDashboardItem.homeItem?.slot
                            slotList = TeamDashboardItem.homeItem?.slotList.orEmpty()
                        }
                        val oldItem = it.item
                        it.item = homeItem
                        it.update()
                        foliaLib.scheduler.runLater(Runnable {
                            it.item = oldItem
                            it.update(setting.style)
                        }, 30)
                        return@setItem
                    }
                    TeamService.teleportToHome(player)
                    player.closeInventory()
                    return@setItem
                }

                ClickType.RIGHT -> {
                    if (teamPlayer.rank == PlayerRank.DEFAULT) {
                        permissionDenied(it, setting.style)
                        return@setItem
                    }
                    if (team.teamHome != null) GUIManager.openTeamUpdateHomeGUI(player)
                    else {
                        TeamService.setHome(player)
                        player.closeInventory()
                    }
                    return@setItem
                }

                else -> Unit

            }

        }

        setItem(TeamDashboardItem.balanceItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) { clickEvent ->
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

        setItem(TeamDashboardItem.warpItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            val player = it.whoClicked as? Player ?: return@setItem
            GUIManager.openTeamWarpGUI(player)
        }

        setItem(TeamDashboardItem.membersItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            GUIManager.openTeamMemberGUI(it.whoClicked as Player, team)
        }

        setItem(TeamDashboardItem.enderChestItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            TeamService.openTeamEnderChest(it.whoClicked as Player)
        }

        setItem(TeamDashboardItem.allyItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            GUIManager.openTeamAlliesListGUI(player, team)
        }

        if (teamPlayer.rank != PlayerRank.OWNER) setItem(TeamDashboardItem.leaveItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            GUIManager.openTeamLeaveGUI(it.whoClicked as Player)
        }

        setItem(TeamDashboardItem.listItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            GUIManager.openTeamListGUI(it.whoClicked as Player)
        }

        setItem(TeamDashboardItem.settingItem?.apply {
            style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
        }) {
            if (teamPlayer.rank == PlayerRank.DEFAULT) {
                permissionDenied(it, setting.style)
                return@setItem
            }
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



