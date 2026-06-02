package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.utilities.uuidMapper
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import java.util.*

object TeamBanManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("team_ban_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates
            val team = Team.getTeam(player) ?: return@register emptyList()
            val baseTemplate = templatesMap["ban-item"] ?: return@register emptyList()
            team.bannedPlayers.convertedList.map { bannedPlayerUUID ->
                val uuid = UUID.fromString(bannedPlayerUUID)
                uuidMapper(uuid, baseTemplate)
            }
        }
    }
}