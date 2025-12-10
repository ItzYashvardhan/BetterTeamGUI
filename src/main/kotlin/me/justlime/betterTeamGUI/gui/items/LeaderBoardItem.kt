package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object LeaderBoardItem {
    var config = ConfigHandler(JFiles.LEADERBOARD_VIEW.filename)
    var setting = config.loadInventorySetting(JGui.Main.MAIN)
    var background = config.loadItems(JGui.Main.BACKGROUND)

    var backSlot = ConfigManager.leaderBoardView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.leaderBoardView.getInt(JGui.Main.BACK_SLOT)) }
    var homeSlot = ConfigManager.leaderBoardView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.leaderBoardView.getInt(JGui.Main.HOME_SLOT)) }
    var prevSlot = ConfigManager.leaderBoardView.getInt(JGui.Main.PREV_SLOT)
    var nextSlot = ConfigManager.leaderBoardView.getInt(JGui.Main.NEXT_SLOT)

    var teamLeaderboardItem = config.loadItem(JGui.LeaderBoardView.TEAM_LEADERBOARD_ITEM)

    fun reload() {
        config.reload()
        config = ConfigHandler(JFiles.LEADERBOARD_VIEW.filename)
        setting = config.loadInventorySetting(JGui.Main.MAIN)
        background = config.loadItems(JGui.Main.BACKGROUND)

        backSlot = ConfigManager.leaderBoardView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.leaderBoardView.getInt(JGui.Main.BACK_SLOT)) }
        homeSlot = ConfigManager.leaderBoardView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.leaderBoardView.getInt(JGui.Main.HOME_SLOT)) }
        prevSlot = ConfigManager.leaderBoardView.getInt(JGui.Main.PREV_SLOT)
        nextSlot = ConfigManager.leaderBoardView.getInt(JGui.Main.NEXT_SLOT)

        teamLeaderboardItem = config.loadItem(JGui.LeaderBoardView.TEAM_LEADERBOARD_ITEM)
    }

}