package me.justlime.betterTeamGUI.config

import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration

object Config {

    lateinit var config: FileConfiguration
    lateinit var teamCreateForm: FileConfiguration
    lateinit var listView: FileConfiguration
    lateinit var listForm: FileConfiguration
    lateinit var teamForm: FileConfiguration
    lateinit var warpsView: FileConfiguration
    lateinit var warpForm: FileConfiguration
    lateinit var membersView: FileConfiguration
    lateinit var memberForm: FileConfiguration
    lateinit var inviteForm: FileConfiguration
    lateinit var otherTeamView: FileConfiguration
    lateinit var otherTeamForm: FileConfiguration
    lateinit var leaveView: FileConfiguration
    lateinit var leaveForm: FileConfiguration
    lateinit var balanceView: FileConfiguration
    lateinit var balanceForm: FileConfiguration
    lateinit var allyView: FileConfiguration
    lateinit var allyForm: FileConfiguration
    lateinit var memberManagementView: FileConfiguration
    lateinit var memberManagementForm: FileConfiguration
    lateinit var teamLBView: FileConfiguration

    fun reload() {
        config = ConfigManager.getConfig(JFiles.CONFIG)
        teamCreateForm = ConfigManager.getConfig(JFiles.TEAMCREATEFORM)
        listView = ConfigManager.getConfig(JFiles.LISTVIEW)
        listForm = ConfigManager.getConfig(JFiles.LISTFORM)
        teamForm = ConfigManager.getConfig(JFiles.TEAMFORM)
        warpsView = ConfigManager.getConfig(JFiles.WARPSVIEW)
        warpForm = ConfigManager.getConfig(JFiles.TEAMWARPFORM)
        membersView = ConfigManager.getConfig(JFiles.MEMBERSVIEW)
        otherTeamView = ConfigManager.getConfig(JFiles.OTHERTEAMVIEW)
        leaveView = ConfigManager.getConfig(JFiles.LEAVEVIEW)
        leaveForm = ConfigManager.getConfig(JFiles.LEAVEFORM)
        balanceView = ConfigManager.getConfig(JFiles.BALANCEVIEW)
        balanceForm = ConfigManager.getConfig(JFiles.BALANCEFORM)
        allyView = ConfigManager.getConfig(JFiles.ALLYVIEW)
        allyForm = ConfigManager.getConfig(JFiles.ALLYFORM)
        memberManagementView = ConfigManager.getConfig(JFiles.MEMBERMANAGEMENTVIEW)
        teamLBView = ConfigManager.getConfig(JFiles.TEAMLBVIEW)
        otherTeamForm = ConfigManager.getConfig(JFiles.OTHERTEAMFORM)
        memberForm = ConfigManager.getConfig(JFiles.TEAMMEMBER)
        memberManagementForm = ConfigManager.getConfig(JFiles.TEAMMEMBERMANAGEMENTFORM)
        inviteForm = ConfigManager.getConfig(JFiles.INVITEFORM)
        ConfigManager.reloadFrameConfig()


    }

    val backItem
        get() = config.getConfigurationSection("back-item") ?: config.createSection("back-item")

    val backButton
        get() = config.getConfigurationSection("back-button") ?: config.createSection("back-button")

    val avatarUrl
        get() = config.getString("Avatar") ?: "https://mc-heads.net/avatar/{playername}"

    private val teamDetail
        get() = listView.getConfigurationSection("team-info") ?: listView.createSection("team-info")

    object TeamInfo {
        val teamName: String
            get() = teamDetail.getString("name") ?: "Unknown" // Default: "Unknown"

        val lore: List<String>
            get() = teamDetail.getStringList("lore").ifEmpty { listOf("Default Lore") } // Default: List

        val teamJoin: String
            get() = config.getString("team-join") ?: "&aYou have joined {team} Team"

        val teamClosed: String
            get() = config.getString("team-closed") ?: "&cYou are not invited to join {team} team"

