package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.Team
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.pluginInstance
import net.justlime.limeframegui.impl.ConfigHandler
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun teamWarp(guiSetting: GUISetting, config: ConfigHandler, team: Team): ChestGUI = ChestGUI(guiSetting.rows, guiSetting.title) {
    this.setting.placeholderPlayer = setting.placeholderPlayer
    val warpItem = config.loadItem("warp") ?: return@ChestGUI
    onClick { it.isCancelled = true }

    val backgroundItem = GUIManager.getBackgroundGuiItem()
    backgroundItem.forEach { setItem(it) }

    val warps = team.warps.get()
    warps.forEach { warp ->
        val warpItemCopy = warpItem.copy() // Important other wise all display name or lore will be same
        warpItemCopy.displayName = warpItemCopy.displayName?.replace("{warp}", warp.name)
        warpItemCopy.lore = warpItemCopy.lore.map { it.replace("{warp}", warp.name) }.toMutableList()

        addItem(warpItemCopy) {
            val player = it.whoClicked as? Player ?: return@addItem

            Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                player.performCommand("team:team warp ${warp.name}")
            }, 2)
            GUIManager.closeInventory(player)
        }
    }
    val backItem = ConfigManager.mainConfig.loadItem("back-item")
    val backItemSlot = ConfigManager.teamWarpConfig.loadItem("main")?.slot ?: 49
    setItem(backItem, backItemSlot) {}

}

