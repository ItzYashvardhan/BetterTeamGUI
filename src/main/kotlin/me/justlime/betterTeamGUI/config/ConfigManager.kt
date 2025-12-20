package me.justlime.betterTeamGUI.config

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.gui.items.*
import me.justlime.betterTeamGUI.pluginInstance
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import org.bukkit.command.CommandSender
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

object ConfigManager {

    lateinit var config: FileConfiguration
    lateinit var sound: FileConfiguration
    lateinit var messages: FileConfiguration

    //GUI
    lateinit var alliesView: FileConfiguration
    lateinit var banView: FileConfiguration
    lateinit var buttons: FileConfiguration
    lateinit var colorsView: FileConfiguration
    lateinit var dashBoardView: FileConfiguration
    lateinit var dialogView: FileConfiguration
    lateinit var font: FileConfiguration
    lateinit var leaderBoardView: FileConfiguration
    lateinit var levelsView: FileConfiguration
    lateinit var listView: FileConfiguration
    lateinit var memberManagementView: FileConfiguration
    lateinit var membersView: FileConfiguration
    lateinit var moneyView: FileConfiguration
    lateinit var settingView: FileConfiguration
    lateinit var teamViewer: FileConfiguration
    lateinit var warpsView: FileConfiguration

    //Form
    lateinit var allyForm: FileConfiguration
    lateinit var balanceForm: FileConfiguration
    lateinit var teamCreateForm: FileConfiguration
    lateinit var inviteForm: FileConfiguration
    lateinit var leaveForm: FileConfiguration
    lateinit var listForm: FileConfiguration
    lateinit var memberManagementForm: FileConfiguration
    lateinit var memberForm: FileConfiguration
    lateinit var otherTeamForm: FileConfiguration
    lateinit var teamForm: FileConfiguration
    lateinit var warpForm: FileConfiguration

