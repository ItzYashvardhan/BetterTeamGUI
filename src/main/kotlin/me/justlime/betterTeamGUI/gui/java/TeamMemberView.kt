package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Main
import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamMemberItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyBetterTeamPlaceholderMap
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import me.justlime.betterTeamGUI.utilities.openAnvilGUI
import me.justlime.betterTeamGUI.utilities.permissionDenied
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Material
import org.bukkit.entity.Player

fun teamMemberView(setting: GUISetting, player: Player, team: Team): ChestGUI = ChestGUI(setting) {

    onClick { it.isCancelled = true }
    nav {
        nextSlot = TeamMemberItem.next
        prevSlot = TeamMemberItem.prev

    }
    TeamMemberItem.background.forEach { setItem(it) }

    setItem(TeamButton.back, TeamMemberItem.back) { GUIManager.openTeamGUI(it.whoClicked as Player) }
    setItem(TeamButton.home, TeamMemberItem.home) { GUIManager.openTeamGUI(it.whoClicked as Player) }

    val teamPlayer = team.getTeamPlayer(player) ?: return@ChestGUI
    addPage {

        team.members.get().forEach { member ->

            val item = if (teamPlayer.rank == PlayerRank.OWNER && member.rank == PlayerRank.ADMIN) TeamMemberItem.memberItem else TeamMemberItem.memberItemNoAdmin

            val finalItem = item.apply {
                this?.texture = "[${member.player.uniqueId}]"
                this?.placeholderOfflinePlayer = member.player
                this?.customPlaceholder = applyBetterTeamPlaceholderMap(team, member)
            }?.copy() ?: GuiItem(Material.PLAYER_HEAD)
            addItem(finalItem) { click ->
                val teamPlayer = team.getTeamPlayer(click.whoClicked as Player) ?: return@addItem
                if (teamPlayer.rank == PlayerRank.DEFAULT || (member.rank == PlayerRank.OWNER && teamPlayer.rank == PlayerRank.OWNER && member.player.name != teamPlayer.player.name)) {
                    permissionDenied(click)
                    return@addItem
                }
                GUIManager.openTeamMemberManagementGUI(click.whoClicked as Player, member, team)
            }
        }
        val teamLimit = team.teamLimit
        val remainingSeats = teamLimit - team.members.get().size
        repeat(remainingSeats) {
            addItem(TeamMemberItem.invite?.copy() ?: GuiItem(Material.LIME_STAINED_GLASS_PANE)) { click ->
                val player = click.whoClicked as Player
                val teamPlayer = team.getTeamPlayer(player) ?: return@addItem
                if (teamPlayer.rank == PlayerRank.DEFAULT) {
                    permissionDenied(click)
                    return@addItem
                }
                openAnvilInviteGUI(player)
            }
        }

        val levelsSection = Main.plugin.config.getConfigurationSection("levels")
        val ultimateMaxTeamLimit = levelsSection?.getKeys(false)?.maxOfOrNull { key ->
            levelsSection.getInt("$key.teamLimit")
        } ?: team.teamLimit

        val lockedInviteItem = TeamMemberItem.lockedInvite ?: GuiItem(Material.BARRIER)

        // Display Locked Slots
        // Show slots starting from the current limit up to the ultimate config limit
        if (teamLimit < ultimateMaxTeamLimit && levelsSection != null) {

            for (slotIndex in teamLimit until ultimateMaxTeamLimit) {

                val requiredLevelNum = levelsSection.getKeys(false).mapNotNull { key ->

                    // Parse "l1" -> 1
                    val levelNum = key.removePrefix("l").toIntOrNull() ?: return@mapNotNull null
                    val maxAtLevel = levelsSection.getInt("$key.teamLimit")

                    // Return pair of (LevelNumber, MaxWarpsAtThatLevel)
                    levelNum to maxAtLevel

                }.sortedBy { it.first }.firstOrNull { (_, maxAtLevel) -> maxAtLevel > slotIndex } // Find first level that unlocks this slot
                    ?.first ?: (team.level + 1)

                val lockedItemCopy = lockedInviteItem.copy().apply {
                    name = name.replace("{level}", requiredLevelNum.toString())
                    lore = lore.map { it.replace("{level}", requiredLevelNum.toString()) }.toMutableList()
                }
                addItem(lockedItemCopy)
            }
        }

    }
}

fun openAnvilInviteGUI(player: Player) {
    openAnvilGUI(
        player = player,
        title = applyMiniColor(TeamMemberItem.inviteTitle ?: ""),
        label = applyMiniColor(TeamMemberItem.inviteLabel ?: ""),
        inputItem = TeamMemberItem.inviteInputItem ?: GuiItem(Material.PAPER),
        outputItem = TeamMemberItem.inviteOutputItem ?: GuiItem(Material.LIME_DYE),
        onInvalidInput = { },
        onCancel = { GUIManager.openTeamMemberGUI(player, Team.getTeam(player.name)!!) },
        onConfirm = { invitedPlayerName ->
            TeamService.invitePlayer(player, invitedPlayerName)
            GUIManager.openTeamMemberGUI(player, Team.getTeam(player.name)!!)
        })
}