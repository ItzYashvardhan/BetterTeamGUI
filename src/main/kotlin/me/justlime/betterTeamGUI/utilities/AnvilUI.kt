package me.justlime.betterTeamGUI.utilities

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.models.GuiItem
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

val legacySerializer = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build()
val adventure = BukkitAudiences.create(pluginInstance)

/**
 * Opens a generic Anvil GUI to get a text input from a player.
 *
 * @param player The player to show the GUI to.
 * @param title The title of the Anvil GUI window.
 * @param label The placeholder text displayed in the input field.
 * @param inputItem The item to display in the left (input) slot.
 * @param outputItem The item to display in the right (output) slot.
 * @param onInvalidInput A lambda that is called if the player tries to confirm with empty or default text.
 * @param onConfirm A lambda that is called with the valid text input when the player confirms.
 */
fun openAnvilGUI(player: Player, title: Component, label: Component, inputItem: GuiItem, outputItem: GuiItem, onInvalidInput: () -> Unit, onCancel: () -> Unit = {}, onConfirm: (String) -> Unit) {
    if (inputItem.smallCapsLore == null) inputItem.smallCapsLore = true
    if (inputItem.smallCapsName == null) inputItem.smallCapsName = true
    if (outputItem.smallCapsLore == null) outputItem.smallCapsLore = true
    if (outputItem.smallCapsName == null) outputItem.smallCapsName = true

    val jsonString: String = GsonComponentSerializer.gson().serialize(title)
    val displayPlaceholder = legacySerializer.serialize(label)
    val plainPlaceholder = PlainTextComponentSerializer.plainText().serialize(label)

    AnvilGUI.Builder().plugin(pluginInstance).jsonTitle(jsonString).text(displayPlaceholder).itemLeft(inputItem.toItemStack()).itemOutput(outputItem.toItemStack()).onClose {
        Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
            onCancel()
        }, 1)
        return@onClose
    }.onClick { slot, state ->
        if (slot != AnvilGUI.Slot.OUTPUT) {
            onCancel()
            return@onClick listOf(AnvilGUI.ResponseAction.close())
        }

        val fullInputText = state.text

        val userInput = if (fullInputText.startsWith(plainPlaceholder, ignoreCase = true)) {
            fullInputText.removePrefix(plainPlaceholder).trim()
        } else {
            fullInputText.trim()
        }

        if (userInput.isEmpty()) {
            onInvalidInput()
            val msg = ConfigManager.messages.getString("empty-input.chat") ?: ""
            val componentMsg = legacySerializer.deserialize(msg)
            adventure.player(player).sendMessage(componentMsg)
            Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                openAnvilGUI(player, title, label, inputItem, outputItem, onInvalidInput, onCancel, onConfirm)
            }, 30)
            return@onClick listOf(AnvilGUI.ResponseAction.close())
        }

        onConfirm(userInput)
        return@onClick listOf(AnvilGUI.ResponseAction.close())
    }.open(player)
}



