package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Config.TeamLBView.scoreTeam
import me.justlime.betterTeamGUI.config.Service
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.SkullMeta

class TeamLeaderBoard(rows: Int, title: String, val teamPlayer: TeamPlayer) : GUIHandler {
    enum class SortType {
        MONEY, SCORE
    }

    private var sortType = SortType.MONEY
    private val inventory = Bukkit.createInventory(this, rows * 9, title)

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val backSlot = Config.TeamLBView.backSlot
        val backSlots = Config.TeamLBView.backSlots
        val backSection = Config.backItem
        GUIManager.createCertainItem(backSection, backSlot, backSlots, inventory)

        val sortItem = Config.TeamLBView.sortType
        val slot = sortItem.getInt("slot")
        val slots = sortItem.getIntegerList("slot")
        GUIManager.createCertainItem(sortItem, slot, slots, inventory)

        val balanceTeam = Config.TeamLBView.balanceTeam
        val scoreTeam = Config.TeamLBView.scoreTeam
        val sortType = Config.TeamLBView.sortType
        val sortedTeamByBalance = Team.getTeamManager().sortTeamsByBalance()
        val sortedTeamByScore = Team.getTeamManager().sortTeamsByScore()
        when (this.sortType) {
            SortType.MONEY -> {
                sortedTeamByBalance.forEachIndexed { index, it ->
                    addItem(index, it)
                }
            }

            SortType.SCORE -> {
                sortedTeamByScore.forEachIndexed { index, it ->
                    addItem(index, it)
                }
            }
        }

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val backSlot = Config.TeamLBView.backSlot
        val backSlots = Config.TeamLBView.backSlots
        val sortSlot = Config.TeamLBView.sortType.getString("slot", " ")?.toIntOrNull()
        if (event.slot == sortSlot) {
            when (sortType) {
                SortType.MONEY -> {
                    this.sortType = SortType.SCORE
                }

                SortType.SCORE -> {
                    this.sortType = SortType.MONEY
                }
            }
            inventory.clear()
            loadInventory(player)
            return
        }
        if (event.slot in backSlots || event.slot == backSlot) {
            GUIManager.openTeamGUI(player)
            return
        }
        if (event.currentItem?.type == Material.PLAYER_HEAD) {
            val headOwner = (event.currentItem?.itemMeta as SkullMeta).owningPlayer ?: return
            val team = Team.getTeam(headOwner) ?: return
            val teamPlayer = team.getTeamPlayer(headOwner) ?: return
            GUIManager.openTeamOtherGUI(player, team, teamPlayer)
            return
        }

    }

    override fun getInventory(): Inventory {
        return inventory
    }

    private fun addItem(index: Int, it: String) {
        val sortedTeam = Team.getTeam(it) ?: return
        val owner = sortedTeam.members.getRank(PlayerRank.OWNER).first()
        val name = scoreTeam.getString("name")?.replace("{pos}", "${index + 1}") ?: " "
        val lore = scoreTeam.getStringList("lore").map { Service.applyLocalPlaceHolder(it, sortedTeam, teamPlayer) }.toMutableList()
        val item = GUIManager.createHeadItem(sortedTeam, owner.player, lore)

        val itemMeta = item.itemMeta
        itemMeta?.setDisplayName(Service.applyLocalPlaceHolder(name, sortedTeam, teamPlayer))
        item.itemMeta = itemMeta
        val itemSlot = inventory.firstEmpty()
        inventory.setItem(itemSlot, item)
    }

}