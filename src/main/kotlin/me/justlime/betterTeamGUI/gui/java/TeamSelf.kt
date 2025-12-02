package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import com.booksaw.betterTeams.commands.team.EchestCommand
import com.booksaw.betterTeams.commands.team.HomeCommand
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamViewItem
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.entity.Player

fun teamSelf(guiSetting: GUISetting, team: Team, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {

    val chatItem = when {
        teamPlayer.isInAllyChat -> TeamViewItem.allyChatItem
        teamPlayer.isInTeamChat -> TeamViewItem.teamChatItem
        else -> TeamViewItem.chatItem
    }
    onClick { it.isCancelled = true }
    TeamViewItem.background.forEach { setItem(it) }


    setItem(chatItem) { event ->
        if (event.click.isLeftClick) {
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

        event.item = when {
            teamPlayer.isInAllyChat -> TeamViewItem.allyChatItem
            teamPlayer.isInTeamChat -> TeamViewItem.teamChatItem
            else -> TeamViewItem.chatItem
        }
        event.update()
    }

    setItem(TeamViewItem.infoItem)

    setItem(TeamViewItem.homeItem) {
        HomeCommand().onCommand(teamPlayer, "", emptyArray(), team)
    }

    setItem(TeamViewItem.balanceItem) {}

    setItem(TeamViewItem.warpItem) {
        val player = it.whoClicked as? Player ?: return@setItem
        GUIManager.openTeamWarpGUI(player)
    }

    setItem(TeamViewItem.membersItem) {}

    setItem(TeamViewItem.enderChestItem) {
        EchestCommand().onCommand(teamPlayer, "", emptyArray(), team)
    }

    setItem(TeamViewItem.allyItem) {}

    setItem(TeamViewItem.leaveItem) {}

    setItem(TeamViewItem.listItem) {
        GUIManager.openTeamListGUI(it.whoClicked as Player)
    }

    setItem(TeamViewItem.settingItem) {}

}


