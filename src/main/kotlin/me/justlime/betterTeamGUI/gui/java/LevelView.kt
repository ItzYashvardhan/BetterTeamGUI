package me.justlime.betterTeamGUI.gui.java

import com.booksaw.betterTeams.Main
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.foliaLib
import me.justlime.betterTeamGUI.gui.GUIManager
import me.justlime.betterTeamGUI.gui.items.LevelItem
import me.justlime.betterTeamGUI.utilities.TeamService
import me.justlime.betterTeamGUI.utilities.applyBackground
import net.justlime.limeframegui.models.GUISetting
import net.justlime.limeframegui.type.ChestGUI
import org.bukkit.entity.Player

private enum class RequiredType { MONEY, SCORE }

private data class TeamLevel(val level: Int, val maxWarps: Int, val teamLimit: Int, val maxBal: Int, val maxAdmins: Int, val maxOwners: Int, val maxChests: Int, val requiredAmount: Int, val requiredType: RequiredType)

fun teamLevel(setting: GUISetting, player: Player, team: Team, teamPlayer: TeamPlayer) {
    setting.style.placeholder = TeamService.applyPlaceHolder(team, teamPlayer)
    ChestGUI(setting) {
        onClick { it.isCancelled = true }
        applyBackground(LevelItem, this) {
            GUIManager.openTeamGUI(player)
        }

        //Past levels
        (1..<team.level).forEach { level ->
            val item = if (level == team.level) LevelItem.unlockedLevelItem else LevelItem.unlockedLevelItem
            val finalItem = item?.clone()?.apply {
                style.placeholder = getLevelPlaceHolder(level)
            }
            addItem(finalItem)
        }

        //Current Levels
        val currentLevel = team.level
        val currentLevelData = getLevelData(currentLevel)
        if (currentLevelData != null) {
            addItem(LevelItem.currentLevelItem?.clone()?.apply { style.placeholder = getLevelPlaceHolder(currentLevel) })
        }

        // Display locked levels
        val levelsSection = Main.plugin.config.getConfigurationSection("levels")
        val maxLevel = levelsSection?.getKeys(false)?.maxOfOrNull { it.removePrefix("l").toInt() } ?: 0

        //Display locked levels
        val nextLevel = team.level + 1
        val nextLevelData = getLevelData(nextLevel)

        if (nextLevelData != null && team.level < maxLevel) {
            val canAfford = when (nextLevelData.requiredType) {
                RequiredType.MONEY -> team.money >= nextLevelData.requiredAmount
                RequiredType.SCORE -> team.score >= nextLevelData.requiredAmount
            }

            val itemToDisplay = if (canAfford) LevelItem.progressUnlockableLevelItem else LevelItem.progressLevelItem
            addItem(itemToDisplay?.clone()?.apply {
                style.placeholder = getLevelPlaceHolder(nextLevel)
            }) {
                if (canAfford) {
                    TeamService.promoteTeam(player)
                    foliaLib.scheduler.runLater(Runnable {
                        GUIManager.openTeamLevelGUI(player, team)
                    }, 2)
                }
            }
        }

        // Display restricted levels
        (team.level + 2..maxLevel).forEach { level ->
            val item = LevelItem.lockedLevelItem?.clone()
            addItem(item?.apply { style.placeholder = getLevelPlaceHolder(level) })
        }

    }.open(player)
}

private fun getLevelPlaceHolder(level: Int): Map<String, String> {
    val levelData = getLevelData(level) ?: return emptyMap()
    return mapOf(
        "{level}" to level.toString(),
        "{prev_level}" to (getLevelData(level - 1)?.level ?: 0).toString(),
        "{next_level}" to (getLevelData(level + 1)?.level ?: 0).toString(),
        "{warp_limit}" to levelData.maxWarps.toString(),
        "{prev_warp_limit}" to (getLevelData(level - 1)?.maxWarps ?: 0).toString(),
        "{next_warp_limit}" to (getLevelData(level + 1)?.maxWarps ?: 0).toString(),
        "{team_limit}" to levelData.teamLimit.toString(),
        "{prev_team_limit}" to (getLevelData(level - 1)?.teamLimit ?: 0).toString(),
        "{next_team_limit}" to (getLevelData(level + 1)?.teamLimit ?: 0).toString(),
        "{max_bal}" to levelData.maxBal.toString(),
        "{prev_max_bal}" to (getLevelData(level - 1)?.maxBal ?: 0).toString(),
        "{next_max_bal}" to (getLevelData(level + 1)?.maxBal ?: 0).toString(),
        "{max_admins}" to levelData.maxAdmins.toString(),
        "{prev_max_admins}" to (getLevelData(level - 1)?.maxAdmins ?: 0).toString(),
        "{next_max_admins}" to (getLevelData(level + 1)?.maxAdmins ?: 0).toString(),
        "{max_owners}" to levelData.maxOwners.toString(),
        "{prev_max_owners}" to (getLevelData(level - 1)?.maxOwners ?: 0).toString(),
        "{next_max_owners}" to (getLevelData(level + 1)?.maxOwners ?: 0).toString(),
        "{max_chests}" to levelData.maxChests.toString(),
        "{prev_max_chests}" to (getLevelData(level - 1)?.maxChests ?: 0).toString(),
        "{next_max_chests}" to (getLevelData(level + 1)?.maxChests ?: 0).toString(),
        "{required_amount}" to levelData.requiredAmount.toString(),
        "{required_type}" to when (levelData.requiredType) {
            RequiredType.MONEY -> ConfigManager.messages.getString("money") ?: "Money"
            RequiredType.SCORE -> ConfigManager.messages.getString("score") ?: "Score"
        }
    )
}

private fun getLevelData(level: Int): TeamLevel? {
    val levelsSection = Main.plugin.config.getConfigurationSection("levels.l$level") ?: return null
    val price = levelsSection.getString("price") ?: "0s" //m for money and s for score
    val requiredType = when (price.last()) {
        'm' -> RequiredType.MONEY
        's' -> RequiredType.SCORE
        else -> RequiredType.MONEY
    }
    val requiredAmount = price.dropLast(1).toIntOrNull() ?: 0
    return TeamLevel(
        level = level,
        maxWarps = levelsSection.getInt("maxWarps", -1),
        teamLimit = levelsSection.getInt("teamLimit", -1),
        maxBal = levelsSection.getInt("maxBal", -1),
        maxAdmins = levelsSection.getInt("maxAdmins", -1),
        maxOwners = levelsSection.getInt("maxOwners", -1),
        maxChests = levelsSection.getInt("maxChests", -1),
        requiredAmount = requiredAmount,
        requiredType = requiredType
    )
}