        val teamAlreadyJoined: String
            get() = config.getString("team-already-joined") ?: "&cYou are already in {team} Team"
    }

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

    object TeamLeaveView {
        val title get() = leaveView.getString("main.title", "Leave Team") ?: ""
        val row get() = leaveView.getInt("main.row", 1)
        val backSlot get() = leaveView.getInt("main.back-slot", 8)
        val backSlots: MutableList<Int> get() = leaveView.getIntegerList("main.back-slot")
        val confirm get() = leaveView.getConfigurationSection("confirm") ?: leaveView.createSection("confirm")
        val cancel get() = leaveView.getConfigurationSection("cancel") ?: leaveView.createSection("cancel")
    }

    object TeamListView {
        val title get() = listView.getString("main.title", "Teams List") ?: ""
        val row get() = listView.getInt("main.row", 6)
        val backSlot get() = listView.getInt("main.back-slot", 49)
        val backSlots: MutableList<Int> get() = listView.getIntegerList("main.back-slot")
    }

    object TeamListForm {
        val title get() = listForm.getString("main.title", "Teams List") ?: ""
        val text get() = listForm.getStringList("main.text").toMutableList()
        val teams get() = listForm.getConfigurationSection("teams") ?: listForm.createSection("teams")
        val create get() = listForm.getConfigurationSection("create") ?: listForm.createSection("create")
    }

    object TeamMemberView {
        val title get() = membersView.getString("main.title", "Teams List") ?: ""
        val row get() = membersView.getInt("main.row", 6)
        val backSlot get() = membersView.getInt("main.back-slot", 49)
        val backSlots: MutableList<Int> get() = membersView.getIntegerList("main.back-slot")
        val owner get() = membersView.getConfigurationSection("owner") ?: membersView.createSection("owner")
        val member get() = membersView.getConfigurationSection("member") ?: membersView.createSection("member")
        val admin get() = membersView.getConfigurationSection("admin") ?: membersView.createSection("admin")
        val manage get() = membersView.getConfigurationSection("manage") ?: membersView.createSection("manage")
        val invite get() = membersView.getConfigurationSection("invite") ?: membersView.createSection("invite")
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

    object TeamMemberManagementView {
        val title get() = memberManagementView.getString("main.title", "Team Member Management") ?: ""
        val row get() = memberManagementView.getInt("main.row", 3)
        val backSlot get() = memberManagementView.getInt("main.back-slot", 16)
        val backSlots: MutableList<Int> get() = memberManagementView.getIntegerList("main.back-slot")
        val demote get() = memberManagementView.getConfigurationSection("demote") ?: memberManagementView.createSection("demote")
        val promote get() = memberManagementView.getConfigurationSection("promote") ?: memberManagementView.createSection("promote")
        val kick get() = memberManagementView.getConfigurationSection("kick") ?: memberManagementView.createSection("kick")
        val ban get() = memberManagementView.getConfigurationSection("ban") ?: memberManagementView.createSection("ban")
        val confirm get() = memberManagementView.getConfigurationSection("confirmed") ?: memberManagementView.createSection("confirm")
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

    object TeamWarpView {
        val title get() = warpsView.getString("main.title", "Teams Warps") ?: ""
        val row get() = warpsView.getInt("main.row", 6)
        val item get() = warpsView.getConfigurationSection("warp") ?: warpsView.createSection("warp")
        val backSlot get() = warpsView.getInt("main.back-slot", 49)
        val backSlots: MutableList<Int> get() = warpsView.getIntegerList("warps.back-slot")
    }

    object TeamWarpForm {
        val title get() = warpForm.getString("main.title", "Teams Warps") ?: ""
        val text: MutableList<String> get() = warpForm.getStringList("main.text").toMutableList()
        val warpBtn get() = warpForm.getConfigurationSection("warp") ?: warpForm.createSection("warp")
    }

