package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.Team
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry

object TeamWarpsManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("team_warps_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            val team = Team.getTeam(player) ?: return@register emptyList()

            val baseTemplate = templatesMap["warp-item"] ?: return@register emptyList()

            team.warps.convertedList.map { warp ->
                val warpPlaceholders = mapOf("warp" to warp)
                baseTemplate.clone().apply {
                    this.style.placeholder.putAll(warpPlaceholders)
                    this.style.viewer = null
                }
            }
        }
    }
}