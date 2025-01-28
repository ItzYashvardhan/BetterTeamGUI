package me.justlime.betterTeamGUI.config

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import org.bukkit.ChatColor
import java.util.regex.Pattern

object Service {

    fun applyLocalPlaceHolder(value: String, team: Team): String {
        val replaced = value.replace("{team}", team.name).replace("{owner}", team.members.getRank(PlayerRank.OWNER).first().player?.name ?: "")
            .replace("{count}", team.members.size().toString()).replace("{total}", team.teamLimit.toString())
            .replace("{balance}", team.balance.toString()).replace("{level}", team.level.toString()).replace("{tag}", team.tag)
            .replace("{score}", team.score.toString())
            .replace("{members}", team.members.get().filter { PlayerRank.OWNER != it.rank }.map { it.player.name }.joinToString(", "))
            .replace("{open}",if(team.isOpen) "&aOpen" else "&cClosed")
        return applyColors(replaced)
    }

    private fun applyColors(message: String): String {
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

}