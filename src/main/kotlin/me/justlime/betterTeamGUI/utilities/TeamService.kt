package me.justlime.betterTeamGUI.utilities

import me.justlime.betterTeamGUI.BetterTeamGUI
import me.justlime.betterTeamGUI.models.JGui
import net.justlime.limeframegui.config.YamlFileHandler
import org.bukkit.entity.Player
import java.io.File

object TeamService {

    private val file: File
        get() = File(BetterTeamGUI.INSTANCE.dataFolder, "config.yml")

    private var _command: String? = null

    var command: String
        get() {
            if (_command == null) reload()
            return _command!!
        }
        private set(value) {
            _command = value
        }

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