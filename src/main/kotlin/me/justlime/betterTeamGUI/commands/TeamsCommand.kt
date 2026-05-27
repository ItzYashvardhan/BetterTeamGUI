package me.justlime.betterTeamGUI.commands

import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.config.GuiDirectoryHandler
import net.justlime.limeframegui.manager.GuiManager
import net.justlime.limeframegui.registry.gui.PageRegistry
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TeamsCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
//        val sound = SoundRegistry.get("open-gui")

        if (args.isEmpty()) {
            if (sender !is Player) {
//                val message = ConfigManager.messages.getString("player-only.chat") ?: ""
//                CommandService.sendMessage(sender, message)
                return true
            }
            GuiManager.clearHistory(sender.uniqueId)
            GuiManager.open(sender,"dashboard_view")
            return true
        }

        if (args[0] == "open" && args.size >1  && sender is Player) {
            GuiManager.clearHistory(sender.uniqueId)
            GuiManager.open(sender,args[1])
            return true
        }


        if (args[0] == "reload") {
            GuiDirectoryHandler.reload(pluginInstance,false)
            return CommandService.reload(sender)
        }

        if (args[0] == "reset") {
            GuiDirectoryHandler.reload(pluginInstance,true)
            return CommandService.reload(sender)
        }

        if (sender !is Player) {
//            val message = ConfigManager.messages.getString("player-only.chat") ?: ""
//            CommandService.sendMessage(sender, message)
            return true
        }

        if (args[0] == "view") {
            CommandService.teamView(sender, args.getOrNull(1) ?: "")
//            GuiSound.playPack(sender, sound)
            return true
        }

//        GUIManager.openTeamGUI(sender)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val list = mutableListOf<String>()
        if (args.size == 1) {
            list.addAll(listOf("view", "reload", "reset", "open"))
        } else if (args.size == 2 && args[0] == "open") {
            list.addAll(PageRegistry.getPages().keys)
        }

        return list
    }

}
