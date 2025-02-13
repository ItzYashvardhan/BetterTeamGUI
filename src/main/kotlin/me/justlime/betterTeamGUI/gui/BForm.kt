package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.form.SimpleForm.Builder
import org.geysermc.cumulus.util.FormImage
import org.geysermc.floodgate.api.FloodgateApi

object BForm {

    private val avatarUrl = "https://mc-heads.net/avatar/{playername}"
    private val api = FloodgateApi.getInstance()

    fun openTeamListForm(player: Player) {
        val form = SimpleForm.builder().title("Team List").content("Select a team to view details")
        val teams = Team.getTeamManager().sortTeamsByBalance()

        teams.forEach { team ->
            val avatarUrl = avatarUrl.replace("{playername}", player.name).replace("{uuid}", player.uniqueId.toString())
            form.button(team, FormImage.Type.URL, avatarUrl)
        }
        val isInTeam = Team.getTeamManager().isInTeam(player)
        form.validResultHandler { response ->
            val index = response.clickedButtonId()
            if (index in teams.indices) {
                openTeamOtherForm(player, Team.getTeam(teams[index]))
            }
            if (isInTeam) {
                if (response.clickedButtonId() == teams.size) {
                    openTeamForm(player)
                }
            }
        }
        if (Team.getTeamManager().isInTeam(player)) {
            form.button("Back")
        }
        api.getPlayer(player.uniqueId).sendForm(form)

    }

