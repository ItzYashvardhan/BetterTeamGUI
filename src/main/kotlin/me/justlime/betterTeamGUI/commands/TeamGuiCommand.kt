package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.CommandResponse
import com.booksaw.betterTeams.commands.SubCommand
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import net.justlime.limeframegui.models.GuiSound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamGuiCommand : SubCommand() {
    override fun onCommand(sender: CommandSender, label: String?, args: Array<String>): CommandResponse {
        val sound = ConfigManager.sound.getString("open-gui") ?: "BLOCK.NOTE_BLOCK.CHIME, 2.0"

        if (args.isEmpty()) {
            if (sender !is Player) {
                val message = ConfigManager.messages.getString("player-only.chat") ?: ""
                CommandService.sendMessage(sender, message)
                return CommandResponse(true)
            }
            val finalSound = GuiSound.loadSound(sound)
            foliaLib.scheduler.runNextTick {
                finalSound?.playSound(sender)
                GUIManager.openTeamGUI(sender)
            }
            return CommandResponse(true)

        }

        if (args[0] == "reload") {
            CommandService.reload(sender)
            return CommandResponse(true)
        }

        if (sender !is Player) {
            val message = ConfigManager.messages.getString("player-only.chat") ?: ""
            CommandService.sendMessage(sender,message)
            return CommandResponse(true)
        }

        if (args[0] == "view") {
            CommandService.teamView(sender, args.getOrNull(1) ?: "")
            val finalSound = GuiSound.loadSound(sound)
            finalSound?.playSound(sender)
            return CommandResponse(true)
        }

        foliaLib.scheduler.runNextTick {
            val finalSound = GuiSound.loadSound(sound)
            finalSound?.playSound(sender)
            GUIManager.openTeamGUI(sender)
        }
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

    override fun onTabComplete(options: MutableList<String>, sender: CommandSender, label: String?, args: Array<out String>) {
        options.addAll(CommandService.tabCompleter(sender, args))
    }

}