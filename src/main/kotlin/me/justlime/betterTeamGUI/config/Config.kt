package me.justlime.betterTeamGUI.config

import me.justlime.betterTeamGUI.pluginInstance

object Config {
    private val teamDetail = pluginInstance.config.getConfigurationSection("team-info")
        ?: pluginInstance.config.createSection("team-info")

    object TeamInfo {
        val teamName: String = teamDetail.getString("name") ?: "Unknown" // Default: "Unknown"
        val head: Boolean = teamDetail.getBoolean("head", false) // Default: false
        val lore: List<String> = teamDetail.getStringList("lore").ifEmpty { listOf("Default Lore") } // Default: List
        val teamJoin: String = pluginInstance.config.getString("team-join") ?: "&aYou have joined {team} Team" // Default: "&aYou have joined {team} Team"
        val teamClosed: String = pluginInstance.config.getString("team-closed") ?: "&cYou are not invited to join {team} team" // Default: "&cYou are not invited to join {team} team"
        val teamAlreadyJoined: String = pluginInstance.config.getString("team-already-joined") ?: "&cYou are already in {team} Team" // Default: "&cYou are already in {team} Team"
    }
}