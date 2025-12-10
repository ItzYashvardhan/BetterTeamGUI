package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.gui.items.TeamButton
import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.models.GuiItem
import net.justlime.limeframegui.models.LimeStyleSheet
import net.justlime.limeframegui.utilities.item
import net.justlime.limeframegui.utilities.update
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.geysermc.floodgate.api.FloodgateApi

fun isBedrockPlayer(player: Player): Boolean {
    return if (pluginInstance.server.pluginManager.isPluginEnabled("Floodgate")) FloodgateApi.getInstance().isFloodgatePlayer(player.uniqueId)
    else false
}

fun permissionDenied(event: InventoryClickEvent, style: LimeStyleSheet) {
    val oldItem = event.item ?: return
    val noPermissionItem = TeamButton.noPermission ?: GuiItem(Material.BARRIER)
    event.item = noPermissionItem
    event.update(style)
    Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
        event.item = oldItem
        event.update(style)
    }, 30)
}

val Team.bannedPlayersList: List<String>
    get() {
        return try {
            // 1. Get the private 'bannedPlayers' field (which is a BanSetComponent)
            val field = Team::class.java.getDeclaredField("bannedPlayers")
            field.isAccessible = true
            val component = field.get(this) ?: return emptyList() // This is the BanSetComponent object

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