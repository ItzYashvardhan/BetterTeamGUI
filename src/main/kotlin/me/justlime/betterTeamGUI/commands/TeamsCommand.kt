package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
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

        if (args[0] == "reload" && sender.hasPermission("betterteamgui.admin.reload")) {
            sender.sendMessage("Config Reloaded")
            pluginInstance.saveDefaultConfig()
            pluginInstance.reloadConfig()
            Config.reload()
            return true
        }
        if (args[0] == "warp" && sender.hasPermission("betterteamgui.use.warps") ) {
            GUIManager.openTeamWarpGUI(sender)
            return true
        }
        if (args[0] == "balance" && sender.hasPermission("betterteamgui.use.balance")) {
            GUIManager.openTeamBalanceGUI(sender)
            return true
        }
        if (args[0] == "members" && sender.hasPermission("betterteamgui.use.members")) {
            GUIManager.openTeamMemberGUI(sender,team,playerTeam)
            return true
        }
        if (args[0] == "ally" && sender.hasPermission("betterteamgui.use.ally")) {
            GUIManager.openTeamAllyGUI(sender,team,playerTeam)
            return true
        }
        GUIManager.openTeamGUI(sender)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        val completion = mutableListOf<String>()
        if (args.size == 1) {
            if (sender.hasPermission("betterteamgui.admin.reload")) completion.add("reload")
            if (sender.hasPermission("betterteamgui.use.warps")) completion.add("warp")
            if (sender.hasPermission("betterteamgui.use.balance")) completion.add("balance")
            if (sender.hasPermission("betterteamgui.use.members")) completion.add("members")
            if (sender.hasPermission("betterteamgui.use.ally")) completion.add("ally")
        }
        return completion
    }
}

