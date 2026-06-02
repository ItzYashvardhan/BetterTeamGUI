package me.justlime.betterTeamGUI.listing.inbox

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.listing.IPopulator
import me.justlime.betterTeamGUI.utilities.uuidMapper
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import org.bukkit.Bukkit
import java.util.UUID

object TeamInvitationManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("invitation_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates
            val team = Team.getTeam(player) ?: return@register emptyList()
            val baseTemplate = templatesMap["invitation-item"] ?: return@register emptyList()
            team.invitedPlayers.map { uUID ->
                uuidMapper(uUID, baseTemplate)
            }
        }
    }
}

