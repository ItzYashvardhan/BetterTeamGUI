package me.justlime.betterTeamGUI.config

import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.impl.ConfigHandler
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.logging.Level

object ConfigManager {
    private val plugin = pluginInstance
    val teamViewConfig = ConfigHandler("team_view.yml")


    init {
        if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdir()
        val formDir = File(plugin.dataFolder, "form")
        if (!formDir.exists()) formDir.mkdirs()
        plugin.saveDefaultConfig()
        getConfig(JFiles.CONFIG)
    }

    private fun getFile(configFile: JFiles): File {
        return File(plugin.dataFolder, configFile.filename)
    }

    fun getConfig(configFile: JFiles): FileConfiguration {
        val file = getFile(configFile)
        if (!file.exists()) {
            plugin.logger.log(Level.WARNING, "File not found: ${file.name}. Falling back to default or generating new.")
            plugin.saveResource(configFile.filename, false)
            return YamlConfiguration.loadConfiguration(file)
        }
        return YamlConfiguration.loadConfiguration(file)
    }

    fun saveConfig(configFile: JFiles) {
        try {
            val file = getFile(configFile)
            getConfig(configFile).save(file)
            plugin.logger.log(Level.INFO, "${file.name} saved successfully.")
        } catch (e: Exception) {
            plugin.logger.log(Level.SEVERE, "Could not save ${configFile.filename}: ${e.message}")
        }
    }

    fun reloadFrameConfig(){
        teamViewConfig.reload()
    }
}
