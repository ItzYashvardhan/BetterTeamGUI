package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.models.GuiItem

object LeaderBoardItem : BaseGuiItem(JFiles.LEADERBOARD_VIEW.filename) {

    var teamLeaderboardItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        teamLeaderboardItem = config.loadItem(JGui.LeaderBoardView.TEAM_LEADERBOARD_ITEM)
    }
}