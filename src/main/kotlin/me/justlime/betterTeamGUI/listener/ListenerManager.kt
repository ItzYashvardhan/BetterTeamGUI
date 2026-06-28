package me.justlime.betterTeamGUI.listener

import me.justlime.betterTeamGUI.BetterTeamGUI
import me.justlime.betterTeamGUI.utilities.ConsoleMessage
import net.justlime.limeframegui.enums.AnsiColor

object ListenerManager {

    fun register() {
        val plugin = BetterTeamGUI.INSTANCE
        plugin.server.pluginManager.registerEvents(InventoryListener(), plugin)
        plugin.server.pluginManager.registerEvents(TeamListener(), plugin)
        ConsoleMessage.printStep("Successfully Registered Listeners", AnsiColor.BRIGHT_GREEN)
    }

}