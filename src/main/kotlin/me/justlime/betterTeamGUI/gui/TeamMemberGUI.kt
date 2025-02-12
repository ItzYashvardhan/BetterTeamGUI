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

class TeamMemberGUI(rows: Int, title: String, val team: Team, val teamPlayer: TeamPlayer) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, rows * 9, title)
    private val section = mutableMapOf(
        MemberType.OWNER to Config.TeamMemberView.owner,
        MemberType.ADMIN to Config.TeamMemberView.admin,
        MemberType.MEMBER to Config.TeamMemberView.member,
        MemberType.MANGE to Config.TeamMemberView.manage
    )

    override fun loadInventory(player: Player) {
        GUIManager.insertBackground(inventory)
        val teamMembers = team.members.get()
        teamMembers.forEach {
            val item = GUIManager.createHeadItem(team, it.player)
            val itemMeta = item.itemMeta
            itemMeta?.apply {
                var cLore: MutableList<String> = mutableListOf()
                var name: String = ""
                val isInSameTeam = team.getTeamPlayer(teamPlayer.player) != null

                if (it.rank == PlayerRank.DEFAULT) {
                    name = section[MemberType.MEMBER]?.getString("name") ?: " "
                    cLore = section[MemberType.MEMBER]?.getStringList("lore")?.toMutableList() ?: mutableListOf()
                    if ((teamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.ADMIN) && isInSameTeam) {
                        cLore.addAll(section[MemberType.MANGE]?.getStringList("lore") ?: listOf())
                    }
                }
                if (it.rank == PlayerRank.ADMIN) {
                    name = section[MemberType.ADMIN]?.getString("name") ?: " "
                    cLore = section[MemberType.ADMIN]?.getStringList("lore")?.toMutableList() ?: mutableListOf()
                    if (teamPlayer.rank == PlayerRank.OWNER && isInSameTeam) {
                        cLore.addAll(section[MemberType.MANGE]?.getStringList("lore") ?: listOf())
                    }
                }
                if (it.rank == PlayerRank.OWNER) {
                    name = section[MemberType.OWNER]?.getString("name") ?: " "
                    cLore = section[MemberType.OWNER]?.getStringList("lore")?.toMutableList() ?: mutableListOf()
                }
                setDisplayName(Service.applyLocalPlaceHolder(name, team, it))
                lore = cLore.map { lore -> Service.applyLocalPlaceHolder(lore, team, it) }.toMutableList()
            }
            item.itemMeta = itemMeta
            inventory.addItem(item)
        }
        val backSlot = Config.TeamMemberView.backSlot
        val backSlots = Config.TeamMemberView.backSlots
        val backSection = Config.backItem
        GUIManager.loadItem(backSection, inventory, team, if (backSlots.isEmpty()) listOf(backSlot) else backSlots, teamPlayer)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val backSlot = Config.TeamMemberView.backSlot
        val backSlots = Config.TeamMemberView.backSlots
        val player = event.whoClicked as Player
        val clickedItem = event.currentItem ?: return

        when (event.slot) {
            in backSlots, backSlot -> {
                GUIManager.openTeamGUI(player)
            }
        }
        if (teamPlayer.rank == PlayerRank.DEFAULT) return
        val isInSameTeam = team.getTeamPlayer(player) != null
        if (!isInSameTeam) return
        if (clickedItem.type == Material.PLAYER_HEAD) {
            val clickedPlayer = (clickedItem.itemMeta as SkullMeta).owningPlayer
            val clickedTeamPlayer = team.getTeamPlayer(clickedPlayer) ?: return
            if (teamPlayer.rank == PlayerRank.ADMIN && (clickedTeamPlayer.rank == PlayerRank.OWNER || clickedTeamPlayer.rank == PlayerRank.ADMIN)) return
            if (teamPlayer.rank == PlayerRank.OWNER && clickedTeamPlayer.rank == PlayerRank.OWNER) return
            GUIManager.openTeamMemberManagementGUI(player, team, clickedTeamPlayer)
        }
    }

    override fun onClose(event: InventoryCloseEvent) {
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    enum class MemberType {
        OWNER, ADMIN, MEMBER, MANGE
    }

}