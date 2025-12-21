package me.justlime.betterTeamGUI.commands

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.gui.GUIManager
import net.justlime.limeframegui.models.GuiSound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TeamsCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val sound = ConfigManager.sound.getString("open-gui") ?: "BLOCK.NOTE_BLOCK.CHIME, 2.0"

        if (args.isEmpty()) {
            if (sender !is Player) {
                val message = ConfigManager.messages.getString("player-only.chat") ?: ""
                CommandService.sendMessage(sender, message)
                return true
            }
            val finalSound = GuiSound.loadSound(sound)
            GUIManager.openTeamGUI(sender)
            finalSound?.playSound(sender)
            return true
        }

        if (args[0] == "reload") {
            return CommandService.reload(sender)
        }

        if (sender !is Player) {
            val message = ConfigManager.messages.getString("player-only.chat") ?: ""
            CommandService.sendMessage(sender, message)
            return true
        }

        if (args[0] == "view") {
            CommandService.teamView(sender, args.getOrNull(1) ?: "")
            val finalSound = GuiSound.loadSound(sound)
            finalSound?.playSound(sender)
            return true
        }

        GUIManager.openTeamGUI(sender)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        return CommandService.tabCompleter(sender, args)
    }

}