    fun openTeamForm(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val title = Config.TeamSelfForm.title
        val text = Config.TeamSelfForm.text
        val form = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, team, teamPlayer))
            .content(text.joinToString("\n") { Service.applyLocalPlaceHolder(it, team, teamPlayer) })
        createButton(form, Config.TeamSelfForm.chat, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.home, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.balance, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.warp, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.members, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.enderchest, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.pvp, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.ally, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.leave, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.listButton, team, teamPlayer)
        createButton(form, Config.TeamSelfForm.settingButton, team, teamPlayer)
        form.validResultHandler { response ->
            when (response.clickedButtonId()) {
                0 -> {
                    player.performCommand("team:team chat")
                }

                1 -> {
                    player.performCommand("team:team home")
                }

                2 -> {
                    //Balance
                    GUIManager.openTeamBalanceGUI(player)
                }

                3 -> {
                    //Warp
                    openTeamWarpForm(team, teamPlayer)
                }

                4 -> {
                    openTeamMemberForm(player, team)
                }

                5 -> {
                    //Enderchest
                    player.performCommand("team:team echest")
                }

                6 -> {
                    //PvP
                    player.performCommand("team:team pvp")
                }

                7 -> {
                    //Ally
                    openTeamAllyForm(player, team)
                }

                8 -> {
                    //Leave
                    GUIManager.openTeamLeaveGUI(player)
                }

                9 -> {
                    //List
                    openTeamListForm(player)
                }
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)

    }

    fun openTeamOtherForm(player: Player, teamToView: Team) {

        val title = Config.otherTeamForm.getString("main.title", "Team Other") ?: ""
        val text = Config.otherTeamForm.getStringList("main.text")
        val teamPlayer = teamToView.getTeamPlayer(player) ?: TeamPlayer(player, PlayerRank.DEFAULT)
        val form = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, teamToView, teamPlayer)).content(
            text.joinToString("\n") { Service.applyLocalPlaceHolder(it, teamToView, teamPlayer) })
        createButton(form, Config.TeamOtherForm.ally, teamToView, teamPlayer)
        createButton(form, Config.TeamOtherForm.member, teamToView, teamPlayer)
        form.button("Back")
        form.validResultHandler { response ->
            if (response.clickedButtonId() == 0) {
                openTeamAllyForm(player, teamToView)
            }
            if (response.clickedButtonId() == 1) {
                openTeamMemberForm(player, teamToView)
            }
            if (response.clickedButtonId() == 2) {
                openTeamListForm(player)
            }
        }


        api.getPlayer(player.uniqueId).sendForm(form)

    }

    /**
     * @param player Form will be sent to this player
     * @param team The team whose ally to view
     */
    fun openTeamAllyForm(player: Player, team: Team) {
        val teamAlly = team.allies.get()
        val form = SimpleForm.builder().title("Team Allies").content("Select a team to view details")
        for (teamUUid in teamAlly) {
            val allyTeam = Team.getTeam(teamUUid) ?: continue
            form.button(allyTeam.name, FormImage.Type.URL, avatarUrl).validResultHandler { response ->
                openTeamOtherForm(player, allyTeam)
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)
    }

    fun openTeamMemberForm(player: Player, team: Team) {
        val teamMembers = team.members.get().sortedBy { it.rank }
        val form = SimpleForm.builder().title(Config.TeamMemberForm.title).content(Config.TeamMemberForm.text.joinToString("\n"))
        val isPlayerInSameTeam = team.getTeamPlayer(player) != null

        for (member in teamMembers) {
            if (member.rank == PlayerRank.OWNER) {
                val lines =
                    Config.TeamMemberForm.owner.getStringList("lines").joinToString("\n §r") { Service.applyLocalPlaceHolder(it, team, member) }
                form.button(lines, FormImage.Type.URL, avatarUrl.replace("{playername}", member.player.name ?: " "))
            }
            if (member.rank == PlayerRank.ADMIN) {
                val lines =
                    Config.TeamMemberForm.admin.getStringList("lines").joinToString("\n §r") { Service.applyLocalPlaceHolder(it, team, member) }
                form.button(lines, FormImage.Type.URL, avatarUrl.replace("{playername}", member.player.name ?: " "))
            }
            if (member.rank == PlayerRank.DEFAULT) {
                val lines =
                    Config.TeamMemberForm.member.getStringList("lines").joinToString("\n §r") { Service.applyLocalPlaceHolder(it, team, member) }
                form.button(lines, FormImage.Type.URL, avatarUrl.replace("{playername}", member.player.name ?: " "))
            }
        }

        form.button("Back")
        form.validResultHandler { response ->
            val index = response.clickedButtonId()
            if (index in teamMembers.indices) {
                if (isPlayerInSameTeam) {
                    val teamPlayer = team.getTeamPlayer(player) ?: return@validResultHandler
                    val teamMember = teamMembers[index]
                    if (teamMember.rank == PlayerRank.DEFAULT) {
                        if (teamPlayer.rank == PlayerRank.OWNER || teamPlayer.rank == PlayerRank.ADMIN) openTeamMemberManagementForm(
                            player, teamMember, team
                        )
                    }
                    if (teamMember.rank == PlayerRank.ADMIN) {
                        if (teamPlayer.rank == PlayerRank.OWNER) openTeamMemberManagementForm(player, teamMember, team)

                    }
                    if (teamMember.rank == PlayerRank.OWNER) {
                        openTeamMemberForm(player, team)
                    }

                } else openTeamMemberForm(player, team)
            }

            if (response.clickedButtonId() == teamMembers.size) {
                openTeamForm(player)
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)

    }

    fun openTeamMemberManagementForm(player: Player, member: TeamPlayer, team: Team) {

        fun openConfirmedModal(section: ConfigurationSection, action: () -> Unit) {
            val title = Service.applyLocalPlaceHolder(section.getString("title") ?: "Confirmed", team, member)
            val text = Service.applyLocalPlaceHolder(section.getString("text") ?: "Are you sure?", team, member)
            val confirmSection = Config.TeamMemberManagementForm.confirm
            val btn1 = confirmSection.getString("button1") ?: "Yes"
            val btn2 = confirmSection.getString("button2") ?: "No"
            val confirmedForm = SimpleForm.builder().title(title).content(text).button(btn1).button(btn2).validResultHandler { response ->
                if (response.clickedButtonId() == 0) {
                    action()
                    Bukkit.getScheduler().runTaskLater((pluginInstance), Runnable {
                        openTeamMemberForm(player, team)
                    }, 10)
                } else {
                    openTeamMemberManagementForm(player, member, team)
                }
            }

            api.getPlayer(player.uniqueId).sendForm(confirmedForm)
        }

        val title = Config.TeamMemberManagementForm.title
        val text = Config.TeamMemberManagementForm.text
        val form = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, team, member))
            .content(text.joinToString("\n") { Service.applyLocalPlaceHolder(it, team, member) })
        createButton(form, Config.TeamMemberManagementForm.demote, team, member)
        createButton(form, Config.TeamMemberManagementForm.promote, team, member)
        createButton(form, Config.TeamMemberManagementForm.kick, team, member)
        createButton(form, Config.TeamMemberManagementForm.ban, team, member)
        createBackButton(form)
        form.validResultHandler { response ->
            when (response.clickedButtonId()) {
                0 -> {
                    openConfirmedModal(Config.TeamMemberManagementForm.demote) {
                        player.performCommand("team demote ${member.player.name}")
                    }
                }

                1 -> {
                    openConfirmedModal(Config.TeamMemberManagementForm.promote) {
                        player.performCommand("team promote ${member.player.name}")
                    }
                }

                2 -> {
                    openConfirmedModal(Config.TeamMemberManagementForm.kick) {
                        player.performCommand("team kick ${member.player.name}")
                    }
                }

                3 -> {
                    openConfirmedModal(Config.TeamMemberManagementForm.ban) {
                        player.performCommand("team ban ${member.player.name}")
                    }
                }

                4 -> {
                    openTeamMemberForm(player, team)
                }
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)

    }

    fun openTeamWarpForm(team: Team, teamPlayer: TeamPlayer) {
        val title = Config.TeamWarpForm.title
        val text = Config.TeamWarpForm.text
        val warps = team.warps.get().sortedBy { it.name }
        val form = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, team, teamPlayer)).content(text.joinToString("\n") {
            Service.applyLocalPlaceHolder(it, team, teamPlayer)
        })
        warps.forEach { warp ->
            createButton(form, Config.TeamWarpForm.warpBtn, team, teamPlayer){lines ->
                lines.replace("{warp}", warp.name)
            }
        }
        createBackButton(form)
        form.validResultHandler { response ->

            val index = response.clickedButtonId()
            if (index in warps.indices) {
                Bukkit.getScheduler().runTaskLater((pluginInstance), Runnable{
                    (teamPlayer.player as Player).teleport(warps[index].location)
                },4)
            }

            if (response.clickedButtonId() == warps.size) {
                openTeamForm(teamPlayer.player as Player)
            }
        }

        api.getPlayer(teamPlayer.player.uniqueId).sendForm(form)

    }

    private fun createButton(
        form: Builder, section: ConfigurationSection, team: Team, teamPlayer: TeamPlayer, editLines: (String) -> String = { it }
    ) {
        val lines = section.getStringList("lines").joinToString("\n §r") { Service.applyLocalPlaceHolder(it, team, teamPlayer) }

        val image = section.getConfigurationSection("image")?.takeIf { it.getBoolean("enabled", false) }

        createImageButton(form, editLines(lines), image)
    }

    private fun createBackButton(form: Builder) {
        val lines = Config.backButton.getStringList("lines").joinToString("\n §r") { Service.applyColors(it) }
        val image = Config.backButton.getConfigurationSection("image")
        createImageButton(form, lines, image)
    }

    private fun createImageButton(form: Builder, lines: String, image: ConfigurationSection?) {
        val imageEnabled = image?.getBoolean("enabled", false) ?: false
        if (imageEnabled) {
            val imageUrl = image?.getString("url", "") ?: ""
            form.button(lines, FormImage.Type.URL, imageUrl)
            return
        } else form.button(lines)
    }

}