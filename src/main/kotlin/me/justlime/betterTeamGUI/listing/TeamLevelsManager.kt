package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.team.level.LevelManager
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry

object TeamLevelsManager : IPopulator {

    override fun registerPopulators() {
        // FIX 1: Matched the exact string from the YAML ("levels_list")
        ListPopulatorRegistry.register("levels_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            val team = Team.getTeam(player) ?: return@register emptyList()
            val levels = LevelManager.getLevels().values.sortedBy { it.level }

            // Fetch all templates (including the missing progress-unlockable!)
            val unlockedTemplate = templatesMap["unlocked"] ?: return@register emptyList()
            val currentTemplate = templatesMap["current"] ?: return@register emptyList()
            val progressTemplate = templatesMap["progress"] ?: return@register emptyList()
            val progressUnlockableTemplate = templatesMap["progress-unlockable"] ?: return@register emptyList()
            val lockedTemplate = templatesMap["locked"] ?: return@register emptyList()

            levels.map { level ->
                val requiredAmount = level.costValue
                val requiredType = when {
                    level.isMoneyCost -> "Money"
                    level.isScoreCost -> "Score"
                    else -> "None"
                }

                val placeholders = mapOf(
                    "level" to level.level.toString(),
                    "team_limit" to level.teamLimit.toString(),
                    "warp_limit" to level.maxWarps.toString(),
                    "max_bal" to level.maxBalance.toString(),
                    "max_chests" to level.maxChests.toString(),
                    "max_admins" to level.maxAdmins.toString(),
                    "max_owners" to level.maxOwners.toString(),
                    "prev_level" to (level.level - 1).toString(),
                    "required_amount" to requiredAmount.toString(),
                    "required_type" to requiredType
                )

                val requirementsMet = when {
                    level.isMoneyCost -> team.money >= requiredAmount
                    level.isScoreCost -> team.score >= requiredAmount
                    else -> true
                }

                // FIX 2: Corrected the mathematical logic for MMO-style level states!
                val template = when {
                    level.level < team.level -> unlockedTemplate            // Past levels
                    level.level == team.level -> currentTemplate            // Exact current level
                    level.level == team.level + 1 -> {                      // Next level
                        if (requirementsMet) progressUnlockableTemplate else progressTemplate
                    }
                    else -> lockedTemplate                                  // Future levels (Level + 2 and beyond)
                }

                template.clone().apply {
                    this.style.placeholder.putAll(placeholders)
                    this.style.viewer = null // Optional context reset
                }
            }
        }
    }
}