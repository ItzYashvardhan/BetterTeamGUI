package me.justlime.betterTeamGUI

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.tcoded.folialib.FoliaLib
import me.justlime.betterTeamGUI.commands.CommandManager
import me.justlime.betterTeamGUI.listener.InventoryListener
import me.justlime.betterTeamGUI.listing.*
import me.justlime.betterTeamGUI.listing.common.TeamAlliesManager
import me.justlime.betterTeamGUI.listing.common.TeamMembersManager
import me.justlime.betterTeamGUI.listing.inbox.TeamInvitationManager
import me.justlime.betterTeamGUI.listing.viewer.TeamListManager
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import net.justlime.limeframegui.api.LimeFrameAPI
import net.justlime.limeframegui.config.GuiDirectoryHandler
import net.justlime.limeframegui.enums.ColorType
import net.justlime.limeframegui.registry.component.PlaceholderRegistry
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin
import java.net.HttpURLConnection
import java.net.URI

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
        setupLimeFrameGUI()
//        Config.reload()
        ConsoleMessage.printStep("LimeFrame Setup Completed")
//        Config.reload()
        server.pluginManager.registerEvents(InventoryListener(), this)


        if (!this.server.pluginManager.isPluginEnabled("BetterTeams")) {
            ConsoleMessage.printStep("BETTERTEAMS PLUGIN REQUIRED", ConsoleMessage.Color.RED)
            ConsoleMessage.printStep("Disabling BetterTeamsGUI")
            this.server.pluginManager.disablePlugin(this)

        }

//        if (ConfigManager.config.getBoolean(JGui.Config.USE_NATIVE_COMMAND, true)) {
//            try {
//                TeamCommandProxy.inject() //Experimental}
//            } catch (e: Exception) {
//                ConsoleMessage.printStep("Failed to Inject GUI Commands", ConsoleMessage.Color.RED)
//                ConsoleMessage.printStep("Error: ${e.message}", ConsoleMessage.Color.BRIGHT_RED)
//            }
//        }
        CommandManager.register()
        ConsoleMessage.printStep("Successfully Registered Commands")
        Metrics(this, 24705)

        ConsoleMessage.printStep("Checking for Updates..")
        checkVersionFromBetterTeamGUIRepo()
        ConsoleMessage.printStep(
            "Successfully Enabled BetterTeamsGUI by ${this.description.authors.first()}",
            ConsoleMessage.Color.BRIGHT_GREEN
        )
        ConsoleMessage.printFooter()
    }

    override fun onDisable() {
        ConsoleMessage.printHeader()
        ConsoleMessage.printStep("Disabling BetterTeamGUI")
        ConsoleMessage.printFooter()
    }

    fun setupLimeFrameGUI() {
        LimeFrameAPI.setKeys {
            inventoryRows = "rows"
            material = "item"
            name = "name"
            lore = "lore"
            glow = "glow"
            slot = "slot"
            slotList = "slot"
            texture = "texture"
            flags = "flags"
        }
        LimeFrameAPI.enableFoliaLib()
        LimeFrameAPI.loadConfig("gui")
        GuiDirectoryHandler.reload(this, true)
        TeamListManager.registerPopulators()
        TeamMembersManager.registerPopulators()
        TeamAlliesManager.registerPopulators()
        TeamBanManager.registerPopulators()
        TeamLeaderboardManager.registerPopulators()
        TeamWarpsManager.registerPopulators()
        TeamLevelsManager.registerPopulators()
        TeamInvitationManager.registerPopulators()
        injectPlaceholder()
    }

    fun injectPlaceholder() {
        val allTeams = Team.getTeamManager().loadedTeamListClone.values
        PlaceholderRegistry.register("team_invites") { player, _ ->
            var inviteCount = 0
            for (team in allTeams) {
                if (team.invitedPlayers.contains(player.uniqueId)) {
                    inviteCount++
                }
            }
            inviteCount.toString()
        }
        PlaceholderRegistry.register("allies_request") { player, _ ->
            val team = Team.getTeamManager().getTeam(player)
            if (team == null) {
                "0"
            } else {
                team.allyRequests.size.toString()
            }
        }

        PlaceholderRegistry.register("team_anchor") { player, _ ->
            val team = Team.getTeam(player) ?: return@register "false"
            val uuid = team.anchoredPlayers.convertedList.find { it == player.uniqueId.toString() }
            if (uuid != null) "true" else "false"
        }

        PlaceholderRegistry.register("viewer") { viewer, _ ->
            viewer.name
        }

        PlaceholderRegistry.register("viewer_rank") { viewer, _ ->
            val team = Team.getTeam(viewer) ?: return@register "None"
            val teamPlayer = team.getTeamPlayer(viewer) ?: return@register ""
            val rank = teamPlayer.rank
            val result = when (rank) {
                PlayerRank.OWNER -> "Owner"
                PlayerRank.ADMIN -> "Admin"
                else -> "Default"
            }
            result
        }
    }
}

private fun checkVersionFromBetterTeamGUIRepo() {
    try {
        val url = URI("https://api.github.com/repos/ItzYashvardhan/BetterTeamGUI/tags").toURL()
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
                "Outdated Version Found:${ConsoleMessage.Color.WHITE} $currentVersion -> $latestTag",
                ConsoleMessage.Color.BRIGHT_YELLOW
            )
            ConsoleMessage.printStep("Modrinth", ConsoleMessage.Color.LIGHT_BLUE)
            ConsoleMessage.printStep("https://modrinth.com/plugin/betterteamsgui/versions", ConsoleMessage.Color.WHITE)
            ConsoleMessage.printStep("Download the latest version from above link", ConsoleMessage.Color.BRIGHT_PURPLE)

        } else {
            ConsoleMessage.printStep(
                "Latest version found${ConsoleMessage.Color.RESET}${ConsoleMessage.Color.WHITE} ($currentVersion)",
                ConsoleMessage.Color.GREEN
            )
        }

    } catch (e: Exception) {
        ConsoleMessage.printStep("Failed to check for updates", ConsoleMessage.Color.RED)
        ConsoleMessage.printStep(e.message ?: "Unidentified Version Error")
    }


}




