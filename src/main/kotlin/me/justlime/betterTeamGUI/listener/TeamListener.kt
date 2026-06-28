package me.justlime.betterTeamGUI.listener

import com.booksaw.betterTeams.customEvents.CreateTeamEvent
import me.justlime.betterTeamGUI.BetterTeamGUI
import me.justlime.betterTeamGUI.config.ConfigManager
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class TeamListener() : Listener {

    @EventHandler
    fun onTeamCreate(event: CreateTeamEvent) {
        val isEnable = ConfigManager.config.getBoolean("random-team-color",true)
        if (isEnable) {
            val randomColor: ChatColor = getRandomChatColor()
            event.team.color = randomColor
        }
    }

    /**
     * Picks a random valid Bukkit ChatColor.
     */
    private fun getRandomChatColor(): ChatColor {
        val validColors = ChatColor.values().filter { it.isColor }
        return validColors.random()
    }
}