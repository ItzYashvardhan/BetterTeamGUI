package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class TeamMemberManagementGUI(rows: Int, title: String, val team: Team, val teamPlayer: TeamPlayer) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, rows * 9, title)
    private val confirmed: MutableMap<ManagementType, Int> = mutableMapOf()
    private val sections = mutableMapOf(
        ManagementType.DEMOTE to Config.TeamMemberManagementView.demote,
        ManagementType.PROMOTE to Config.TeamMemberManagementView.promote,
        ManagementType.KICK to Config.TeamMemberManagementView.kick,
        ManagementType.BAN to Config.TeamMemberManagementView.ban,
        ManagementType.CONFIRM to Config.TeamMemberManagementView.confirm
    )

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val backSlot = Config.TeamMemberManagementView.backSlot
        val backSlots = Config.TeamMemberManagementView.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
        sections.values.forEach { GUIManager.loadItem(it, inventory, team, mutableListOf(), teamPlayer) }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        val backSlot = Config.TeamMemberManagementView.backSlot
        val backSlots = Config.TeamMemberManagementView.backSlots
        val demoteSlot = sections[ManagementType.DEMOTE]?.getString("slot", " ")?.toIntOrNull()
        val promoteSlot = sections[ManagementType.PROMOTE]?.getString("slot", " ")?.toIntOrNull()
        val kickSlot = sections[ManagementType.KICK]?.getString("slot", " ")?.toIntOrNull()
        val banSlot = sections[ManagementType.BAN]?.getString("slot", " ")?.toIntOrNull()

        when (event.slot) {
            in backSlots, backSlot -> {
                GUIManager.openTeamGUI(player)
            }

            demoteSlot -> {
                if (!confirmed.containsKey(ManagementType.DEMOTE)) {
                    val confirmedSection = sections[ManagementType.CONFIRM] ?: return
                    confirmed.clear()
                    sections.values.forEach { GUIManager.loadItem(it, inventory, team, mutableListOf(), teamPlayer) }
                    GUIManager.loadItem(confirmedSection, inventory, team, mutableListOf(demoteSlot), teamPlayer)
                    confirmed[ManagementType.DEMOTE] = 0
                    return
                }
                player.performCommand("team:team demote ${teamPlayer.player.name}")
                GUIManager.closeInventory(player)
            }

            promoteSlot -> {
                if (!confirmed.containsKey(ManagementType.PROMOTE)) {
                    val confirmedSection = sections[ManagementType.CONFIRM] ?: return
                    confirmed.clear()
                    sections.values.forEach { GUIManager.loadItem(it, inventory, team, mutableListOf(), teamPlayer) }
                    GUIManager.loadItem(confirmedSection, inventory, team, mutableListOf(promoteSlot), teamPlayer)
                    confirmed[ManagementType.PROMOTE] = 0
                    return
                }
                player.performCommand("team:team promote ${teamPlayer.player.name}")
                GUIManager.closeInventory(player)
            }

            kickSlot -> {
                if (!confirmed.containsKey(ManagementType.KICK)) {
                    val confirmedSection = sections[ManagementType.CONFIRM] ?: return
                    confirmed.clear()
                    sections.values.forEach { GUIManager.loadItem(it, inventory, team, mutableListOf(), teamPlayer) }
                    GUIManager.loadItem(confirmedSection, inventory, team, mutableListOf(kickSlot), teamPlayer)
                    confirmed[ManagementType.KICK] = 0
                    return
                }
                player.performCommand("team:team kick ${teamPlayer.player.name}")
                GUIManager.closeInventory(player)
            }

            banSlot -> {
                if (!confirmed.containsKey(ManagementType.BAN)) {
                    val confirmedSection = sections[ManagementType.CONFIRM] ?: return
                    confirmed.clear()
                    sections.values.forEach { GUIManager.loadItem(it, inventory, team, mutableListOf(), teamPlayer) }
                    GUIManager.loadItem(confirmedSection, inventory, team, mutableListOf(banSlot), teamPlayer)
                    confirmed[ManagementType.BAN] = 0
                    return
                }
                player.performCommand("team:team ban ${teamPlayer.player.name}")
                GUIManager.closeInventory(player)
            }

        }
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    enum class ManagementType {
        DEMOTE, PROMOTE, KICK, BAN, CONFIRM
    }
}