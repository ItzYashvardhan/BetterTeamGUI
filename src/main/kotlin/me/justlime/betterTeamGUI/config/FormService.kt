package me.justlime.betterTeamGUI.config

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.clip.placeholderapi.PlaceholderAPI
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import java.util.regex.Pattern

object FormService {

    fun applyLocalPlaceHolder(value: String, team: Team, teamPlayer: TeamPlayer): String {
        val ownerName = team.members.getRank(PlayerRank.OWNER).firstOrNull()?.player?.name ?: "N/A"
        val members = team.members.get().filter { it.rank != PlayerRank.OWNER }.mapNotNull { it.player.name }
        val replaced = value.replace("{team}", team.name).replace("{owner}", ownerName).replace("{count}", team.members.size().toString())
            .replace("{total}", team.teamLimit.toString()).replace("{balance}", team.balance.toString()).replace("{level}", team.level.toString())
            .replace("{tag}", team.tag ?: "").replace("{score}", team.score.toString()).replace("{open}", if (team.isOpen) "&aOpen" else "&cClosed")
            .replace("{chat}", if (teamPlayer.isInAllyChat) "Ally" else if (teamPlayer.isInTeamChat) "Team" else "Global")
            .replace("{player}", teamPlayer.player.name.toString())
        return applyPlaceHolder(applyColors(replaced), teamPlayer.player)
    }

    fun applyColors(message: String): String {
        var coloredMessage = ChatColor.translateAlternateColorCodes('&', message)
        val hexPattern = Pattern.compile("&#[a-fA-F0-9]{6}")
        val matcher = hexPattern.matcher(coloredMessage)
        while (matcher.find()) {
            val hexCode = matcher.group()
            val bukkitHexCode = "\u00A7x" + hexCode.substring(2).toCharArray().joinToString("") { "\u00A7$it" }
            coloredMessage = coloredMessage.replace(hexCode, bukkitHexCode)
        }
        return coloredMessage
    }

    fun applyPlaceHolder(text: String, player: OfflinePlayer): String {
        if (pluginInstance.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setPlaceholders(player, text)
        }
        return text
    }
}