package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamWarpItem
import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun teamWarp(guiSetting: GUISetting, team: Team): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {
    val warpItem = TeamWarpItem.warpItem ?: return@ChestGUI
    onClick { it.isCancelled = true }

    val backgroundItem = GUIManager.getBackgroundGuiItem()
    backgroundItem.forEach { setItem(it) }

    val warps = team.warps.get()
    warps.forEach { warp ->
        val warpItemCopy = warpItem.copy() // Important otherwise all display name or lore will be same
        warpItemCopy.name = warpItemCopy.name.replace("{warp}", warp.name)
        warpItemCopy.lore = warpItemCopy.lore.map { it.replace("{warp}", warp.name) }.toMutableList()

        addItem(warpItemCopy) {
            val player = it.whoClicked as? Player ?: return@addItem

            Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                player.performCommand("team:team warp ${warp.name}")
            }, 2)
            GUIManager.closeInventory(player)
        }
    }
    val backItem = TeamButton.back
    val backItemSlot = TeamButton.backSlot?.slot
    setItem(backItem, backItemSlot) {}
}

