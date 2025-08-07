package me.justlime.betterTeamGUI

import me.clip.placeholderapi.metrics.bukkit.Metrics
import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.gui.GUIHandler
import me.justlime.betterTeamGUI.listener.ListenerManager
import net.justlime.limeframegui.api.LimeFrameAPI
import net.justlime.limeframegui.enums.ColorType
import net.justlime.limeframegui.impl.ConfigHandler
import net.justlime.limeframegui.utilities.FrameColor
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
        LimeFrameAPI.init(this)
        Config.reload()

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
        FrameColor.colorType = ColorType.MINI_MESSAGE
        LimeFrameAPI.setKeys {
            inventoryRows = "row"
            material = "item"
            name = "name"
            lore = "lore"
            glow = "glow"
            slot = "slot"
            slotList = "slot"
            texture = "texture"
        }
    }

}




