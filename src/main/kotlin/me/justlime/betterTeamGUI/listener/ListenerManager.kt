package me.justlime.betterTeamGUI.listener

import me.justlime.betterTeamGUI.BetterTeamGUI

object ListenerManager {
    fun register(plugin: BetterTeamGUI) {
        plugin.server.pluginManager.registerEvents(InventoryListener(), plugin)
    }
}