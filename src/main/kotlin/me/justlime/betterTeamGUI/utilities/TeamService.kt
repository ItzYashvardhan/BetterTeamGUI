package me.justlime.betterTeamGUI.utilities

import me.justlime.betterTeamGUI.models.JGui
import net.justlime.limeframegui.config.YamlFileHandler
import org.bukkit.entity.Player
import java.io.File

object TeamService {

    val file = File("config.yml")

    var command: String = YamlFileHandler(file).config.getString(JGui.Config.PREFIX) ?: "team"
    fun warp(player: Player, warpName: String) {
        player.performCommand("$command warp $warpName")
    }

    fun warp(player: Player, warpName: String, password: String) {
        player.performCommand("$command warp $warpName $password")
    }

    fun reload() {
        command = YamlFileHandler(file).config.getString(JGui.Config.PREFIX) ?: "team"
    }

}