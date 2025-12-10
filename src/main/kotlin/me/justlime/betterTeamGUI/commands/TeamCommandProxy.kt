package me.justlime.betterTeamGUI.commands

import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.TeamService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus
import java.lang.reflect.Field
import kotlin.collections.iterator

@ApiStatus.Experimental
//Note: Generated from Gemini Model 3.0
/**
 * Acts as a middle-man between Bukkit and the original BetterTeams command.
 * It intercepts the main command to open the GUI, but passes everything else through.
 */
class TeamCommandProxy(name: String, private val originalCommand: Command) : Command(name, originalCommand.description, originalCommand.usage, originalCommand.aliases) {

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        //If it's a player running "/team" (booksaw original command) with no arguments
        if (args.isEmpty() && sender is Player) {
            Bukkit.getScheduler().runTask(pluginInstance, Runnable {
                GUIManager.openTeamGUI(sender)
            })
            return true
        }

        // If there are arguments, send it to BetterTeams
        return originalCommand.execute(sender, label, args)
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        // Preserve original Tab Completion
        return originalCommand.tabComplete(sender, alias, args)
    }

    companion object {
        fun inject() {
            try {
                val server = Bukkit.getServer()
                val commandMapField: Field = server.javaClass.getDeclaredField("commandMap")
                commandMapField.isAccessible = true
                val commandMap = commandMapField.get(server) as CommandMap

                val commandNameWithPrefix = "team:team"
                val commandName = TeamService.command
                val originalCommand = commandMap.getCommand(commandNameWithPrefix)

                if (originalCommand != null) {
                    // Unregister the old command from the map
                    if (unregisterCommand(commandMap, originalCommand)) {
                        // Create our proxy wrapping the original
                        val proxy = TeamCommandProxy(commandName, originalCommand)

                        // Register our proxy into the map
                        // "betterteams" is the fallback prefix, but it will take precedence for "team" because we removed the old one
                        commandMap.register(commandName, proxy)

                        pluginInstance.logger.info("Successfully hooked into /team command via CommandMap injection!")
                    } else {
                        pluginInstance.logger.warning("Failed to unregister original /team command. Proxy injection aborted.")
                    }
                } else {
                    pluginInstance.logger.warning("Could not find 'team' command in CommandMap. Is BetterTeams loaded?")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                pluginInstance.logger.severe("Failed to inject TeamCommandProxy!")
            }
        }

        /**
         * Safely unregisters a command from the CommandMap by reflecting on the map itself.
         * This targets the 'knownCommands' map using the standardized Bukkit class structure.
         */
        private fun unregisterCommand(commandMap: CommandMap, command: Command): Boolean {
            try {
                // 1. Find the 'knownCommands' field by walking up the class hierarchy
                // This is required because getDeclaredField doesn't check superclasses (SimpleCommandMap)
                var knownCommandsField: Field? = null
                var clazz: Class<*>? = commandMap.javaClass

                while (clazz != null && clazz != Object::class.java) {
                    try {
                        knownCommandsField = clazz.getDeclaredField("knownCommands")
                        break // Found it!
                    } catch (_: NoSuchFieldException) {
                        clazz = clazz.superclass // Try parent class
                    }
                }

                if (knownCommandsField == null) {
                    pluginInstance.logger.warning("Could not find 'knownCommands' field in CommandMap hierarchy.")
                    return false
                }

                knownCommandsField.isAccessible = true
                @Suppress("UNCHECKED_CAST") val knownCommands = knownCommandsField.get(commandMap) as MutableMap<String, Command>

                // 2. Collect all keys associated with this command instance to avoid concurrent modification
                // This covers name, aliases, and fallback prefixes (e.g. "betterteams:team")
                val keysToRemove = mutableListOf<String>()

                for ((key, value) in knownCommands) {
                    if (value === command) {
                        keysToRemove.add(key)
                    }
                }

                // 3. Remove all identified keys
                for (key in keysToRemove) {
                    knownCommands.remove(key)
                }

                // 4. Try public unregister method as a final cleanup step
                command.unregister(commandMap)

                return true

            } catch (e: Exception) {
                pluginInstance.logger.severe("Error during command unregistration fallback.")
                e.printStackTrace()
                return false
            }
        }
    }
}