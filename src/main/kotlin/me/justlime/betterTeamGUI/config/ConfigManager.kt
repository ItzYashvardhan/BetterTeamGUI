package me.justlime.betterTeamGUI.config

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.logging.Level

object ConfigManager {

    lateinit var config: FileConfiguration
    lateinit var messages: FileConfiguration
    lateinit var buttons: FileConfiguration
    lateinit var colorsView: FileConfiguration
    lateinit var font: FileConfiguration
    lateinit var teamCreateForm: FileConfiguration
    lateinit var listView: FileConfiguration
    lateinit var listForm: FileConfiguration
    lateinit var teamForm: FileConfiguration
    lateinit var teamView: FileConfiguration
    lateinit var warpsView: FileConfiguration
    lateinit var warpForm: FileConfiguration
    lateinit var membersView: FileConfiguration
    lateinit var memberForm: FileConfiguration
    lateinit var inviteForm: FileConfiguration
    lateinit var teamViewer: FileConfiguration
    lateinit var otherTeamForm: FileConfiguration
    lateinit var leaveView: FileConfiguration
    lateinit var leaveForm: FileConfiguration
    lateinit var moneyView: FileConfiguration
    lateinit var balanceForm: FileConfiguration
    lateinit var alliesView: FileConfiguration
    lateinit var allyForm: FileConfiguration
    lateinit var memberManagementView: FileConfiguration
    lateinit var memberManagementForm: FileConfiguration
    lateinit var leaderBoardView: FileConfiguration
    lateinit var settingView: FileConfiguration
    lateinit var banView: FileConfiguration



    fun load() {
        config = loadConfig(JFiles.CONFIG)
        messages = loadConfig(JFiles.MESSAGES)
        buttons = loadConfig(JFiles.BUTTONS)
        colorsView = loadConfig(JFiles.COLORS)
        font = loadConfig(JFiles.FONT)
        teamCreateForm = loadConfig(JFiles.TEAM_CREATE_FORM)
        listView = loadConfig(JFiles.LIST_VIEW)
        listForm = loadConfig(JFiles.LIST_FORM)
        teamForm = loadConfig(JFiles.TEAM_FORM)
        teamView = loadConfig(JFiles.TEAM_VIEW)
        warpsView = loadConfig(JFiles.WARPS_VIEW)
        warpForm = loadConfig(JFiles.WARP_FORM)
        membersView = loadConfig(JFiles.MEMBERS_VIEW)
        teamViewer = loadConfig(JFiles.TEAM_VIEWER)
        leaveView = loadConfig(JFiles.DIALOG_VIEW)
        leaveForm = loadConfig(JFiles.LEAVE_FORM)
        moneyView = loadConfig(JFiles.MONEY_VIEW)
        balanceForm = loadConfig(JFiles.BALANCE_FORM)
        alliesView = loadConfig(JFiles.ALLIES_VIEW)
        allyForm = loadConfig(JFiles.ALLY_FORM)
        memberManagementView = loadConfig(JFiles.MEMBER_MANAGEMENT_VIEW)
        leaderBoardView = loadConfig(JFiles.LEADERBOARD_VIEW)
        otherTeamForm = loadConfig(JFiles.OTHER_TEAM_FORM)
        memberForm = loadConfig(JFiles.TEAM_MEMBER)
        memberManagementForm = loadConfig(JFiles.TEAM_MEMBER_MANAGEMENT_FORM)
        inviteForm = loadConfig(JFiles.INVITE_FORM)
        settingView = loadConfig(JFiles.SETTING_VIEW)
        banView = loadConfig(JFiles.BAN_VIEW)
    }

    private fun getFile(configFile: JFiles): File {
        return File(pluginInstance.dataFolder, configFile.filename)
    }

    private fun loadConfig(configFile: JFiles): FileConfiguration {
        if (!pluginInstance.dataFolder.exists()) pluginInstance.dataFolder.mkdir()
        val file = getFile(configFile)

        if (!file.exists()) {
            ConsoleMessage.printStep("Generated new ${file.name}")
            pluginInstance.saveResource(configFile.filename, false)
        }
        pluginInstance.saveResource(configFile.filename, true)

        val config = YamlConfiguration.loadConfiguration(file)
        return config
    }

    fun saveConfig(configFile: JFiles): Boolean {
        return try {
            when (configFile) {
                JFiles.CONFIG -> config
                JFiles.MESSAGES -> messages
                JFiles.BUTTONS -> buttons
                JFiles.FONT -> font
                JFiles.COLORS -> colorsView
                JFiles.TEAM_CREATE_FORM -> teamCreateForm
                JFiles.LIST_VIEW -> listView
                JFiles.LIST_FORM -> listForm
                JFiles.TEAM_FORM -> teamForm
                JFiles.TEAM_VIEW -> teamView
                JFiles.WARPS_VIEW -> warpsView
                JFiles.WARP_FORM -> warpForm
                JFiles.MEMBERS_VIEW -> membersView
                JFiles.TEAM_MEMBER -> memberForm
                JFiles.INVITE_FORM -> inviteForm
                JFiles.TEAM_VIEWER -> teamViewer
                JFiles.OTHER_TEAM_FORM -> otherTeamForm
                JFiles.DIALOG_VIEW -> leaveView
                JFiles.LEAVE_FORM -> leaveForm
                JFiles.MONEY_VIEW -> moneyView
                JFiles.BALANCE_FORM -> balanceForm
                JFiles.ALLIES_VIEW -> alliesView
                JFiles.ALLY_FORM -> allyForm
                JFiles.MEMBER_MANAGEMENT_VIEW -> memberManagementView
                JFiles.TEAM_MEMBER_MANAGEMENT_FORM -> memberManagementForm
                JFiles.LEADERBOARD_VIEW -> leaderBoardView
                JFiles.SETTING_VIEW -> settingView
                JFiles.BAN_VIEW -> banView
            }.save(File(pluginInstance.dataFolder, configFile.filename))
            true
        } catch (e: Exception) {
            pluginInstance.logger.log(Level.SEVERE, "Could not save ${configFile.filename}: ${e.message}")
            false
        }
    }

}
