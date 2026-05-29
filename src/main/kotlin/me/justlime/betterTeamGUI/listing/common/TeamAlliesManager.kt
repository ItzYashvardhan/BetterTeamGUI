package me.justlime.betterTeamGUI.listing.common

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.listing.IPopulator
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import java.util.UUID

object TeamAlliesManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("allies_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            // Get the team. If target data exists (Public View), use it. Otherwise use viewer (Private View).
            val targetPlayer = response.setting.style.offlinePlayer ?: player
            val team = Team.getTeam(targetPlayer) ?: return@register emptyList()

            // Fetch the Base Template
            val baseTemplate = templatesMap["allies-item"] ?: return@register emptyList()

            // Ally Lookup (Handles both UUIDs and Names safely)
            val allies = team.allies.convertedList.mapNotNull { allyStr ->
                try {
                    val id = UUID.fromString(allyStr)
                    Team.getTeam(id)
                } catch (e: Exception) {
                    Team.getTeamManager().getTeam(allyStr)
                }
            }

            // Map Data to GUI Items
            allies.mapNotNull { allyTeam ->
                val ownerRank = allyTeam.members.getRank(PlayerRank.OWNER)
                
                // Safely grab an owner (or skip if the team has no owner for some reason)
                val randomAllyOwner = ownerRank.randomOrNull()?.player ?: return@mapNotNull null

                // PLACEHOLDERS: Provides every variable needed by BOTH YAML files
                val allyPlaceholders = mapOf(
                    "team" to allyTeam.name,
                    "color" to allyTeam.color.name.lowercase(),
                    "ally_uuid" to (allyTeam.id?.toString() ?: ""),
                    "tag" to (allyTeam.tag ?: ""),
                    "team_size" to allyTeam.members.size().toString(),
                    "team_score" to allyTeam.score.toString()
                )

                baseTemplate.clone().apply {
                    this.style.offlinePlayer = randomAllyOwner
                    this.style.placeholder.putAll(allyPlaceholders)
                    this.style.viewer = null
                }
            }
        }
    }
}