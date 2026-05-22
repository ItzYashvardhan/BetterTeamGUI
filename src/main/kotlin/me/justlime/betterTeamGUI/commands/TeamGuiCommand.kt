package me.justlime.betterTeamGUI.commands

import com.booksaw.betterTeams.CommandResponse
import com.booksaw.betterTeams.commands.SubCommand
import me.justlime.betterTeamGUI.foliaLib
import net.justlime.limeframegui.models.registry.GuiSound
import net.justlime.limeframegui.registry.component.SoundRegistry
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamGuiCommand : SubCommand() {
    override fun onCommand(sender: CommandSender, label: String?, args: Array<String>): CommandResponse {
        val sound = SoundRegistry.get("open-gui")

        if (args.isEmpty()) {
            if (sender !is Player) {
//                val message = ConfigManager.messages.getString("player-only.chat") ?: ""
//                CommandService.sendMessage(sender, message)
                return CommandResponse(true)
            }
            foliaLib.scheduler.runNextTick {
//                GuiSound.playPack(sender, sound)
//                GUIManager.openTeamGUI(sender)
            }
            return CommandResponse(true)

        }

        if (args[0] == "reload") {
            CommandService.reload(sender)
            return CommandResponse(true)
        }

        if (sender !is Player) {
//            val message = ConfigManager.messages.getString("player-only.chat") ?: ""
//            CommandService.sendMessage(sender, message)
            return CommandResponse(true)
        }

        if (args[0] == "view") {
            CommandService.teamView(sender, args.getOrNull(1) ?: "")
            GuiSound.playPack(sender, sound)
            return CommandResponse(true)
        }

        foliaLib.scheduler.runNextTick {
            GuiSound.playPack(sender, sound)
//            GUIManager.openTeamGUI(sender)
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

    override fun onTabComplete(
        options: MutableList<String>,
        sender: CommandSender,
        label: String?,
        args: Array<out String>
    ) {
        options.addAll(CommandService.tabCompleter(sender, args))
    }

}