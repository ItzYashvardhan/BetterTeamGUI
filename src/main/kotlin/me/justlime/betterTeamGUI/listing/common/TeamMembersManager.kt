package me.justlime.betterTeamGUI.listing.common

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.listing.IPopulator
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry

object TeamMembersManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("members_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            // Get the team. If there is target data (Public View), use it. Otherwise use viewer (Private View).
            val targetPlayer = response.setting.style.offlinePlayer ?: player
            val team = Team.getTeam(targetPlayer) ?: return@register emptyList()

            // Check the viewer's rank inside THIS specific team
            val isOwner = team.members.getRank(PlayerRank.OWNER).any { it.playerUUID == player.uniqueId }
            val isAdmin = isOwner || team.members.getRank(PlayerRank.ADMIN).any { it.playerUUID == player.uniqueId }

            // Fetch all members and sort them by rank
            val members = mutableListOf<TeamPlayer>()
            members.addAll(team.members.getRank(PlayerRank.OWNER))
            members.addAll(team.members.getRank(PlayerRank.ADMIN))
            members.addAll(team.members.getRank(PlayerRank.DEFAULT))

            // Safely fetch templates (Adapts to which YAML called it)
            val memberItemTemplate = templatesMap["member_item"] ?: templatesMap["member_view_item"] ?: return@register emptyList()
            val memberItemNoAdminTemplate = templatesMap["member-item-no-admin"] ?: memberItemTemplate
            val inviteButtonTemplate = templatesMap["invite-button"] // Might be null for public view
            val lockedInviteTemplate = templatesMap["locked-invite"] // Might be null for public view

            // Generate Member Items
            val memberItems = members.map { teamPlayer ->
                val memberOfflinePlayer = teamPlayer.player
                val memberPlaceholders = mapOf(
                    "team_player" to (memberOfflinePlayer.name ?: "Unknown"),
                    "rank" to teamPlayer.rank.name
                )
                val template = if (isAdmin) memberItemTemplate else memberItemNoAdminTemplate
                template.clone().apply {
                    this.style.offlinePlayer = memberOfflinePlayer
                    this.style.placeholder.putAll(memberPlaceholders)
                    this.style.viewer = null
                }
            }

            // Generate Invite Slots (ONLY if the template exists in the YAML)
            val inviteItems = if (inviteButtonTemplate != null) {
                val availableSlots = (team.teamLimit - team.members.size()).coerceAtLeast(0)
                (1..availableSlots).map { inviteButtonTemplate.clone() }
            } else {
                emptyList()
            }

            // Generate Locked Slots (ONLY if the template exists in the YAML)
            val lockedItems = if (lockedInviteTemplate != null) {
                val lockedSlots = (team.level * 2 - team.teamLimit).coerceAtLeast(0)
                (1..lockedSlots).map {
                    val placeholders = mapOf("team_level" to (team.level + 1).toString())
                    lockedInviteTemplate.clone().apply {
                        this.style.placeholder.putAll(placeholders)
                    }
                }
            } else {
                emptyList()
            }

            // Return the combined list
            memberItems + inviteItems + lockedItems
        }
    }
}