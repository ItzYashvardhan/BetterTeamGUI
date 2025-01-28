package me.justlime.betterTeamGUI.api

import me.justlime.betterTeamGUI.BetterTeamGUI
import me.justlime.betterTeamGUI.pluginInstance

object HookBetterTeamGUI {
    fun instance(): BetterTeamGUI {
        return pluginInstance
    }
}