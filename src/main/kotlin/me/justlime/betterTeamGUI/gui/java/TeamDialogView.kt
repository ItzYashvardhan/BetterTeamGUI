package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.TeamDialogItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.teamPlayerToPlaceholderMap
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

fun teamLeaveDialog(setting: GUISetting) = ChestGUI(setting) {
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

fun teamDisbandDialog(setting: GUISetting) = ChestGUI(setting) {
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

fun teamDeleteHomeDialog(setting: GUISetting) = ChestGUI(setting) {
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

fun teamUpdateHomeDialog(setting: GUISetting) = ChestGUI(setting) {
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

fun teamPromoteToOwnerDialog(setting: GUISetting, targetTeamPlayer: TeamPlayer) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.promoteToOwnerBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.promoteToOwnerConfirmItem?.clone().apply {
        this?.customPlaceholder = teamPlayerToPlaceholderMap(targetTeamPlayer)
    }
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.promote(p, targetTeamPlayer)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.promoteToOwnerCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                val team = Team.getTeam(p.name) ?: return@let
                GUIManager.openTeamMemberManagementGUI(p, targetTeamPlayer, team)
            }
        }
    }
}

fun teamPromoteToAdminDialog(setting: GUISetting, targetTeamPlayer: TeamPlayer) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.promoteToAdminBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.promoteToAdminConfirmItem?.clone().apply {
        this?.customPlaceholder = teamPlayerToPlaceholderMap(targetTeamPlayer)
    }
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.promote(p, targetTeamPlayer)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.promoteToAdminCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                val team = Team.getTeam(p.name) ?: return@let
                GUIManager.openTeamMemberManagementGUI(p, targetTeamPlayer, team)
            }
        }
    }
}

fun teamDemoteToAdminDialog(setting: GUISetting, targetTeamPlayer: TeamPlayer) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.demoteToAdminBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.demoteToAdminConfirmItem?.clone().apply {
        this?.customPlaceholder = teamPlayerToPlaceholderMap(targetTeamPlayer)
    }
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.demote(p, targetTeamPlayer)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.demoteToAdminCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                val team = Team.getTeam(p.name) ?: return@let
                GUIManager.openTeamMemberManagementGUI(p, targetTeamPlayer, team)
            }
        }
    }
}


fun teamDemoteToDefaultDialog(setting: GUISetting, targetTeamPlayer: TeamPlayer) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.demoteToDefaultBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.demoteToDefaultConfirmItem?.clone().apply {
        this?.customPlaceholder = teamPlayerToPlaceholderMap(targetTeamPlayer)
    }
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.demote(p, targetTeamPlayer)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.demoteToDefaultCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                val team = Team.getTeam(p.name) ?: return@let
                GUIManager.openTeamMemberManagementGUI(p, targetTeamPlayer, team)
            }
        }
    }
}

fun teamKickDialog(setting: GUISetting, targetTeamPlayer: TeamPlayer) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.kickBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.kickConfirmItem?.clone().apply {
        this?.customPlaceholder = teamPlayerToPlaceholderMap(targetTeamPlayer)
    }
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.kick(p, targetTeamPlayer)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.kickCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                val team = Team.getTeam(p.name) ?: return@let
                GUIManager.openTeamMemberManagementGUI(p, targetTeamPlayer, team)
            }
        }
    }
}

fun teamBanDialog(setting: GUISetting, targetTeamPlayer: TeamPlayer) = ChestGUI(setting) {
    onClick { it.isCancelled = true }
    // Background & Static Items
    TeamDialogItem.banBackground.forEach { setItem(it) }

    // Confirm Item
    val confirmItem = TeamDialogItem.banConfirmItem?.clone().apply {
        this?.customPlaceholder = teamPlayerToPlaceholderMap(targetTeamPlayer)
    }
    if (confirmItem != null) {
        setItem(confirmItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                TeamService.ban(p, targetTeamPlayer)
                GUIManager.closeInventory(p)
            }
        }
    }

    // Cancel Item
    val cancelItem = TeamDialogItem.banCancelItem
    if (cancelItem != null) {
        setItem(cancelItem) { clickEvent ->
            (clickEvent.whoClicked as? Player)?.let { p ->
                val team = Team.getTeam(p.name) ?: return@let
                GUIManager.openTeamMemberManagementGUI(p, targetTeamPlayer, team)
            }
        }
    }
}
