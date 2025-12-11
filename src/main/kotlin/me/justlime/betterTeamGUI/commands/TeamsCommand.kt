package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import me.justlime.betterTeamGUI.utilities.TeamService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TeamsCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        if (args.isEmpty()) {
            GUIManager.openTeamGUI(sender)
            return true
        }

        if (args[0] == "view" && sender.hasPermission("betterteamgui.use.view")) {
            val teamName = args.getOrNull(1) ?: return true
            val teamToView = Team.getTeam(teamName) ?: return true
            GUIManager.openTeamViewerGUI(sender, teamToView)
            return true
        }
        val team = Team.getTeam(sender.name) ?: return true

        if (args[0] == "reload" && sender.hasPermission("betterteamgui.admin.reload")) {
            ConsoleMessage.printHeader()
            try {
                pluginInstance.saveDefaultConfig()
                pluginInstance.reloadConfig()
                Config.reload(sender)
                TeamService.reload()
                sender.sendMessage("[BetterTeamGUI] §aSuccessfully Reloaded Configs")
                ConsoleMessage.printStep("Config Reloaded")
            } catch (e: Exception) {
                ConsoleMessage.printStep("Failed to Reload Config", ConsoleMessage.Color.RED)
                ConsoleMessage.printStep("Error: ${e.message}", ConsoleMessage.Color.BRIGHT_RED)
            }

            ConsoleMessage.printFooter()
            return true
        }
        if (args[0] == "warp" && sender.hasPermission("betterteamgui.use.warps")) {
            GUIManager.openTeamWarpGUI(sender)
            return true
        }
        if (args[0] == "members" && sender.hasPermission("betterteamgui.use.members")) {
            GUIManager.openTeamMemberGUI(sender, team)
            return true
        }
        if (args[0] == "ally" && sender.hasPermission("betterteamgui.use.ally")) {
            GUIManager.openTeamAlliesListGUI(sender, team)
            return true
        }

        if (args[0] == "list" && sender.hasPermission("betterteamgui.use.ally")) {
            GUIManager.openTeamListGUI(sender)
            return true
        }


        GUIManager.openTeamGUI(sender)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        val completion = mutableListOf<String>()
        if (args.size == 1) {
            if (sender.hasPermission("betterteamgui.admin.reload")) completion.add("reload")
            if (sender.hasPermission("betterteamgui.use.warps")) completion.add("warp")
            if (sender.hasPermission("betterteamgui.use.balance")) completion.add("balance")
            if (sender.hasPermission("betterteamgui.use.members")) completion.add("members")
            if (sender.hasPermission("betterteamgui.use.ally")) completion.add("ally")
            if (sender.hasPermission("betterteamgui.use.view")) completion.add("view")
            if (sender.hasPermission("betterteamgui.use.list")) completion.add("list")
            if (sender.hasPermission("betterteamgui.use.lb")) completion.add("lb")
        }
        if (args.size == 2) {
            val teamManager = Team.getTeamManager()
            val activeTeams = teamManager.loadedTeamListClone.values.map { it.name }
            completion.addAll(activeTeams)
        }
        return completion
    }

}
