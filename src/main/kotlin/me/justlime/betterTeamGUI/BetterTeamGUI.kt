package me.justlime.betterTeamGUI

import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.commands.TeamCommandProxy
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import net.justlime.limeframegui.api.LimeFrameAPI
import net.justlime.limeframegui.color.FontLoader
import net.justlime.limeframegui.enums.ColorType
import org.bstats.bukkit.Metrics
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin

lateinit var pluginInstance: BetterTeamGUI

class BetterTeamGUI : JavaPlugin() {

    override fun onEnable() {

        ConsoleMessage.printHeader()
        ConsoleMessage.printStep("Enabling BetterTeamGUI")
        pluginInstance = this
        LimeFrameAPI.init(this, ColorType.MINI_MESSAGE)
        if (!this.dataFolder.exists()) this.dataFolder.mkdir()
        this.saveDefaultConfig()
        Config.reload()

        if (!this.server.pluginManager.isPluginEnabled("BetterTeams")) {
            ConsoleMessage.printStep("BETTERTEAMS PLUGIN REQUIRED", ConsoleMessage.Color.RED)
            ConsoleMessage.printStep("Disabling BetterTeamsGUI")
            this.server.pluginManager.disablePlugin(this)
        }

        if (ConfigManager.config.getBoolean(JGui.Config.USE_NATIVE_COMMAND, true)) {
            try {
                TeamCommandProxy.inject() //Experimental}
                ConsoleMessage.printStep("Successfully Injected GUI Commands")

            } catch (e: Exception) {
                ConsoleMessage.printStep("Failed to Inject GUI Commands", ConsoleMessage.Color.RED)
                ConsoleMessage.printStep("Error: ${e.message}", ConsoleMessage.Color.BRIGHT_RED)
            }
        }
        CommandManager.register()
        ConsoleMessage.printStep("Successfully Registered Commands")
        Metrics(this, 24705)
        setupLimeFrameGUI()
        ConsoleMessage.printStep("LimeFrame Setup Completed")
        ConsoleMessage.printStep("Successfully Enabled BetterTeamsGUI - ${this.description.version} by ${this.description.authors.first()}", ConsoleMessage.Color.BRIGHT_GREEN)
        ConsoleMessage.printFooter()
    }

    override fun onDisable() {
        ConsoleMessage.printHeader()
        ConsoleMessage.printStep("Disabling BetterTeamGUI")
        ConsoleMessage.printFooter()
    }

    fun setupLimeFrameGUI() {
        FontLoader.load(JFiles.FONT.filename)
        LimeFrameAPI.setKeys {
            inventoryRows = "row"
            material = "item"
            name = "name"
            lore = "lore"
            glow = "glow"
            slot = "slot"
            slotList = "slot"
            texture = "texture"
            flags = "flags"
            stylishName = ConfigManager.font.getBoolean("small-caps", true)
            stylishLore = ConfigManager.font.getBoolean("small-caps", true)
            stylishTitle = ConfigManager.font.getBoolean("small-caps", true)
        }
        ItemFlag.HIDE_ATTRIBUTES
    }

}




