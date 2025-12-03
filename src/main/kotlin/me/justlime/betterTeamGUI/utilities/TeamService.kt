package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Main
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

}