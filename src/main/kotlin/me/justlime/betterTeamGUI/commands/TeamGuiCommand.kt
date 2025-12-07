package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.CommandResponse
import com.booksaw.betterTeams.commands.SubCommand
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamGuiCommand() : SubCommand() {
    override fun onCommand(player: CommandSender?, label: String?, args: Array<String>): CommandResponse {
        if (player !is Player) {
            return CommandResponse(false)
        }
        Bukkit.getScheduler().runTask(pluginInstance, Runnable {
            GUIManager.openTeamGUI(player)
        })
        return CommandResponse(true)
    }

    override fun getCommand(): String {
        return "gui"
    }

    override fun getNode(): String {
        return "betterteams.command.gui"
    }

    override fun getHelp(): String {
        return "Opens the team GUI"
    }

    override fun getArguments(): String {
        return ""
    }

    override fun getMinimumArguments(): Int {
        return 0
    }

    override fun getMaximumArguments(): Int {
        return 0
    }

    override fun onTabComplete(player: List<String>?, sender: CommandSender?, label: String?, args: Array<String>) {
        // No tab completion needed for this command
    }

}