package me.justlime.betterTeamGUI.listing

import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.team.level.LevelManager
import net.justlime.limeframegui.registry.gui.ListPopulatorRegistry

object TeamLevelsManager : IPopulator {

    override fun registerPopulators() {
        ListPopulatorRegistry.register("team_levels_list") { response ->
            val player = response.player
            val templatesMap = response.mask.templates

            val team = Team.getTeam(player) ?: return@register emptyList()
            val levels = LevelManager.getLevels().values.sortedBy { it.level }

            val unlockedTemplate = templatesMap["unlocked-item"] ?: return@register emptyList()
            val currentTemplate = templatesMap["current-item"] ?: return@register emptyList()
            val progressTemplate = templatesMap["progress-item"] ?: return@register emptyList()
            val lockedTemplate = templatesMap["locked-item"] ?: return@register emptyList()

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

                val template = when {
                    team.level >= level.level -> unlockedTemplate
                    team.level + 1 == level.level -> if (requirementsMet) currentTemplate else progressTemplate
                    else -> lockedTemplate
                }

                template.clone().apply {
                    this.style.placeholder.putAll(placeholders)
                    this.style.viewer = null
                }
            }
        }
    }
}