package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Main
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object TeamService {
    private val commandConfig = Main.plugin.config.getConfigurationSection("command")
    const val PREFIX = "betterteams:"
    const val COMMAND = "team"
    const val TEAM_COMMAND: String = COMMAND

    fun teamToPlaceholderMap(team: Team): Map<String, String> {
        return mapOf(
            "{team}" to (team.name ?: "N/A"),
            "{team_size}" to team.members.size().toString(),
            "{team_limit}" to team.teamLimit.toString(),
            "{team_level}" to team.level.toString(),
            "{team_score}" to team.score.toString(),
            "{team_money}" to team.money.toString(),
            "{anchor}" to team.isAnchored.toString(),
            "{team_description}" to team.description,
            "{team_color_code}" to "<" + team.color.name + ">",
            "{/team_color_code}" to "</" + team.color.name + ">",
        )
    }

    fun teamPlayerToPlaceholderMap(teamPlayer: TeamPlayer): Map<String, String> {
        return mapOf(
            "{rank}" to teamPlayer.rank.name,
            "{team_player}" to (teamPlayer.player.name ?: ""),
        )
    }

    fun applyPlaceHolder(team: Team, teamPlayer: TeamPlayer): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map.putAll(teamToPlaceholderMap(team))
        map.putAll(teamPlayerToPlaceholderMap(teamPlayer))
        return map
    }



    fun joinTeam(player: Player, teamName: String) {
        player.performCommand("$TEAM_COMMAND join $teamName")
    }

    fun depositAmount(player: Player, amount: String) {
        player.performCommand("$TEAM_COMMAND deposit $amount")
    }

    fun withdrawAmount(player: Player, amount: String) {
        player.performCommand("$TEAM_COMMAND withdraw $amount")
    }

    fun setWarp(player: Player, warpName: String, password: String? = null) {
        if (password != null) player.performCommand("$TEAM_COMMAND setwarp $warpName $password")
        else player.performCommand("$TEAM_COMMAND setwarp $warpName")
    }

    fun delWarp(player: Player, warpName: String) {
        player.performCommand("$TEAM_COMMAND delwarp $warpName")
    }

    fun warp(player: Player, warpName: String) {
        player.performCommand("$TEAM_COMMAND warp $warpName")
    }

    fun warp(player: Player, warpName: String, password: String) {
        player.performCommand("$TEAM_COMMAND warp $warpName $password")
    }

    fun teleportToHome(player: Player) {
        player.performCommand("$TEAM_COMMAND home")
    }

    fun setHome(player: Player) {
        player.performCommand("$TEAM_COMMAND sethome")
    }

    fun removeHome(player: Player) {
        player.performCommand("$TEAM_COMMAND delhome")
    }

    fun openTeamEnderChest(player: Player) {
        player.performCommand("$TEAM_COMMAND echest")
    }

    fun leaveTeam(player: Player) {
        player.performCommand("$TEAM_COMMAND leave")
    }

    fun setTitle(player: Player, title: String) {
        if (title.isBlank()) player.performCommand("$TEAM_COMMAND title")
        else player.performCommand("$TEAM_COMMAND title $title")
    }

    fun togglePvp(player: Player) {
        player.performCommand("$TEAM_COMMAND pvp")
    }

    fun setDescription(player: Player, description: String) {
        if (description.isBlank()) player.performCommand("$TEAM_COMMAND description")
        else player.performCommand("$TEAM_COMMAND description $description")
    }

    fun setTag(player: Player, tag: String) {
        if (tag.isBlank()) player.performCommand("$TEAM_COMMAND tag")
        else player.performCommand("$TEAM_COMMAND tag $tag")
    }

    fun disbandTeam(player: Player) {
        player.performCommand("$TEAM_COMMAND disband")
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.performCommand("$TEAM_COMMAND disband")
        }, 4)
    }

    fun createTeam(player: Player, teamName: String) {
        player.performCommand("$TEAM_COMMAND create $teamName")
    }

    fun setTeamColor(player: Player, color: String) {
        //string get like blue, red
        player.performCommand("$TEAM_COMMAND color $color")
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            GUIManager.openTeamSettingGUI(player)
        }, 4)
    }

    fun promote(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$TEAM_COMMAND promote ${targetPlayer.player.name}")
    }

    fun demote(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$TEAM_COMMAND demote ${targetPlayer.player.name}")
    }

    fun kick(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$TEAM_COMMAND kick ${targetPlayer.player.name}")
    }

    fun ban(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$TEAM_COMMAND ban ${targetPlayer.player.name}")
    }

    fun unban(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$TEAM_COMMAND unban ${targetPlayer.player.name}")
    }

    fun invitePlayer(player: Player, invitedPlayerName: String) {
        player.performCommand("$TEAM_COMMAND invite $invitedPlayerName")
    }

}