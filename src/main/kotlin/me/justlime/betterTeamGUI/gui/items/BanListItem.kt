package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler
import net.justlime.limeframegui.models.GuiItem

object BanListItem : BaseGuiItem(JFiles.BAN_VIEW.filename) {

    var bannedPlayerItem: GuiItem? = null

    init {
        reloadItems()
    }

    override fun reloadItems() {
        bannedPlayerItem = config.loadItem(JGui.BanList.BANNED_PLAYER_ITEM)
    }

}