    fun load(sender: CommandSender) {
        reloadSafely(JFiles.CONFIG.filename, sender) { config = loadConfig(JFiles.CONFIG) }
        reloadSafely(JFiles.MESSAGES.filename, sender) { messages = loadConfig(JFiles.MESSAGES) }
        reloadSafely(JFiles.SOUND.filename, sender) { sound = loadConfig(JFiles.SOUND) }
        reloadSafely(JFiles.ALLIES_VIEW.filename, sender) { alliesView = loadConfig(JFiles.ALLIES_VIEW); TeamAlliesItem.reload() }
        reloadSafely(JFiles.BAN_VIEW.filename, sender) { banView = loadConfig(JFiles.BAN_VIEW); BanListItem.reload() }
        reloadSafely(JFiles.BUTTONS.filename, sender) { buttons = loadConfig(JFiles.BUTTONS); TeamButton.reload() }
        reloadSafely(JFiles.COLORS.filename, sender) { colorsView = loadConfig(JFiles.COLORS); ColorPickerItem.reload() }
        reloadSafely(JFiles.DASHBOARD_VIEW.filename, sender) { dashBoardView = loadConfig(JFiles.DASHBOARD_VIEW); TeamDashboardItem.reload() }
        reloadSafely(JFiles.DIALOG_VIEW.filename, sender) { dialogView = loadConfig(JFiles.DIALOG_VIEW); TeamDialogItem.reload() }
        reloadSafely(JFiles.FONT.filename, sender) { font = loadConfig(JFiles.FONT) }
        reloadSafely(JFiles.LEADERBOARD_VIEW.filename, sender) { leaderBoardView = loadConfig(JFiles.LEADERBOARD_VIEW); LeaderBoardItem.reload() }
        reloadSafely(JFiles.LEVELS_VIEW.filename, sender) { levelsView = loadConfig(JFiles.LEVELS_VIEW); LevelItem.reload() }
        reloadSafely(JFiles.LIST_VIEW.filename, sender) { listView = loadConfig(JFiles.LIST_VIEW); TeamListItem.reload() }
        reloadSafely(JFiles.MEMBER_MANAGEMENT_VIEW.filename, sender) { memberManagementView = loadConfig(JFiles.MEMBER_MANAGEMENT_VIEW); TeamMemberManagementItem.reload() }
        reloadSafely(JFiles.MEMBERS_VIEW.filename, sender) { membersView = loadConfig(JFiles.MEMBERS_VIEW); TeamMemberItem.reload() }
        reloadSafely(JFiles.MONEY_VIEW.filename, sender) { moneyView = loadConfig(JFiles.MONEY_VIEW); TeamMoneyItem.reload() }
        reloadSafely(JFiles.SETTING_VIEW.filename, sender) { settingView = loadConfig(JFiles.SETTING_VIEW); TeamSettingItem.reload() }
        reloadSafely(JFiles.TEAM_VIEWER.filename, sender) { teamViewer = loadConfig(JFiles.TEAM_VIEWER); TeamViewerItems.reload() }
        reloadSafely(JFiles.WARPS_VIEW.filename, sender) { warpsView = loadConfig(JFiles.WARPS_VIEW); TeamWarpItem.reload() }

        //Forms
        reloadSafely(JFiles.ALLY_FORM.filename, sender) { allyForm = loadConfig(JFiles.ALLY_FORM) }
        reloadSafely(JFiles.BALANCE_FORM.filename, sender) { balanceForm = loadConfig(JFiles.BALANCE_FORM) }
        reloadSafely(JFiles.TEAM_CREATE_FORM.filename, sender) { teamCreateForm = loadConfig(JFiles.TEAM_CREATE_FORM) }
        reloadSafely(JFiles.INVITE_FORM.filename, sender) { inviteForm = loadConfig(JFiles.INVITE_FORM) }
        reloadSafely(JFiles.LEAVE_FORM.filename, sender) { leaveForm = loadConfig(JFiles.LEAVE_FORM) }
        reloadSafely(JFiles.LIST_FORM.filename, sender) { listForm = loadConfig(JFiles.LIST_FORM) }
        reloadSafely(JFiles.TEAM_MEMBER_MANAGEMENT_FORM.filename, sender) { memberManagementForm = loadConfig(JFiles.TEAM_MEMBER_MANAGEMENT_FORM) }
        reloadSafely(JFiles.TEAM_MEMBER.filename, sender) { memberForm = loadConfig(JFiles.TEAM_MEMBER) }
        reloadSafely(JFiles.OTHER_TEAM_FORM.filename, sender) { otherTeamForm = loadConfig(JFiles.OTHER_TEAM_FORM) }
        reloadSafely(JFiles.TEAM_FORM.filename, sender) { teamForm = loadConfig(JFiles.TEAM_FORM) }
        reloadSafely(JFiles.WARP_FORM.filename, sender) { warpForm = loadConfig(JFiles.WARP_FORM) }

        //Forms
        Config.teamCreateForm = teamCreateForm
        Config.listForm = listForm
        Config.teamForm = teamForm
        Config.warpForm = warpForm
        Config.memberForm = memberForm
        Config.inviteForm = inviteForm
        Config.otherTeamForm = otherTeamForm
        Config.leaveForm = leaveForm
        Config.balanceForm = balanceForm
        Config.allyForm = allyForm
        Config.memberManagementForm = memberManagementForm
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

        val config = YamlConfiguration()
        config.load(file)
        return config
    }

    var errorCount = 0
    fun reloadSafely(name: String, sender: CommandSender, action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            errorCount++
            (sender as? Player)?.sendMessage("[BetterTeamGUI] §cFailed to reload: $name")

            // Send a cleaner message to the console (removes the giant stack trace dump)
            val cause = e.cause ?: e
            val errorMessage = if (cause is InvalidConfigurationException) {
                "YAML Syntax Error: ${cause.message?.lines()?.firstOrNull() ?: "Unknown syntax error"}"
            } else {
                cause.message ?: "Unknown error"
            }

            ConsoleMessage.printNext("Failed to reload $name: $errorMessage ${ConsoleMessage.Color.RESET}", ConsoleMessage.Color.RED)

            // Only print full stack trace if you really want to debug
            // e.printStackTrace()
        }
    }

}
