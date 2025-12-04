package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.config.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamMemberItem {

    val config = ConfigHandler(JFiles.MEMBERS_VIEW.filename)
    val setting = config.loadInventorySetting(JGui.Main.SETTING)
    val background = config.loadItems(JGui.Main.BACKGROUND)

    val back = ConfigManager.membersView.getIntegerList(JGui.Main.BACK_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.BACK_SLOT)) }
    val home = ConfigManager.membersView.getIntegerList(JGui.Main.HOME_SLOT).ifEmpty { listOf(ConfigManager.membersView.getInt(JGui.Main.HOME_SLOT)) }

    val memberItem = config.loadItem(JGui.MemberView.MEMBER_ITEM)

}