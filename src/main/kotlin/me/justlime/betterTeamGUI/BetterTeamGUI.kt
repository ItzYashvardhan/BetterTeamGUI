package me.justlime.betterTeamGUI

import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.gui.BetterTeamGUIInventory
import me.justlime.betterTeamGUI.listener.ListenerManager
import org.bukkit.plugin.java.JavaPlugin

lateinit var pluginInstance: BetterTeamGUI

class BetterTeamGUI : JavaPlugin() {
    override fun onEnable() {
        if (this.server.pluginManager.isPluginEnabled("BetterTeams")) {
            this.logger.info("Successfully Enabled BetterTeamsGUI")
        } else this.server.pluginManager.disablePlugin(this)
        if(!this.dataFolder.exists()) this.dataFolder.mkdir()
        this.saveDefaultConfig()
        pluginInstance = this
        CommandManager.register(this) //Initialize
        ListenerManager.register(this) //Initialize
    }

    override fun onDisable() {
        val players = pluginInstance.server.onlinePlayers
        players.forEach {
            val inventory = it.openInventory.topInventory
            val holder = inventory.holder
            if (holder is BetterTeamGUIInventory) it.closeInventory()
        }
    }
}


