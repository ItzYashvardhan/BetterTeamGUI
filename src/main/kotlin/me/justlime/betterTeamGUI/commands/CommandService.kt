package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.adventure
import me.justlime.betterTeamGUI.utilities.applyMiniColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandService {

    fun tabCompleter(sender: CommandSender, args: Array<out String>): MutableList<String> {
        val completion = mutableListOf<String>()
        if (args.size == 1) {
            if (sender.hasPermission("betterteamgui.admin.reload")) completion.add("reload")
            if (sender.hasPermission("betterteamgui.use.view")) completion.add("view")
        }

        if (args.size == 2 && args[0] == "view") {
            val teamManager = Team.getTeamManager()
            val activeTeams = teamManager.loadedTeamListClone.values.map { it.name }
            completion.addAll(activeTeams)
        }

        return completion
    }

    fun reload(sender: CommandSender): Boolean {
        if (!sender.hasPermission("betterteamgui.admin.reload")) {
            val msg = ConfigManager.messages.getString("no-permission.chat") ?: ""
            sendMessage(sender, msg)
            return true
        }

        ConsoleMessage.printHeader()
        try {
            pluginInstance.saveDefaultConfig()
            pluginInstance.reloadConfig()
            Config.reload(sender)
            TeamService.reload()
            val message = ConfigManager.messages.getString("reload.chat") ?: ""
            sendMessage(sender, message)
            ConsoleMessage.printStep("Config Reloaded")
        } catch (e: Exception) {
            ConsoleMessage.printStep("Failed to Reload Config", ConsoleMessage.Color.RED)
            ConsoleMessage.printStep("Error: ${e.message}", ConsoleMessage.Color.BRIGHT_RED)
        }

        ConsoleMessage.printFooter()
        return true
    }

    fun teamView(sender: CommandSender, teamName: String) {
        if (!sender.hasPermission("betterteamgui.use.view")) {
            val msg = ConfigManager.messages.getString("no-permission") ?: ""
            sendMessage(sender, msg)
            return
        }
        if (sender !is Player) {
            val msg = ConfigManager.messages.getString("player-only.chat") ?: ""
            sendMessage(sender, msg)
            return
        }
        val teamToView = Team.getTeam(teamName)
        if (teamToView == null) {
            val msg = ConfigManager.messages.getString("team-not-found.chat") ?: ""
            sendMessage(sender, msg)
            return
        }
        foliaLib.scheduler.runNextTick {
            GUIManager.openTeamViewerGUI(sender, teamToView)
        }

        return
    }

    fun sendMessage(sender: CommandSender, message: String) {
        val componentMsg = applyMiniColor(message)
        when (sender) {
            is Player -> adventure.player(sender).sendMessage(componentMsg)
            else -> adventure.console().sendMessage(componentMsg)
        }
    }
}