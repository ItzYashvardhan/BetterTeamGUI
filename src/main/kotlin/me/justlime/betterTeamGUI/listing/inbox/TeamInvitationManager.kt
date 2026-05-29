package me.justlime.betterTeamGUI.listing.inbox

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.listing.IPopulator
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import org.bukkit.Bukkit

object TeamInvitationManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("invitation_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            val team = Team.getTeam(player) ?: return@register emptyList()

            val baseTemplate = templatesMap["invitation-item"] ?: return@register emptyList()

            team.invitedPlayers.map { uUID ->
                val invitedPlayer = Bukkit.getOfflinePlayer(uUID)
                val invitationPlaceholders = mapOf(
                    "team_player" to (invitedPlayer.name ?: "Unknown")
                )
                baseTemplate.clone().apply {
                    this.style.offlinePlayer = invitedPlayer
                    this.style.placeholder.putAll(invitationPlaceholders)
                    this.style.viewer = null
                }
            }
        }
    }
}