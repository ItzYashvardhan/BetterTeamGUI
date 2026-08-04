package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.team.level.LevelManager
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry
import net.justlime.limeframegui.models.GuiItem
import org.bukkit.Material

object TeamWarpsManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("warps_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates
            val team = Team.getTeam(player) ?: return@register emptyList()
            val levels = LevelManager.getLevels()
            val teamLevel = team.levelObject

            // Logic limits
            val unlockedWarpsLimit = teamLevel.maxWarps
            val absoluteMaxWarps = levels.values.maxOfOrNull { it.maxWarps } ?: 0

            // Existing data
            val existingWarpStrings = team.warps.convertedList

            // Item Templates
            val occupiedItemTemplate = templatesMap["occupied-warp"] ?: GuiItem(Material.BEDROCK)
            val claimableItemTemplate = templatesMap["claimable-warp"] ?: GuiItem(Material.BEDROCK)
            val lockedItemTemplate = templatesMap["locked-warp"] ?: GuiItem(Material.BEDROCK)

            val generatedItems = mutableListOf<GuiItem>()

            for (index in 0 until absoluteMaxWarps) {
                if (index < unlockedWarpsLimit) {
                    val currentWarpString = existingWarpStrings.getOrNull(index)
                    if (currentWarpString != null) {
                        val currentWarp = team.warps.fromString(currentWarpString)
                        val itemToAdd = occupiedItemTemplate.clone()

                        val loc = currentWarp.location
                        val worldName = loc?.world?.name ?: "Unknown"
                        val x = loc?.blockX?.toString() ?: "0"
                        val y = loc?.blockY?.toString() ?: "0"
                        val z = loc?.blockZ?.toString() ?: "0"

                        val placeholders = mapOf(
                            "warp_name" to currentWarp.name,
                            "warp_world" to worldName,
                            "warp_x" to x,
                            "warp_y" to y,
                            "warp_z" to z,
                            "warp_pos" to "$x, $y, $z",
                            "warp_locked" to if (currentWarp.hasPassword()) "Yes" else "No",
                            "warp_password" to currentWarp.encrypPassword
                        )

                        itemToAdd.style.placeholder.putAll(placeholders)

                        generatedItems.add(itemToAdd)
                    } else {
                        generatedItems.add(claimableItemTemplate.clone())
                    }
                } else {
                    generatedItems.add(lockedItemTemplate.clone())
                }
            }

            return@register generatedItems
        }
    }
}