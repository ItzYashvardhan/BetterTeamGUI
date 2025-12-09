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

fun isBedrockPlayer(player: Player): Boolean {
    return if (pluginInstance.server.pluginManager.isPluginEnabled("Floodgate")) FloodgateApi.getInstance().isFloodgatePlayer(player.uniqueId)
    else false
}

fun teamToPlaceholderMap(team: Team): Map<String, String> {
    return mapOf(
        "{team}" to (team.name ?: "N/A"),
        "{tag}" to (team.tag ?: "N/A"),
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
    event.item?.style?.stylishName = setting.style?.stylishName ?: LimeFrameAPI.keys.stylishName
    event.item?.style?.stylishLore = setting.style?.stylishLore ?: LimeFrameAPI.keys.stylishLore
    event.update()
    Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
        event.item = oldItem
        event.update()
    }, 30)
}

val Team.bannedPlayersList: List<String>
    get() {
        return try {
            // 1. Get the private 'bannedPlayers' field (which is a BanSetComponent)
            val field = Team::class.java.getDeclaredField("bannedPlayers")
            field.isAccessible = true
            val component = field.get(this) // This is the BanSetComponent object

            if (component == null) return emptyList()

            // 2. The component likely wraps the data. We need to call its get() method.
            val getMethod = component.javaClass.getMethod("get")

            // 3. Invoke .get().
            // FIX: We cast to Collection<*> (wildcard) instead of Collection<String>.
            // This prevents the ClassCastException because we aren't asserting the content type yet.
            val rawCollection = getMethod.invoke(component) as? Collection<*>

            // 4. Safely map whatever is inside (UUIDs or Strings) to String using .toString()
            rawCollection?.map { it.toString() } ?: emptyList()

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }