package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.SkullMeta

class TeamLeaderBoard(rows: Int, title: String, val team: Team, val teamPlayer: TeamPlayer) : GUIHandler {
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
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
        val sortItem = Config.TeamLBView.sortType
        val material = Material.valueOf(sortItem.getString("item") ?: "PAPER")
        val name = Service.applyLocalPlaceHolder(sortItem.getString("name")?.replace("{sort_type}", sortType.name) ?: "", team, teamPlayer)
        val newLore = sortItem.getStringList("lore").map {
            it.replace("{sort_type}", sortType.name)
            Service.applyLocalPlaceHolder(it, team, teamPlayer)
        }
        val glow = sortItem.getBoolean("glow")
        GUIManager.createItem(material, name, newLore, glow)
        val slot = sortItem.getInt("slot")
        inventory.setItem(slot, GUIManager.createItem(material, name, newLore, glow))

        val balanceTeam = Config.TeamLBView.balanceTeam
        val scoreTeam = Config.TeamLBView.scoreTeam
        val sortType = Config.TeamLBView.sortType
        val sortedTeamByBalance = Team.getTeamManager().sortTeamsByBalance()
        val sortedTeamByScore = Team.getTeamManager().sortTeamsByScore()
        when (this.sortType) {
            SortType.MONEY -> {
                sortedTeamByBalance.forEachIndexed() { index, it ->
                    val balTeam = Team.getTeam(it) ?: return
                    val owner = balTeam.members.getRank(PlayerRank.OWNER).first()
                    val name = balanceTeam.getString("name")?.replace("{pos}", "${index + 1}") ?: " "
                    val lore = balanceTeam.getStringList("lore").map { Service.applyLocalPlaceHolder(it, balTeam, teamPlayer) }.toMutableList()

                    val item = GUIManager.createHeadItem(team, owner.player, lore)
                    val itemMeta = item.itemMeta
                    itemMeta?.setDisplayName(Service.applyLocalPlaceHolder(name, balTeam, teamPlayer))
                    item.itemMeta = itemMeta
                    val slot = inventory.firstEmpty()
                    inventory.setItem(slot, item)
                }
            }

            SortType.SCORE -> {
                sortedTeamByScore.forEachIndexed { index, it ->
                    val scTeam = Team.getTeam(it) ?: return
                    val owner = scTeam.members.getRank(PlayerRank.OWNER).first()
                    val name = scoreTeam.getString("name")?.replace("{pos}", "${index + 1}") ?: " "
                    val lore = scoreTeam.getStringList("lore").map { Service.applyLocalPlaceHolder(it, scTeam, teamPlayer) }.toMutableList()
                    val item = GUIManager.createHeadItem(team, owner.player, lore)
                    val itemMeta = item.itemMeta
                    itemMeta?.setDisplayName(Service.applyLocalPlaceHolder(name, scTeam, teamPlayer))
                    item.itemMeta = itemMeta
                    val slot = inventory.firstEmpty()
                    inventory.setItem(slot, item)
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
        if(event.currentItem?.type == Material.PLAYER_HEAD) {
            val headOwner = (event.currentItem?.itemMeta as SkullMeta).owningPlayer ?: return
            val team = Team.getTeam(headOwner) ?: return
            val teamPlayer = team.getTeamPlayer(headOwner) ?: return
            GUIManager.openTeamOtherGUI(player, team, teamPlayer)
            return
        }

    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

}