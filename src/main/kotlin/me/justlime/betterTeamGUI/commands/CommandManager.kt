package me.justlime.betterTeamGUI.commands

import me.justlime.betterTeamGUI.BetterTeamGUI
import me.justlime.betterTeamGUI.pluginInstance

object CommandManager {
    fun register(betterTeamGUI: BetterTeamGUI) {
        pluginInstance.getCommand("teams")?.setExecutor(TeamCommand())
    }

}