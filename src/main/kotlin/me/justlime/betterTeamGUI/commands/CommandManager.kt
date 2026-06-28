package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Main
import me.justlime.betterTeamGUI.BetterTeamGUI

object CommandManager {
    fun register() {
        BetterTeamGUI.INSTANCE.getCommand("teams")?.setExecutor(TeamsCommand())
        Main.plugin?.teamCommand?.addSubCommand(TeamGuiCommand())
    }

}