package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.Team
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import org.bukkit.Bukkit
import java.util.UUID

object TeamBanManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("team_ban_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            val team = Team.getTeam(player) ?: return@register emptyList()

            val baseTemplate = templatesMap["ban-item"] ?: return@register emptyList()

            team.bannedPlayers.convertedList.map { bannedPlayerUUID ->
                val uuid = UUID.fromString(bannedPlayerUUID)
                val bannedPlayer = Bukkit.getOfflinePlayer(uuid)
                val banPlaceholders = mapOf(
                    "team_player" to (bannedPlayer.name ?: "Unknown")
                )

                baseTemplate.clone().apply {
                    this.style.offlinePlayer = bannedPlayer
                    this.style.placeholder.putAll(banPlaceholders)
                    this.style.viewer = null
                }
            }
        }
    }
}