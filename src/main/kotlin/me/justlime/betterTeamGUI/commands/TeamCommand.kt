package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.TeamListGUI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true
        val teamName = Team.getTeam(sender)
        val newName = Team.getTeamManager()
        val gui = TeamListGUI(9*6,"Teams List").inventory
        sender.openInventory(gui)
        return true
    }

}