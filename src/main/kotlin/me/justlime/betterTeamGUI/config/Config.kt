package me.justlime.betterTeamGUI.config

import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Material

object Config {

    private val config get() = pluginInstance.config

    private val teamDetail
        get() = config.getConfigurationSection("team-info") ?: config.createSection("team-info")

    private val selfTeamDetail
        get() = config.getConfigurationSection("self") ?: config.createSection("self")

    val backItem
        get() = config.getConfigurationSection("back-item") ?: config.createSection("back-item")

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

    object TeamSelfItem {
        val title get() = selfTeamDetail.getString("title") ?: ""
        val row get() = selfTeamDetail.getInt("row", 6)
        val chat get() = selfTeamDetail.getConfigurationSection("chat") ?: selfTeamDetail.createSection("chat")
        val home get() = selfTeamDetail.getConfigurationSection("home") ?: selfTeamDetail.createSection("home")
        val balance get() = selfTeamDetail.getConfigurationSection("balance") ?: selfTeamDetail.createSection("balance")
        val warp get() = selfTeamDetail.getConfigurationSection("warp") ?: selfTeamDetail.createSection("warp")
        val members get() = selfTeamDetail.getConfigurationSection("members") ?: selfTeamDetail.createSection("members")
        val enderchest get() = selfTeamDetail.getConfigurationSection("enderchest") ?: selfTeamDetail.createSection("enderchest")
        val pvp get() = selfTeamDetail.getConfigurationSection("pvp") ?: selfTeamDetail.createSection("pvp")
        val ally get() = selfTeamDetail.getConfigurationSection("ally") ?: selfTeamDetail.createSection("ally")
        val leave get() = selfTeamDetail.getConfigurationSection("leave") ?: selfTeamDetail.createSection("leave")
        val listItem get() = selfTeamDetail.getConfigurationSection("list-item") ?: selfTeamDetail.createSection("list-item")
        val settingItem get() = selfTeamDetail.getConfigurationSection("setting-item") ?: selfTeamDetail.createSection("setting-item")
    }

    object TeamLeaveItem {
        val title get() = config.getString("leave.title", "Leave Team") ?: ""
        val row get() = config.getInt("leave.row", 1)
        val confirm get() = config.getConfigurationSection("leave.confirm") ?: config.createSection("leave.confirm")
        val cancel get() = config.getConfigurationSection("leave.cancel") ?: config.createSection("leave.cancel")
        val backSlot get() = config.getInt("leave.back-slot", 8)
        val backSlots: MutableList<Int> get() = config.getIntegerList("leave.back-slot")
    }

    object TeamListItem {
        val title get() = config.getString("list.title", "Teams List") ?: ""
        val row get() = config.getInt("list.row", 6)
        val backSlot get() = config.getInt("list.back-slot", 49)
        val backSlots: MutableList<Int> get() = config.getIntegerList("list.back-slot")
    }

    object TeamMemberItem {
        val title get() = config.getString("members.title", "Teams List") ?: ""
        val row get() = config.getInt("members.row", 6)
        val backSlot get() = config.getInt("members.back-slot", 49)
        val backSlots: MutableList<Int> get() = config.getIntegerList("members.back-slot")
        val owner get() = config.getConfigurationSection("members.owner") ?: config.createSection("members.owner")
        val member get() = config.getConfigurationSection("members.member") ?: config.createSection("members.member")
        val admin get() = config.getConfigurationSection("members.admin") ?: config.createSection("members.admin")
        val manage get() = config.getConfigurationSection("members.manage") ?: config.createSection("members.manage")
    }

    object TeamMemberManagementItem {
        val title get() = config.getString("member-management.title", "Team Member Management") ?: ""
        val row get() = config.getInt("member-management.row", 3)
        val backSlot get() = config.getInt("member-management.back-slot", 16)
        val backSlots: MutableList<Int> get() = config.getIntegerList("member-management.back-slot")
        val demote get() = config.getConfigurationSection("member-management.demote") ?: config.createSection("member-management.demote")
        val promote get() = config.getConfigurationSection("member-management.promote") ?: config.createSection("member-management.promote")
        val kick get() = config.getConfigurationSection("member-management.kick") ?: config.createSection("member-management.kick")
        val ban get() = config.getConfigurationSection("member-management.ban") ?: config.createSection("member-management.ban")
        val confirm get() = config.getConfigurationSection("member-management.confirmed") ?: config.createSection("member-management.confirm")
    }


    object TeamWarpItem {
        val title get() = config.getString("warps.title", "Teams Warps") ?: ""
        val row get() = config.getInt("warps.row", 6)
        val item get() = config.getString("members.item", "ENDER_PEARL") ?: ""
        val backSlot get() = config.getInt("warps.back-slot", 49)
        val backSlots: MutableList<Int> get() = config.getIntegerList("warps.back-slot")
    }

    object TeamBalanceItem {
        val title get() = config.getString("balance.title", "Team Balance") ?: ""
        val row get() = config.getInt("balance.row", 3)
        val deposit get() = config.getConfigurationSection("balance.withdraw") ?: config.createSection("balance.withdraw")
        val withdraw get() = config.getConfigurationSection("balance.deposit") ?: config.createSection("balance.deposit")
        val backSlot get() = config.getInt("balance.back-slot", 22)
        val backSlots: MutableList<Int> get() = config.getIntegerList("balance.back-slot")
    }

    object TeamAllyItem {
        val title get() = config.getString("ally.title", "Team Allies") ?: ""
        val row get() = config.getInt("ally.row", 6)
        val backSlot get() = config.getInt("ally.back-slot", 49)
        val backSlots: MutableList<Int> get() = config.getIntegerList("ally.back-slot")
    }

    object TeamOtherItem{
        val title get() = config.getString("other.title", "Team Other") ?: ""
        val row get() = config.getInt("other.row", 6)
        val backSlot get() = config.getInt("other.back-slot", 16)
        val backSlots: MutableList<Int> get() = config.getIntegerList("other.back-slot")
        val info get() = config.getConfigurationSection("other.info") ?: config.createSection("other.info")
        val ally get() = config.getConfigurationSection("other.ally") ?: config.createSection("other.ally")
        val member get() = config.getConfigurationSection("other.member") ?: config.createSection("other.member")
        val balance get() = config.getConfigurationSection("other.balance") ?: config.createSection("other.balance")
    }

    object TeamCreateItem {
        val title get() = config.getString("create.title", "Create Team") ?: ""
        val row get() = config.getInt("create.row", 3)
        val name get() = config.getConfigurationSection("create.name") ?: config.createSection("create.name")
        val create get() = config.getConfigurationSection("create.create") ?: config.createSection("create.create")
        val cancel get() = config.getConfigurationSection("create.cancel") ?: config.createSection("create.cancel")
    }


    val background: Material
        get() = Material.valueOf(config.getString("background") ?: "WHITE_STAINED_GLASS_PANE")

}
