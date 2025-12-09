package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamViewerItems
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.teamToPlaceholderMap
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamViewer(setting: GUISetting, player: Player, team: Team) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    TeamViewerItems.teamViewerBackground.forEach { setItem(it) }

    val infoItem = if (team.description.isBlank()) TeamViewerItems.teamViewerInfoWithoutDescription else TeamViewerItems.teamViewerInfoWithDescription
    setItem(infoItem?.apply {
        style.placeholder = teamToPlaceholderMap(team)
    })
    setItem(TeamButton.back, TeamViewerItems.teamViewerBackSlot) {
        GUIManager.openTeamGUI(it.whoClicked as Player)
        //Back Tracking TODO
    }

    setItem(TeamButton.home, TeamViewerItems.teamViewerHomeSlot) { GUIManager.openTeamGUI(it.whoClicked as Player) }

    setItem(TeamViewerItems.teamViewerBalance?.apply {
        style.placeholder = teamToPlaceholderMap(team)
    })

    setItem(TeamViewerItems.teamViewerMembers?.apply {
        style.placeholder = teamToPlaceholderMap(team)
    }) {
        GUIManager.openTeamViewerMembersGUI(player, team)
    }

    setItem(TeamViewerItems.teamViewerAllies?.apply {
        style.placeholder = teamToPlaceholderMap(team)

    }) {
        GUIManager.openTeamViewerAlliesGUI(player, team)
    }

}.open(player)

fun teamViewerMembers(setting: GUISetting, player: Player, team: Team) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        nav {
            prevSlot = TeamViewerItems.teamViewerMembersPrevSlot
            nextSlot = TeamViewerItems.teamViewerMembersNextSlot
            nextItem = TeamButton.next!!
            prevItem = TeamButton.prev!!
        }
        TeamViewerItems.teamViewerMembersBackground.forEach { setItem(it) }

        setItem(TeamButton.back, TeamViewerItems.teamViewerMembersBackSlot) {
            GUIManager.openTeamViewerGUI(it.whoClicked as Player, team)
        }

        setItem(TeamButton.home, TeamViewerItems.teamViewerMembersHomeSlot) {
            GUIManager.openTeamGUI(it.whoClicked as Player)
        }

        addPage {
            team.members.get().forEach { teamPlayer ->
                val item = TeamViewerItems.teamViewerMemberItem?.clone()?.apply {
                    style.offlinePlayer = teamPlayer.player
                    style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
                }
                if (item != null) {
                    addItem(item)
                }
            }
        }
    }.open(player)
}

fun teamViewerAllies(setting: GUISetting, player: Player, team: Team) {
    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        nav {
            prevSlot = TeamViewerItems.teamViewerAlliesPrevSlot
            nextSlot = TeamViewerItems.teamViewerAlliesNextSlot
            nextItem = TeamButton.next!!
            prevItem = TeamButton.prev!!
        }
        TeamViewerItems.teamViewerAlliesBackground.forEach { setItem(it) }

        setItem(TeamButton.back, TeamViewerItems.teamViewerAlliesBackSlot) {
            GUIManager.openTeamViewerGUI(it.whoClicked as Player, team)
        }

        setItem(TeamButton.home, TeamViewerItems.teamViewerAlliesHomeSlot) {
            GUIManager.openTeamGUI(it.whoClicked as Player)
        }

        addPage {
            team.allies.get().forEach { uuid ->
                val allyTeam = Team.getTeam(uuid) ?: return@forEach
                val ownerRank = team.members.getRank(PlayerRank.OWNER)
                if (ownerRank.isNotEmpty()) {
                    val owner = ownerRank.random()
                    val item = TeamViewerItems.teamViewerAllyItem?.copy()?.apply {
                        texture = "[${owner.playerUUID}]"
                        val placeholder = teamToPlaceholderMap(allyTeam).toMutableMap()
                        placeholder["{team_ally}"] = (allyTeam.name ?: "Unknown")
                        style.placeholder = placeholder
                    }

                    if (item != null) {
                        addItem(item) {
                            if (it.click.isLeftClick) GUIManager.openTeamViewerGUI(player, allyTeam)
                        }
                    }
                }
            }
        }
    }.open(player)
}