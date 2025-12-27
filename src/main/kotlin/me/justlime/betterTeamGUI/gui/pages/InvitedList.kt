package me.justlime.betterTeamGUI.gui.pages

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.InvitedPlayersListItem
import me.justlime.betterTeamGUI.utilities.applyBackground
import net.justlime.limeframegui.models.GuiSetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

fun invitedListView(setting: GuiSetting, player: Player, team: Team) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }

        applyBackground(InvitedPlayersListItem, this, true) {
            GUIManager.openInvitePlayerGUI(player, team)
        }

        val invitedPlayers = team.invitedPlayers

        addPage {
            if (invitedPlayers.isEmpty()) {
                return@addPage
            }

            invitedPlayers.forEach { uuid ->
                val invitedPlayer = Bukkit.getOfflinePlayer(uuid)
                val invitedPlayerItem = InvitedPlayersListItem.invitedPlayerItem?.copy()?.apply {
                    texture = "[${invitedPlayer.uniqueId}]"
                    style.placeholder["{player}"] = invitedPlayer.name.toString()
                } ?: GuiItem(Material.PAPER)

                addItem(invitedPlayerItem) {
                    team.invitedPlayers.remove(uuid)
                    GUIManager.openInviteListGUI(player, team)
                }

            }
        }
    }.open(player)
}
