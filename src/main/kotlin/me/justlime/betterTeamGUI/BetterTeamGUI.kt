package me.justlime.betterTeamGUI

import me.clip.placeholderapi.metrics.bukkit.Metrics
import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.commands.TeamCommandProxy
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
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
        TeamCommandProxy.inject() //Experimental
        CommandManager.register() //Initialize
        LimeFrameAPI.debugging = true

        Metrics(this, 24705)
        setupLimeFrameGUI()
    }

    override fun onDisable() {
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