    object TeamBalanceView {
        val title get() = balanceView.getString("main.title", "Team Balance") ?: ""
        val row get() = balanceView.getInt("main.row", 3)
        val backSlot get() = balanceView.getInt("main.back-slot", 22)
        val backSlots: MutableList<Int> get() = balanceView.getIntegerList("main.back-slot")
        val deposit get() = balanceView.getConfigurationSection("withdraw") ?: balanceView.createSection("withdraw")
        val withdraw get() = balanceView.getConfigurationSection("deposit") ?: balanceView.createSection("deposit")

    }

    object TeamBalanceForm {
        val title get() = balanceForm.getString("main.title", "Team Balance") ?: ""
        val text: MutableList<String> get() = balanceForm.getStringList("main.text").toMutableList()
        val label: String get() = balanceForm.getString("label", "") ?: ""
        val placeholder: String get() = balanceForm.getString("placeholder", "") ?: ""
        val withdraw get() = balanceForm.getConfigurationSection("withdraw") ?: balanceForm.createSection("withdraw")
        val deposit get() = balanceForm.getConfigurationSection("deposit") ?: balanceForm.createSection("deposit")
    }

    object TeamAllyView {
        val title get() = allyView.getString("main.title", "Team Allies") ?: ""
        val row get() = allyView.getInt("main.row", 6)
        val backSlot get() = allyView.getInt("main.back-slot", 49)
        val backSlots: MutableList<Int> get() = allyView.getIntegerList("main.back-slot")
    }

    object TeamAllyForm {
        val title get() = allyForm.getString("main.title", "Team Allies") ?: ""
        val text: MutableList<String> get() = allyForm.getStringList("main.text").toMutableList()
        val allies get() = allyForm.getConfigurationSection("allies") ?: allyForm.createSection("allies")
    }

    object TeamOtherView {
        val title get() = otherTeamView.getString("main.title", "Team Other") ?: ""
        val row get() = otherTeamView.getInt("main.row", 6)
        val backSlot get() = otherTeamView.getInt("main.back-slot", 16)
        val backSlots: MutableList<Int> get() = config.getIntegerList("main.back-slot")
        val info get() = otherTeamView.getConfigurationSection("info") ?: otherTeamView.createSection("info")
        val ally get() = otherTeamView.getConfigurationSection("ally") ?: otherTeamView.createSection("ally")
        val member get() = otherTeamView.getConfigurationSection("member") ?: otherTeamView.createSection("member")
        val balance get() = otherTeamView.getConfigurationSection("balance") ?: otherTeamView.createSection("balance")
    }

    object TeamOtherForm {
        val title get() = otherTeamForm.getString("main.title", "Team Other") ?: ""
        val text: MutableList<String> get() = otherTeamForm.getStringList("main.text").toMutableList()
        val ally get() = otherTeamForm.getConfigurationSection("ally") ?: otherTeamForm.createSection("ally")
        val member get() = otherTeamForm.getConfigurationSection("member") ?: otherTeamForm.createSection("member")
        val join get() = otherTeamForm.getConfigurationSection("join") ?: otherTeamForm.createSection("join")

    }

    object TeamLBView {
        val title get() = teamLBView.getString("main.title", "Team Leaderboard") ?: ""
        val row get() = teamLBView.getInt("main.row", 6)
        val backSlot get() = teamLBView.getInt("main.back-slot", 49)
        val backSlots: MutableList<Int> get() = teamLBView.getIntegerList("main.back-slot")
        val balanceTeam get() = teamLBView.getConfigurationSection("balance-team") ?: teamLBView.createSection("balance-team")
        val scoreTeam get() = teamLBView.getConfigurationSection("score-team") ?: teamLBView.createSection("score-team")
        val sortType get() = teamLBView.getConfigurationSection("sort-type") ?: teamLBView.createSection("sort-type")
    }

    val background: Material
        get() = Material.valueOf(config.getString("background") ?: "WHITE_STAINED_GLASS_PANE")

}
