package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.commands.team.EchestCommand
import com.booksaw.betterTeams.commands.team.HomeCommand
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.config.Service
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.setItem
import org.bukkit.entity.Player

class TeamSelfGUI(val row: Int, val title: String) {
    val teamViewConfig = ConfigManager.teamViewConfig
    fun open(player: Player) {
        ChestGUI(row, title) {
            val team = Team.getTeam(player) ?: return@ChestGUI
            val teamPlayer = team.getTeamPlayer(player) ?: return@ChestGUI

            onClick { it.isCancelled = true }

            val homeItem = teamViewConfig.loadItem("home")
            val balanceItem = teamViewConfig.loadItem("balance")
            val warpItem = teamViewConfig.loadItem("warp")
            val membersItem = teamViewConfig.loadItem("members")
            val enderChestItem = teamViewConfig.loadItem("enderchest")
            val allyItem = teamViewConfig.loadItem("ally")
            val leaveItem = teamViewConfig.loadItem("leave")
            val listItem = teamViewConfig.loadItem("list")
            val settingItem = teamViewConfig.loadItem("setting")

            var chatItem = when {
                teamPlayer.isInAllyChat -> teamViewConfig.loadItem("ally_chat_enabled") ?: teamViewConfig.loadItem("chat")
                teamPlayer.isInTeamChat -> teamViewConfig.loadItem("team_chat_enabled") ?: teamViewConfig.loadItem("chat")
                else -> teamViewConfig.loadItem("chat")
            }

            var pvpItem = when {
                team.isPvp -> teamViewConfig.loadItem("pvp")
                else -> teamViewConfig.loadItem("pvp_disabled") ?: teamViewConfig.loadItem("pvp")
            }
            var appliedPlaceholderPvpItem = pvpItem?.copy()?.apply {
                this.lore = this.lore.map { Service.applyLocalPlaceHolder(it, team, teamPlayer) }.toMutableList()
            }


            setItem(chatItem) { event ->
                if (event.click.isShiftClick) {
                    // Toggle Ally Chat
                    val newState = !teamPlayer.isInAllyChat
                    teamPlayer.setAllyChat(newState)
                    teamPlayer.setTeamChat(false)
                } else {
                    // Toggle Team Chat
                    val newState = !teamPlayer.isInTeamChat
                    teamPlayer.setTeamChat(newState)
                    teamPlayer.setAllyChat(false)
                }

                // Update GUI item
                chatItem = when {
                    teamPlayer.isInAllyChat -> teamViewConfig.loadItem("ally_chat_enabled") ?: teamViewConfig.loadItem("chat")
                    teamPlayer.isInTeamChat -> teamViewConfig.loadItem("team_chat_enabled") ?: teamViewConfig.loadItem("chat")
                    else -> teamViewConfig.loadItem("chat")
                }

                event.inventory.setItem(event.slot, chatItem?.toItemStack())
                event.isCancelled = true
            }

            setItem(homeItem) {
                HomeCommand().onCommand(teamPlayer, "", emptyArray(), team)
            }
            setItem(balanceItem) {
                GUIManager.openTeamBalanceGUI(player)
            }
            setItem(warpItem) {
                GUIManager.openTeamWarpGUI(player)
            }
            setItem(membersItem) {
                GUIManager.openTeamMemberGUI(player, team, teamPlayer)
            }
            setItem(enderChestItem) {
                EchestCommand().onCommand(teamPlayer, "", emptyArray(), team)
            }
            setItem(appliedPlaceholderPvpItem) { event ->
                team.setPvp(!team.isPvp)
                pvpItem?.apply {
                    appliedPlaceholderPvpItem?.lore = this.lore.map { Service.applyLocalPlaceHolder(it, team, teamPlayer) }.toMutableList()
                    appliedPlaceholderPvpItem?.apply { event.inventory.setItem(event.slot, appliedPlaceholderPvpItem) }
                }
            }
            setItem(allyItem) {
                GUIManager.openTeamAllyGUI(player, team, teamPlayer)
            }
            setItem(leaveItem) {
                GUIManager.openTeamLeaveGUI(player)
            }
            setItem(listItem) {
                GUIManager.openTeamListGUI(player)
            }
            setItem(settingItem) {
                //TODO
            }

        }.open(player)

    }
}