package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Main
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JGui
import me.justlime.betterTeamGUI.pluginInstance

object CommandManager {
    fun register() {
        if (!ConfigManager.config.getBoolean(JGui.Config.USE_NATIVE_COMMAND, true)) pluginInstance.getCommand("teams")?.setExecutor(TeamsCommand())
        Main.plugin.teamCommand.addSubCommand(TeamGuiCommand())
    }

}