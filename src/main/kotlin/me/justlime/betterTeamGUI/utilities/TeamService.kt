package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JGui
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.handler.GUIEventHandler
import net.justlime.limeframegui.utilities.FrameAdapter
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

object TeamService {
    var command: String = ConfigManager.config.getString(JGui.Config.PREFIX) ?: "team"

    fun teamToPlaceholderMap(team: Team): Map<String, String> {
        return mapOf(
            "{team}" to (team.name ?: "N/A"),
            "{tag}" to (team.tag ?: "N/A"),
            "{team_size}" to team.members.size().toString(),
            "{team_limit}" to team.teamLimit.toString(),
            "{team_warps}" to team.maxWarps.toString(),
            "{team_level}" to team.level.toString(),
            "{team_score}" to team.score.toString(),
            "{team_money}" to team.money.toString(),
            "{anchor}" to team.isAnchored.toString(),
            "{team_description}" to team.description,
            "{team_color_code}" to "<" + team.color.name + ">",
            "{/team_color_code}" to "</" + team.color.name + ">",
            "{allies_request}" to team.allyRequests.size.toString(),
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
        player.performCommand("$command join $teamName")
    }

    fun invitePlayer(player: Player, invitedPlayerName: String) {
        player.performCommand("$command invite $invitedPlayerName")
    }

    fun depositAmount(player: Player, amount: String) {
        player.performCommand("$command deposit $amount")
    }

    fun withdrawAmount(player: Player, amount: String) {
        player.performCommand("$command withdraw $amount")
    }

    fun setWarp(player: Player, warpName: String, password: String? = null) {
        if (password != null) player.performCommand("$command setwarp $warpName $password")
        else player.performCommand("$command setwarp $warpName")
    }

    fun delWarp(player: Player, warpName: String) {
        player.performCommand("$command delwarp $warpName")
    }

    fun warp(player: Player, warpName: String) {
        player.performCommand("$command warp $warpName")
    }

    fun warp(player: Player, warpName: String, password: String) {
        player.performCommand("$command warp $warpName $password")
    }

    fun teleportToHome(player: Player) {
        player.performCommand("$command home")
    }

    fun setHome(player: Player) {
        player.performCommand("$command sethome")
    }

    fun removeHome(player: Player) {
        player.performCommand("$command delhome")
    }

    fun openTeamEnderChest(player: Player) {
        player.performCommand("$command echest")
    }

    fun leaveTeam(player: Player) {
        player.performCommand("$command leave")
    }

    fun setTitle(player: Player, title: String) {
        if (title.isBlank()) player.performCommand("$command title")
        else player.performCommand("$command title $title")
    }

    fun togglePvp(player: Player) {
        player.performCommand("$command pvp")
    }

    fun setDescription(player: Player, description: String) {
        if (description.isBlank()) player.performCommand("$command description")
        else player.performCommand("$command description $description")
    }

    fun setTag(player: Player, tag: String) {
        if (tag.isBlank()) player.performCommand("$command tag")
        else player.performCommand("$command tag $tag")
    }

    fun rename(player: Player, newName: String) {
        player.performCommand("$command name $newName")
    }

    fun disbandTeam(player: Player) {
        player.performCommand("$command disband")
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.performCommand("$command disband")
        }, 4)
    }

    fun createTeam(player: Player, teamName: String) {
        player.performCommand("$command create $teamName")
    }

    fun setTeamColor(player: Player, color: String) {
        //string get like blue, red
        player.performCommand("$command color $color")
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            GUIManager.openTeamSettingGUI(player)
        }, 4)
    }

    fun promote(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$command promote ${targetPlayer.player.name}")
    }

    fun demote(player: Player, targetPlayer: TeamPlayer) {
        player.performCommand("$command demote ${targetPlayer.player.name}")
    }

    fun kick(player: Player, targetTeamPlayer: TeamPlayer) {
        player.performCommand("$command kick ${targetTeamPlayer.player.name}")
    }

    fun ban(player: Player, targetTeamPlayer: TeamPlayer) {
        player.performCommand("$command ban ${targetTeamPlayer.player.name}")
    }

    fun unban(player: Player, targetPlayer: OfflinePlayer) {
        player.performCommand("$command unban ${targetPlayer.player?.name}")
    }

    fun addAlly(player: Player, allyTeam: Team) {
        player.performCommand("$command ally ${allyTeam.name}")
    }

    fun removeAlly(player: Player, allyTeam: Team) {
        player.performCommand("$command neutral ${allyTeam.name}")
    }

    fun promoteTeam(player: Player){
        player.performCommand("$command rankup")
    }

    fun reload() {
        command = ConfigManager.config.getString(JGui.Config.PREFIX) ?: "team"
    }

}