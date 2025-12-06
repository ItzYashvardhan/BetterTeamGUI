package me.justlime.betterTeamGUI

import com.booksaw.betterTeams.BooksawCommand
import me.clip.placeholderapi.metrics.bukkit.Metrics
import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.commands.subcommand.TeamCommandProxy
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.gui.GUIHandler
import me.justlime.betterTeamGUI.listener.ListenerManager
import net.justlime.limeframegui.api.LimeFrameAPI
import net.justlime.limeframegui.color.FontLoader
import net.justlime.limeframegui.enums.ColorType
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin

lateinit var pluginInstance: BetterTeamGUI

class BetterTeamGUI : JavaPlugin() {
    override fun onEnable() {
        if (this.server.pluginManager.isPluginEnabled("BetterTeams")) {
            this.logger.info("Successfully Enabled BetterTeamsGUI")
        } else this.server.pluginManager.disablePlugin(this)
        if (!this.dataFolder.exists()) this.dataFolder.mkdir()
        this.saveDefaultConfig()
        pluginInstance = this
        LimeFrameAPI.init(this, ColorType.MINI_MESSAGE)
        Config.reload()

        TeamCommandProxy.inject()
//        setupMainCommand()
        CommandManager.register() //Initialize
        ListenerManager.register(this) //Initialize
        Metrics(this, 24705)
        setupLimeFrameGUI()

    }

    override fun onDisable() {
        val players = pluginInstance.server.onlinePlayers
        players.forEach {
            val inventory = it.openInventory.topInventory
            val holder = inventory.holder
            if (holder is GUIHandler) it.closeInventory()
        }
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
            smallCaps = ConfigManager.font.getBoolean("small-caps", true)
        }
        ItemFlag.HIDE_ATTRIBUTES
    }

    fun setupMainCommand() {
//        val command = "team"
//        val teamCommand = Main.plugin.getCommand(command)
//        if (teamCommand != null) {
//            val originalExecutor = teamCommand.executor
//
//            // Safety check: ensure we don't wrap our own proxy if reload happens
//            if (originalExecutor !is TeamCommandProxy) {
//                val proxy = TeamCommandProxy(originalExecutor)
//                teamCommand.setExecutor(proxy)
//                teamCommand.tabCompleter = proxy
//                logger.info("Successfully hooked into /team command!")
//            }
//        } else {
//            logger.warning("Could not find '$command' command to hook into. Is BetterTeams loaded?")
//        }
//    }
    }
}




