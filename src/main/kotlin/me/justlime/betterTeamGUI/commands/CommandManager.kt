package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Main
import me.justlime.betterTeamGUI.pluginInstance

object CommandManager {
    fun register() {
        pluginInstance.getCommand("teams")?.setExecutor(TeamsCommand())
        Main.plugin.teamCommand.addSubCommand(TeamGuiCommand())
    }

}