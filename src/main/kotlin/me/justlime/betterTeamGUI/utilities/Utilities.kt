package me.justlime.betterTeamGUI.utilities

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.BetterTeamGUI
import net.justlime.limeframegui.models.GuiItem
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.geysermc.floodgate.api.FloodgateApi
import java.util.*

fun isBedrockPlayer(player: Player): Boolean =
    if (BetterTeamGUI.INSTANCE.server.pluginManager.isPluginEnabled("Floodgate")) FloodgateApi.getInstance()
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

fun uuidMapper(
    uUID: UUID,
    baseTemplate: GuiItem
): GuiItem {
    val invitedPlayer = Bukkit.getOfflinePlayer(uUID)
    val invitationPlaceholders = mapOf(
        "team_player" to (invitedPlayer.name ?: "Unknown")
    )
    return baseTemplate.clone().apply {
        this.style.offlinePlayer = invitedPlayer
        this.style.placeholder.putAll(invitationPlaceholders)
        this.style.viewer = null
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