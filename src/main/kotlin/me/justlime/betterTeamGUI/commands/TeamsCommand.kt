package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import javax.swing.text.html.HTML.Tag.S

class TeamsCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        if (args.isEmpty()) {
            GUIManager.openTeamGUI(sender)
            return true
        }

        val team = Team.getTeam(sender.name) ?: return true
        val playerTeam = team.getTeamPlayer(sender) ?: return true


        if (args[0] == "reload" && sender.isOp) {
            sender.sendMessage("Config Reloaded")
            pluginInstance.reloadConfig()
            return true
        }
        if (args[0] == "warp") {
            GUIManager.openTeamWarpGUI(sender)
            return true
        }
        if (args[0] == "create") {
            GUIManager.openTeamCreateGUI(sender)
            return true
        }
        if (args[0] == "balance") {
            GUIManager.openTeamBalanceGUI(sender)
            return true
        }
        if (args[0] == "leave") {
            GUIManager.openTeamLeaveGUI(sender)
            return true
        }
        if (args[0] == "home") {
            sender.performCommand("team home")
            return true
        }
        if (args[0] == "chat") {
            sender.performCommand("team chat")
            return true
        }
        if (args[0] == "members") {
            GUIManager.openTeamMemberGUI(sender,team,playerTeam)
            return true
        }
        if (args[0] == "ally") {
            sender.sendMessage(" ")
            return true
        }
        if (args[0] == "pvp") {
            sender.performCommand("team pvp")
            return true
        }
        if (args[0] == "echest") {
            sender.performCommand("team echest")
            return true
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        val completion = mutableListOf<String>()
        if (args.size == 1) {
            if (sender.isOp) completion.add("reload")
            completion.add("warp")
            completion.add("balance")
            completion.add("leave")
            completion.add("home")
            completion.add("chat")
            completion.add("members")
            completion.add("ally")
            completion.add("pvp")
            completion.add("echest")
        }
        return completion
    }
}

