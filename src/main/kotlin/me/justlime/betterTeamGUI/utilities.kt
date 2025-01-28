package me.justlime.betterTeamGUI

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

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
