package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Main.plugin
import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.Team.getTeam
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.gui.GUIManager.createHeadItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.SkullMeta

class TeamListGUI(rows: Int, title: String) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, rows * 9, title)
    override fun getInventory(): Inventory {
        return inventory
    }

    override fun onOpen(event: InventoryOpenEvent) {
        GUIManager.insertBackground(inventory)
    }

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)

        Team.getTeamManager().loadTeams()
        val teams = Team.getTeamManager().loadedTeamListClone
        teams.forEach { (_, team) ->
            val owner = team.members.getRank(PlayerRank.OWNER).first()
            val itemLore = Config.TeamInfo.lore.flatMap {
                if (it.contains("{members}")) {
                    val memberPart = it.split(" ").find { part -> part.contains("{members}") }

                    val colorPrefix = memberPart?.substringBefore("{members}") ?: "&7"
                    val tMembers = team.members.get().filter { a -> a.rank != PlayerRank.OWNER }.mapNotNull { p -> p.player.name }

                    val wrappedText = wrapText(tMembers)

                    wrappedText.split("\n").mapIndexed { index, line ->

                        val finalLine = if (index == 0) {
                            it.replace("{members}", line)
                        } else {
                            "$colorPrefix$line"
                        }
                        Service.applyLocalPlaceHolder(finalLine, team, owner)
                    }
                } else {
                    listOf(Service.applyLocalPlaceHolder(it, team, owner))
                }
            }.toMutableList()
            val playerHeadItem = createHeadItem(team, owner.player, itemLore)
            inventory.addItem(playerHeadItem)
        }

        if (Team.getTeamManager().isInTeam(player)) {
            val backSlot = Config.TeamListItem.backSlot
            val backSlots = Config.TeamListItem.backSlots
            val backSection = Config.backItem
            val teamPlayer = getTeam(player.name)?.getTeamPlayer(player) ?: return
            GUIManager.loadItem(backSection, inventory, getTeam(player.name), if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)

        }

    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val player = event.whoClicked as? Player ?: return

        val item = event.currentItem ?: return

        // Handle back button logic
        val backSlot = Config.TeamListItem.backSlot
        val backSlots = Config.TeamListItem.backSlots
        if (event.slot == backSlot || event.slot in backSlots) {
            GUIManager.openTeamGUI(player)
            return
        }

        if (item.type != Material.PLAYER_HEAD) return

        val owner = (item.itemMeta as? SkullMeta)?.owningPlayer ?: return
        val team = getTeam(owner) ?: return
        val teamOwner = team.getRank(PlayerRank.OWNER).first() ?: return

        getTeam(player)?.let {

            player.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamAlreadyJoined, it, teamOwner))
            return

        }

        when {
            team.getTeamPlayer(player) != null -> {
                player.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamAlreadyJoined, team, teamOwner))
            }

            !team.isOpen && !team.isInvited(player.uniqueId) -> {
                player.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamClosed, team,teamOwner))
            }

            else -> {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                    team.join(player)
                    player.sendMessage(Service.applyLocalPlaceHolder(Config.TeamInfo.teamJoin, team,teamOwner))
                })
            }
        }

        if (!team.isOpen || !team.isInvited(player.uniqueId) || team.getTeamPlayer(player) == null) {
            player.closeInventory()
        }
    }

    override fun onClose(event: InventoryCloseEvent) {
        return
    }

    private fun wrapText(names: List<String>): String {
        val maxLineLength = 40
        val result = StringBuilder()
        var line = StringBuilder()

        for ((index, name) in names.withIndex()) {
            if (line.isNotEmpty() && line.length + name.length + 2 > maxLineLength) { // +2 for ", "
                result.append(line.toString().trim()).append("\n")
                line = StringBuilder()
            }
            line.append(name)
            if (index != names.size - 1) { // Add comma only if it's not the last name
                line.append(", ")
            }
        }

        if (line.isNotEmpty()) {
            result.append(line.toString().trim()) // Append last line
        }

        return result.toString()
    }

}