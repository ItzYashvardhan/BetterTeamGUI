package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.items.TeamListItem
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI

fun teamList(guiSetting: GUISetting, teamPlayer: TeamPlayer): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {
    val teams = Team.getTeamManager().sortTeamsByScore().mapNotNull { Team.getTeamManager().getTeam(it) }
    onClick { it.isCancelled = true }
    nav {
        margin = 3
    }
    TeamListItem.background.forEach { setItem(it) }

    addPage(6, "Regular Page {page}") {
        teams.forEach { team ->
            val owner = team.members.getRank(PlayerRank.OWNER).random()
            val item = TeamListItem.teamItem.apply {
                this?.texture = "[${owner.player.name}]"
                this?.placeholderOfflinePlayer = owner.player
            }?.copy()
            addItem(item)
        }
    }
//    addPage(6, "Regular Page {page}") {
//        //this item added to specific page only (page 1)
//        for (i in 1..100) {
//            val item1 = GuiItem(Material.MAP)
//            val newItem = item1.copy(name = "Item $i")
//            addItem(newItem) {
//                it.whoClicked.sendMessage("Removed Item at ${it.currentItem?.itemMeta?.displayName}")
//            }
//        }
//
//        //Runs for only specific Page (1)
//    }
}