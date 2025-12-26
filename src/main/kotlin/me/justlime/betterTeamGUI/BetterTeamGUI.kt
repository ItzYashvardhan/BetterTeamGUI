package me.justlime.betterTeamGUI

import com.tcoded.folialib.FoliaLib
import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.commands.TeamCommandProxy
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import me.justlime.betterTeamGUI.listener.InventoryListener
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import net.justlime.limeframegui.api.LimeFrameAPI
import loader.FontLoader
import loader.SoundLoader
import net.justlime.limeframegui.enums.ColorType
import org.bstats.bukkit.Metrics
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin
import java.net.HttpURLConnection
import java.net.URL

lateinit var pluginInstance: BetterTeamGUI
lateinit var foliaLib: FoliaLib

class BetterTeamGUI : JavaPlugin() {

    var lang = "en"

    override fun onEnable() {

        ConsoleMessage.printHeader()
        ConsoleMessage.printStep("Enabling BetterTeamGUI")
        pluginInstance = this
        foliaLib = FoliaLib(this)
        if (!this.dataFolder.exists()) this.dataFolder.mkdir()
        this.saveDefaultConfig()
        lang = this.config.getString("lang") ?: "en"
        when (lang) {
            "es-ar" -> {
                ConsoleMessage.printStep("Language set to Spanish (Argentina)", ConsoleMessage.Color.YELLOW)
                ConsoleMessage.printStep("Translated by HATOR", ConsoleMessage.Color.BRIGHT_GREEN)
            }
        }


        LimeFrameAPI.init(this, ColorType.MINI_MESSAGE)
        Config.reload()
        server.pluginManager.registerEvents(InventoryListener(), this)


        if (!this.server.pluginManager.isPluginEnabled("BetterTeams")) {
            ConsoleMessage.printStep("BETTERTEAMS PLUGIN REQUIRED", ConsoleMessage.Color.RED)
            ConsoleMessage.printStep("Disabling BetterTeamsGUI")
            this.server.pluginManager.disablePlugin(this)

        }

        if (ConfigManager.config.getBoolean(JGui.Config.USE_NATIVE_COMMAND, true)) {
            try {
                TeamCommandProxy.inject() //Experimental}
            } catch (e: Exception) {
                ConsoleMessage.printStep("Failed to Inject GUI Commands", ConsoleMessage.Color.RED)
                ConsoleMessage.printStep("Error: ${e.message}", ConsoleMessage.Color.BRIGHT_RED)
            }
        }
        CommandManager.register()
        ConsoleMessage.printStep("Successfully Registered Commands")
        Metrics(this, 24705)
        setupLimeFrameGUI()
        Config.reload()
        ConsoleMessage.printStep("LimeFrame Setup Completed")
        ConsoleMessage.printStep("Checking for Updates..")
        checkVersionFromBetterTeamGUIRepo()
        ConsoleMessage.printStep("Successfully Enabled BetterTeamsGUI by ${this.description.authors.first()}", ConsoleMessage.Color.BRIGHT_GREEN)
        ConsoleMessage.printFooter()
    }

    override fun onDisable() {
        ConsoleMessage.printHeader()
        ConsoleMessage.printStep("Disabling BetterTeamGUI")
        ConsoleMessage.printFooter()
    }

    fun setupLimeFrameGUI() {
        FontLoader.load(JFiles.FONT.filename)
        SoundLoader.load(JFiles.SOUND.filename)
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
        LimeFrameAPI.enableFoliaLib()
    }

}

private fun checkVersionFromBetterTeamGUIRepo() {
    try {
        val url = URL("https://api.github.com/repos/ItzYashvardhan/BetterTeamGUI/tags")
        val conn = url.openConnection() as HttpURLConnection
        conn.setRequestProperty("User-Agent", "Mozilla/5.0")
        conn.connectTimeout = 5000
        conn.readTimeout = 5000

        val response = conn.inputStream.bufferedReader().readText()

        val versionPattern = Regex("\"name\"\\s*:\\s*\"([^\"]+)\"")
        val match = versionPattern.find(response) ?: return

        val latestTag = match.groupValues[1].removePrefix("v")
        val currentVersion = pluginInstance.description.version

        if (currentVersion != latestTag) {
            ConsoleMessage.printStep(
                "Outdated Version Found:${ConsoleMessage.Color.WHITE} $currentVersion -> $latestTag", ConsoleMessage.Color.BRIGHT_YELLOW
            )
            ConsoleMessage.printStep("Modrinth", ConsoleMessage.Color.LIGHT_BLUE)
            ConsoleMessage.printStep("https://modrinth.com/plugin/betterteamsgui/versions", ConsoleMessage.Color.WHITE)
            ConsoleMessage.printStep("Download the latest version from above link", ConsoleMessage.Color.BRIGHT_PURPLE)

        } else {
            ConsoleMessage.printStep(
                "Latest version found${ConsoleMessage.Color.RESET}${ConsoleMessage.Color.WHITE} ($currentVersion)", ConsoleMessage.Color.GREEN
            )
        }

    } catch (e: Exception) {
        ConsoleMessage.printStep("Failed to check for updates", ConsoleMessage.Color.RED)
        ConsoleMessage.printStep(e.message ?: "Unidentified Version Error")
    }
}




