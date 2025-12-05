package me.justlime.betterTeamGUI.gui.java

import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamDialogItem
import me.justlime.betterTeamGUI.utilities.TeamService
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamLeaveDialog(setting: GUISetting) = ChestGUI(setting.copy()) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.leaveBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.leaveConfirmItem
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.leaveTeam(p)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.leaveCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                GUIManager.openTeamGUI(p)
            }
        }
    }

}

fun teamDisbandDialog(setting: GUISetting) = ChestGUI(setting.copy()) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.disbandBackground.forEach { setItem(it) }

    // Disband View
    val disbandConfirmItem = TeamDialogItem.disbandConfirmItem
    if (disbandConfirmItem != null) {
        setItem(disbandConfirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { player ->
                TeamService.disbandTeam(player)
                GUIManager.closeInventory(player)
            }
        }
    }

    val disbandCancelItem = TeamDialogItem.disbandCancelItem
    if (disbandCancelItem != null) {
        setItem(disbandCancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                GUIManager.openTeamSettingGUI(p)
            }
        }
    }

}

fun teamDeleteHomeDialog(setting: GUISetting) = ChestGUI(setting.copy()) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.deleteHomeBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.deleteHomeConfirmItem
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.removeHome(p)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.deleteHomeCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                GUIManager.openTeamGUI(p)
            }
        }
    }
}

fun teamUpdateHomeDialog(setting: GUISetting) = ChestGUI(setting.copy()) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.updateHomeBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.updateHomeConfirmItem
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.setHome(p)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.updateHomeCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                GUIManager.openTeamGUI(p)
            }
        }
    }
}
