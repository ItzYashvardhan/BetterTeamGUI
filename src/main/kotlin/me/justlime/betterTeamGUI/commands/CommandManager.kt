package me.justlime.betterTeamGUI.commands

import me.justlime.betterTeamGUI.pluginInstance

object CommandManager {
    fun register() {
        pluginInstance.getCommand("teams")?.setExecutor(TeamsCommand())
    }

}