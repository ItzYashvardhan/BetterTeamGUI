package me.justlime.betterTeamGUI.config

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.clip.placeholderapi.PlaceholderAPI
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import java.util.regex.Pattern

object Service {

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

    fun removeColors(message: String): String {
        // Regex to match Minecraft color codes (§x§r§g§b§x§x§r) and simpler §x formats, as well as formatting codes like §l, §n, etc.
        val colorAndFormatCodePattern = Regex("\u00A7[x0-9a-fA-F](\u00A7[0-9a-fA-F]){5}|\u00A7[0-9a-fk-orA-FK-OR]")

        // Remove any color or formatting codes
        var plainMessage = message.replace(colorAndFormatCodePattern, "")

        // Remove alternate color codes like &#FFFFFF or &x
        plainMessage = plainMessage.replace(Regex("&[0-9a-fA-F]|&#[a-fA-F0-9]{6}"), "")

        return plainMessage
    }

    fun applyPlaceHolder(text: String, player: OfflinePlayer): String {
        if (pluginInstance.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setPlaceholders(player, text)
        }
        return text
    }

    fun applyPlaceHolder(text: MutableList<String>, player: OfflinePlayer): MutableList<String> {
        if (pluginInstance.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setPlaceholders(player, text)
        }
        return text
    }

}