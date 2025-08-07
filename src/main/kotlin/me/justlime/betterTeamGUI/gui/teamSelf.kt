package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import com.booksaw.betterTeams.commands.team.EchestCommand
import com.booksaw.betterTeams.commands.team.HomeCommand
import net.justlime.limeframegui.impl.ConfigHandler
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamSelf(guiSetting: GUISetting, config: ConfigHandler, team: Team, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {
    val homeItem = config.loadItem("home")
    val balanceItem = config.loadItem("balance")
    val warpItem = config.loadItem("warp")
    val membersItem = config.loadItem("members")
    val enderChestItem = config.loadItem("enderchest")
    val allyItem = config.loadItem("ally")
    val leaveItem = config.loadItem("leave")
    val listItem = config.loadItem("list")
    val settingItem = config.loadItem("setting")
    val backgroundItem = GUIManager.getBackgroundGuiItem()
    var chatItem = when {
        teamPlayer.isInAllyChat -> config.loadItem("ally_chat_enabled") ?: config.loadItem("chat")
        teamPlayer.isInTeamChat -> config.loadItem("team_chat_enabled") ?: config.loadItem("chat")
        else -> config.loadItem("chat")
    }

    this.setting.placeholderPlayer = guiSetting.placeholderPlayer
    onClick { it.isCancelled = true }
    backgroundItem.forEach { setItem(it) }
    var pvpItem = when {
        team.isPvp -> config.loadItem("pvp")
        else -> config.loadItem("pvp_disabled") ?: config.loadItem("pvp")
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
            teamPlayer.isInAllyChat -> config.loadItem("ally_chat_enabled") ?: config.loadItem("chat")
            teamPlayer.isInTeamChat -> config.loadItem("team_chat_enabled") ?: config.loadItem("chat")
            else -> config.loadItem("chat")
        }

        event.inventory.setItem(event.slot, chatItem?.toItemStack())
        event.isCancelled = true
    }

    setItem(homeItem) {
        HomeCommand().onCommand(teamPlayer, "", emptyArray(), team)
    }

    setItem(balanceItem) {}

    setItem(warpItem) {
        val player = it.whoClicked as? Player ?: return@setItem
        GUIManager.openTeamWarpGUI(player)
    }

    setItem(membersItem) {}

    setItem(enderChestItem) {
        EchestCommand().onCommand(teamPlayer, "", emptyArray(), team)
    }

    setItem(pvpItem) { event ->
        team.setPvp(!team.isPvp)
        pvpItem = when {
            team.isPvp -> config.loadItem("pvp")
            else -> config.loadItem("pvp_disabled") ?: config.loadItem("pvp")
        }
        event.inventory.setItem(event.slot, pvpItem?.toItemStack())
        event.isCancelled = true
    }

    setItem(allyItem) {}

    setItem(leaveItem) {}

    setItem(listItem) {}

    setItem(settingItem) {
    }

}


