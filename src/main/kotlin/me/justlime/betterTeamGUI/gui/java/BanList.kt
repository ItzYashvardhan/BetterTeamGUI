package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.BanListItem
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.bannedPlayersList
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

fun teamBanList(setting: GUISetting, player: Player, team: Team) {

    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        nav {
            prevSlot = BanListItem.prevSlot
            nextSlot = BanListItem.nextSlot
        }
        BanListItem.background.forEach { setItem(it) }

        BanListItem.backSlot.forEach { slot ->
            setItem(TeamButton.back, slot) {
                GUIManager.openTeamMemberGUI(it.whoClicked as Player,team)
            }
        }

        BanListItem.homeSlot.forEach { slot ->
            setItem(TeamButton.home, slot) {
                GUIManager.openTeamGUI(it.whoClicked as Player)
            }
        }

        addPage {
            team.bannedPlayersList.forEach { bannedPlayerUUIDString ->

                try {
                    val uuid = UUID.fromString(bannedPlayerUUIDString)
                    val bannedPlayer = Bukkit.getOfflinePlayer(uuid)

                    val item = BanListItem.bannedPlayerItem?.copy()?.apply {
                        this.texture = "[${bannedPlayer.uniqueId}]"

                        this.style.offlinePlayer = bannedPlayer
                        this.style.placeholder = mapOf(
                            "{team_player}" to (bannedPlayer.name ?: "Unknown")
                        )
                    }

                    if (item != null) {
                        addItem(item) { clickEvent ->
                            val player = clickEvent.whoClicked as Player
                            TeamService.unban(player, bannedPlayer)
                            player.closeInventory()
                        }
                    }
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }
            }
        }
    }.open(player)
}