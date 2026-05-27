package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.StonecutterInventory
import org.geysermc.floodgate.api.FloodgateApi

fun isBedrockPlayer(player: Player): Boolean =
    if (pluginInstance.server.pluginManager.isPluginEnabled("Floodgate")) FloodgateApi.getInstance()
        .isFloodgatePlayer(player.uniqueId) else false

val Team.bannedPlayersList: List<String>
    get() {
        return try {
            val field = Team::class.java.getDeclaredField("bannedPlayers")
            field.isAccessible = true
            val component = field.get(this) ?: return emptyList() // This is the BanSetComponent object
            val getMethod = component.javaClass.getMethod("get")
            val rawCollection = getMethod.invoke(component) as? Collection<*>
            rawCollection?.map { it.toString() } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
//Utilities for GUI
//fun permissionDenied(event: InventoryClickEvent, style: GuiStyleSheet) {
//    val oldItem = event.item ?: return
//    val noPermissionItem = TeamButton(LanguageBroker.getGui(event.whoClicked as Player, "buttons.yml")).noPermission
//        ?: GuiItem(Material.BARRIER)
//    event.item = noPermissionItem
//    event.update(style)
//    foliaLib.scheduler.runLater(Runnable {
//        event.item = oldItem
//        event.update(style)
//    }, 30)
//}