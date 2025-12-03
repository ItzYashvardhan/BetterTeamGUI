package me.justlime.betterTeamGUI.gui.java

import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamLeaveItem
import me.justlime.betterTeamGUI.utilities.TeamService
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamLeave(setting: GUISetting, viewer: Player)= ChestGUI(setting.rows, setting.title){
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamLeaveItem.background.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamLeaveItem.confirmItem
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.leaveTeam(p)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamLeaveItem.cancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                GUIManager.openTeamGUI(p)
            }
        }
    }

}