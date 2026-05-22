package me.justlime.betterTeamGUI.listener

import com.booksaw.betterTeams.events.InventoryManagement
import com.booksaw.betterTeams.message.MessageManager
import me.justlime.betterTeamGUI.foliaLib
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType

//This is used listen for Team Chest close for BetterTeams
class InventoryListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onAdminEnderChestClose(e: InventoryCloseEvent) {
        val player = e.player as? Player ?: return

        val enderChestTitle = MessageManager.getMessage("echest.echest")
        if (e.view.type != InventoryType.CHEST || e.view.title != enderChestTitle) {
            return
        }


        if (InventoryManagement.adminViewers.containsKey(player)) {
            foliaLib.scheduler.runAtEntityLater(player, Runnable {
//                val sound = ConfigManager.sound.getString("open-gui", "BLOCK.NOTE_BLOCK.CHIME, 2.0") ?: "BLOCK.NOTE_BLOCK.CHIME, 2.0"
//                val finalSound = GuiSound.loadSound(sound)
//                finalSound?.playSound(player)
//                GUIManager.openTeamGUI(player)
            }, 1L)
        }
    }
}