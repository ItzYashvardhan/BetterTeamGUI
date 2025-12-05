package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Main
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object TeamService {
    private val commandConfig = Main.plugin.config.getConfigurationSection("command")
    private val teamCommandPrefix: String = commandConfig?.getStringList("team")?.firstOrNull() ?: "team"

    fun depositAmount(player: Player, amount: String) {
        player.performCommand("$teamCommandPrefix deposit $amount")
    }

    fun withdrawAmount(player: Player, amount: String) {
        player.performCommand("$teamCommandPrefix withdraw $amount")
    }

    fun setWarp(player: Player, warpName: String, password: String? = null) {
        if (password != null) player.performCommand("$teamCommandPrefix setwarp $warpName $password")
        else player.performCommand("$teamCommandPrefix setwarp $warpName")
    }

    fun delWarp(player: Player, warpName: String) {
        player.performCommand("$teamCommandPrefix delwarp $warpName")
    }

    fun warp(player: Player, warpName: String) {
        player.performCommand("$teamCommandPrefix warp $warpName")
    }

    fun warp(player: Player, warpName: String, password: String) {
        player.performCommand("$teamCommandPrefix warp $warpName $password")
    }

    fun teleportToHome(player: Player) {
        player.performCommand("$teamCommandPrefix home")
    }

    fun openTeamEnderChest(player: Player) {
        player.performCommand("$teamCommandPrefix echest")
    }

    fun leaveTeam(player: Player) {
        player.performCommand("$teamCommandPrefix leave")
    }

    fun setTitle(player: Player, title: String) {
        if (title.isBlank()) player.performCommand("$teamCommandPrefix title")
        else player.performCommand("$teamCommandPrefix title $title")
    }

    fun setAnchor(player: Player, bool: Boolean) {

        player.performCommand("$teamCommandPrefix anchor $bool")
    }

    fun togglePvp(player: Player) {
        player.performCommand("$teamCommandPrefix pvp")
    }

    fun setDescription(player: Player, description: String) {
        if (description.isBlank()) player.performCommand("$teamCommandPrefix description")
        else player.performCommand("$teamCommandPrefix description $description")
    }

    fun setTag(player: Player, tag: String) {
        if (tag.isBlank()) player.performCommand("$teamCommandPrefix tag")
        else player.performCommand("$teamCommandPrefix tag $tag")
    }

    fun disbandTeam(player: Player) {
        player.performCommand("$teamCommandPrefix disband")
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            player.performCommand("$teamCommandPrefix disband")
        }, 4)
    }

    fun createTeam(player: Player, teamName: String) {
        player.performCommand("$teamCommandPrefix create $teamName")
    }
}