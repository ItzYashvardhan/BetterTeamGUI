package me.justlime.betterTeamGUI.gui

import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

val pendingTransactions = mutableMapOf<UUID, Boolean>()

class TeamBalanceAnvilGUI(val player: Player, private val isDeposit: Boolean) : GUIHandler {
    private val inventory = Bukkit.createInventory(this, InventoryType.ANVIL, if (isDeposit) "§lDeposit Amount" else "§lWithdraw Amount")

    override fun onOpen(event: InventoryOpenEvent) {

    }

    override fun loadInventory(player: Player) {
        val item = ItemStack(Material.EMERALD)
        val meta = item.itemMeta
        meta?.setDisplayName(" ") // Placeholder text
        item.itemMeta = meta
        inventory.setItem(0, item) // Slot 0: Input item

        // Store whether this is a deposit or withdraw request
        pendingTransactions[player.uniqueId] = isDeposit
    }

    override fun onClick(event: InventoryClickEvent) {
        player.sendMessage("✅ You clicked om " + inventory.type.name)
        if (event.slot == 2) {
            event.isCancelled = true // Prevent item from disappearing
            val item = inventory.getItem(2) // Get the renamed item
            if (item != null && item.type != Material.AIR) {
                // Give item to player
                player.inventory.addItem(item)

                // Clear result slot so the anvil doesn't eat the item
                inventory.setItem(2, null)

                player.sendMessage("✅ You received: ${item.itemMeta?.displayName}")
            }

        }
        return
    }

    override fun onClose(event: InventoryCloseEvent) {

    }

    override fun getInventory(): Inventory {
        return inventory
    }

    override fun onAnvilRename(event: PrepareAnvilEvent) {
        pluginInstance.logger.info("hello")
        val name = event.result?.itemMeta?.displayName
        pluginInstance.logger.info(name)
//        player.forEach {
//            val renamedItem = event.result ?: return
//            val isDeposit = pendingTransactions[it.uniqueId] ?: return
//            val amountStr = renamedItem.itemMeta?.displayName ?: return
//            val amount = amountStr.toIntOrNull()
//
//            if (amount == null || amount <= 0) {
//                it.sendMessage("§cInvalid amount entered.")
//                return
//            }
//
//            val command = if (isDeposit) "team deposit $amount" else "team withdraw $amount"
//            Bukkit.dispatchCommand(it, command)
//
//            pendingTransactions.remove(it.uniqueId)
//        }
    }
}