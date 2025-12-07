package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.gui.items.TeamSettingItem.setting
import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.api.LimeFrameAPI
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.geysermc.floodgate.api.FloodgateApi

fun getPlayerHead(offlinePlayer: OfflinePlayer): ItemStack {

    // Create the player head item
    val playerHead = ItemStack(Material.PLAYER_HEAD, 1)

    // Set the head's meta to the player's information
    val meta = playerHead.itemMeta as? SkullMeta
    if (meta != null) {
        meta.owningPlayer = offlinePlayer // Assign the player to the head
        meta.setDisplayName("§a${offlinePlayer.name}'s Head") // Optional: Add a custom name
        playerHead.itemMeta = meta
    }
    return playerHead
}

fun isBedrockPlayer(player: Player): Boolean {
    return if (pluginInstance.server.pluginManager.isPluginEnabled("Floodgate")) FloodgateApi.getInstance().isFloodgatePlayer(player.uniqueId)
    else false
}

fun teamToPlaceholderMap(team: Team): Map<String, String> {
    return mapOf(
        "{team}" to (team.name ?: "N/A"),
        "{team_size}" to team.members.size().toString(),
        "{team_limit}" to team.teamLimit.toString(),
        "{team_level}" to team.level.toString(),
        "{team_score}" to team.score.toString(),
        "{team_money}" to team.money.toString(),
        "{anchor}" to team.isAnchored.toString(),
        "{team_description}" to team.description,
        "{team_color_code}" to "<" + team.color.name + ">",
        "{/team_color_code}" to "</" + team.color.name + ">",
    )
}

fun teamPlayerToPlaceholderMap(teamPlayer: TeamPlayer): Map<String, String> {
    return mapOf(
        "{rank}" to teamPlayer.rank.name,
        "{team_player}" to (teamPlayer.player.name ?: ""),
    )
}

fun permissionDenied(event: InventoryClickEvent) {
    val oldItem = event.item ?: return
    val noPermissionItem = TeamButton.noPermission ?: return
    event.item = noPermissionItem
    event.item?.styleSheet?.stylishName = setting.styleSheet?.stylishName ?: LimeFrameAPI.keys.stylishName
    event.item?.styleSheet?.stylishLore = setting.styleSheet?.stylishLore ?: LimeFrameAPI.keys.stylishLore
    event.update()
    Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
        event.item = oldItem
        event.update()
    }, 30)
}
