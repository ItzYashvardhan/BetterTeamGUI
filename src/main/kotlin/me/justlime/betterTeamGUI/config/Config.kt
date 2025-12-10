package me.justlime.betterTeamGUI.config

import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration

object Config {

    lateinit var teamCreateForm: FileConfiguration
    lateinit var listForm: FileConfiguration
    lateinit var teamForm: FileConfiguration
    lateinit var warpForm: FileConfiguration
    lateinit var memberForm: FileConfiguration
    lateinit var inviteForm: FileConfiguration
    lateinit var otherTeamForm: FileConfiguration
    lateinit var leaveForm: FileConfiguration
    lateinit var balanceForm: FileConfiguration
    lateinit var allyForm: FileConfiguration
    lateinit var memberManagementForm: FileConfiguration

    fun reload() {
        ConfigManager.load()
    }

    val backButton get() = ConfigManager.config.getConfigurationSection("back-button") ?: ConfigManager.config.createSection("back-button")

    val avatarUrl get() = ConfigManager.config.getString("Avatar") ?: "https://mc-heads.net/avatar/{playername}"

    object TeamCreateForm {
        val title get() = teamCreateForm.getString("main.title", "Create Team") ?: ""
        val text: MutableList<String> get() = teamCreateForm.getStringList("main.text").toMutableList()
        val label get() = teamCreateForm.getString("label", "") ?: ""
        val placeholder get() = teamCreateForm.getString("placeholder", "") ?: ""
    }

    object TeamLeaveForm {
        val title get() = leaveForm.getString("main.title", "Leave Team") ?: ""
        val text: MutableList<String> get() = leaveForm.getStringList("main.text").toMutableList()
        val confirm get() = leaveForm.getConfigurationSection("confirm") ?: leaveForm.createSection("confirm")
        val cancel get() = leaveForm.getConfigurationSection("cancel") ?: leaveForm.createSection("cancel")
    }

    object TeamSelfForm {
        val title get() = teamForm.getString("main.title", "Teams List") ?: ""
        val text: MutableList<String> get() = teamForm.getStringList("main.text").toMutableList()
        val chat get() = teamForm.getConfigurationSection("chat") ?: teamForm.createSection("chat")
        val home get() = teamForm.getConfigurationSection("home") ?: teamForm.createSection("home")
        val balance get() = teamForm.getConfigurationSection("balance") ?: teamForm.createSection("balance")
        val warp get() = teamForm.getConfigurationSection("warp") ?: teamForm.createSection("warp")
        val members get() = teamForm.getConfigurationSection("members") ?: teamForm.createSection("members")
        val enderchest get() = teamForm.getConfigurationSection("enderchest") ?: teamForm.createSection("enderchest")
        val pvp get() = teamForm.getConfigurationSection("pvp") ?: teamForm.createSection("pvp")
        val ally get() = teamForm.getConfigurationSection("ally") ?: teamForm.createSection("ally")
        val leave get() = teamForm.getConfigurationSection("leave") ?: teamForm.createSection("leave")
        val listButton get() = teamForm.getConfigurationSection("list") ?: teamForm.createSection("list")
        val settingButton get() = teamForm.getConfigurationSection("setting") ?: teamForm.createSection("setting")

    }

    object TeamListForm {
        val title get() = listForm.getString("main.title", "Teams List") ?: ""
        val text get() = listForm.getStringList("main.text").toMutableList()
        val teams get() = listForm.getConfigurationSection("teams") ?: listForm.createSection("teams")
        val create get() = listForm.getConfigurationSection("create") ?: listForm.createSection("create")
    }

    object TeamMemberForm {
        val title get() = memberForm.getString("main.title", "Teams Members") ?: ""
        val text: MutableList<String> get() = memberForm.getStringList("main.text").toMutableList()
        val owner get() = memberForm.getConfigurationSection("owner") ?: memberForm.createSection("owner")
        val member get() = memberForm.getConfigurationSection("member") ?: memberForm.createSection("member")
        val admin get() = memberForm.getConfigurationSection("admin") ?: memberForm.createSection("admin")
        val invite get() = memberForm.getConfigurationSection("invite") ?: memberForm.createSection("invite")

    }

    object TeamInviteForm {
        val title get() = inviteForm.getString("main.title", "Invite Members") ?: ""
        val text: MutableList<String> get() = inviteForm.getStringList("main.text").toMutableList()
        val playerBtn get() = inviteForm.getConfigurationSection("playerBtn") ?: inviteForm.createSection("player")
    }

    object TeamMemberManagementForm {
        val title get() = memberManagementForm.getString("main.title", "Team Member Management") ?: ""
        val text: MutableList<String> get() = memberManagementForm.getStringList("main.text").toMutableList()
        val demote get() = memberManagementForm.getConfigurationSection("demote") ?: memberManagementForm.createSection("demote")
        val promote get() = memberManagementForm.getConfigurationSection("promote") ?: memberManagementForm.createSection("promote")
        val kick get() = memberManagementForm.getConfigurationSection("kick") ?: memberManagementForm.createSection("kick")
        val ban get() = memberManagementForm.getConfigurationSection("ban") ?: memberManagementForm.createSection("ban")
        val confirm get() = memberManagementForm.getConfigurationSection("confirm") ?: memberManagementForm.createSection("confirm")
    }

    object TeamWarpForm {
        val title get() = warpForm.getString("main.title", "Teams Warps") ?: ""
        val text: MutableList<String> get() = warpForm.getStringList("main.text").toMutableList()
        val warpBtn get() = warpForm.getConfigurationSection("warp") ?: warpForm.createSection("warp")
    }

    object TeamBalanceForm {
        val title get() = balanceForm.getString("main.title", "Team Balance") ?: ""
        val text: MutableList<String> get() = balanceForm.getStringList("main.text").toMutableList()
        val label: String get() = balanceForm.getString("label", "") ?: ""
        val placeholder: String get() = balanceForm.getString("placeholder", "") ?: ""
        val withdraw get() = balanceForm.getConfigurationSection("withdraw") ?: balanceForm.createSection("withdraw")
        val deposit get() = balanceForm.getConfigurationSection("deposit") ?: balanceForm.createSection("deposit")
    }

    object TeamAllyForm {
        val title get() = allyForm.getString("main.title", "Team Allies") ?: ""
        val text: MutableList<String> get() = allyForm.getStringList("main.text").toMutableList()
        val allies get() = allyForm.getConfigurationSection("allies") ?: allyForm.createSection("allies")
    }

    object TeamOtherForm {
        val title get() = otherTeamForm.getString("main.title", "Team Other") ?: ""
        val text: MutableList<String> get() = otherTeamForm.getStringList("main.text").toMutableList()
        val ally get() = otherTeamForm.getConfigurationSection("ally") ?: otherTeamForm.createSection("ally")
        val member get() = otherTeamForm.getConfigurationSection("member") ?: otherTeamForm.createSection("member")
        val join get() = otherTeamForm.getConfigurationSection("join") ?: otherTeamForm.createSection("join")

    }

    val background: Material
        get() = Material.valueOf(ConfigManager.config.getString("background.yml") ?: "WHITE_STAINED_GLASS_PANE")

}
