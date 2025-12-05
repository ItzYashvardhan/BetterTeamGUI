package me.justlime.betterTeamGUI.gui.java

import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.ColorPickerItem
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.utilities.TeamService
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.ChatColor
import org.bukkit.entity.Player

fun colorPickerView(setting: GUISetting, player: Player) = ChestGUI(setting.copy()) {
    onClick { it.isCancelled = true }
    ColorPickerItem.background.forEach { setItem(it) }
    setItem(TeamButton.back, ColorPickerItem.backSlot) { GUIManager.openTeamSettingGUI(player) }
    setItem(TeamButton.home, ColorPickerItem.homeSlot) { GUIManager.openTeamGUI(player) }

    setItem(ColorPickerItem.aqua) { TeamService.setTeamColor(player, ChatColor.AQUA.name) }
    setItem(ColorPickerItem.black) { TeamService.setTeamColor(player, ChatColor.BLACK.name) }
    setItem(ColorPickerItem.blue) { TeamService.setTeamColor(player, ChatColor.BLUE.name) }
    setItem(ColorPickerItem.darkAqua) { TeamService.setTeamColor(player, ChatColor.DARK_AQUA.name) }
    setItem(ColorPickerItem.darkBlue) { TeamService.setTeamColor(player, ChatColor.DARK_BLUE.name) }
    setItem(ColorPickerItem.darkGray) { TeamService.setTeamColor(player, ChatColor.DARK_GRAY.name) }
    setItem(ColorPickerItem.darkGreen) { TeamService.setTeamColor(player, ChatColor.DARK_GREEN.name) }
    setItem(ColorPickerItem.darkPurple) { TeamService.setTeamColor(player, ChatColor.DARK_PURPLE.name) }
    setItem(ColorPickerItem.darkRed) { TeamService.setTeamColor(player, ChatColor.DARK_RED.name) }
    setItem(ColorPickerItem.gold) { TeamService.setTeamColor(player, ChatColor.GOLD.name) }
    setItem(ColorPickerItem.gray) { TeamService.setTeamColor(player, ChatColor.GRAY.name) }
    setItem(ColorPickerItem.green) { TeamService.setTeamColor(player, ChatColor.GREEN.name) }
    setItem(ColorPickerItem.red) { TeamService.setTeamColor(player, ChatColor.RED.name) }
    setItem(ColorPickerItem.strikethrough) { TeamService.setTeamColor(player, ChatColor.STRIKETHROUGH.name) }
    setItem(ColorPickerItem.white) { TeamService.setTeamColor(player, ChatColor.WHITE.name) }
    setItem(ColorPickerItem.yellow) { TeamService.setTeamColor(player, ChatColor.YELLOW.name) }
}
