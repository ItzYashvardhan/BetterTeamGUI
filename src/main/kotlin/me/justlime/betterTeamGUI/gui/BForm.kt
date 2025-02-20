package me.justlime.betterTeamGUI.gui

import com.booksaw.betterTeams.PlayerRank
import com.booksaw.betterTeams.Team
import com.booksaw.betterTeams.TeamPlayer
import me.justlime.betterTeamGUI.config.Config
import me.justlime.betterTeamGUI.config.Service
import me.justlime.betterTeamGUI.pluginInstance
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.geysermc.cumulus.form.CustomForm
import org.geysermc.cumulus.form.ModalForm
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.form.SimpleForm.Builder
import org.geysermc.cumulus.util.FormImage
import org.geysermc.floodgate.api.FloodgateApi

object BForm {

    private fun avatarUrl(player: OfflinePlayer?): String {
        var url = Config.avatarUrl.replace("{playername}", player?.name.toString()).replace("{uuid}", player?.uniqueId.toString())
        url = if (player != null) Service.applyPlaceHolder(url, player) else url
        return url
    }

    private val api = FloodgateApi.getInstance()

    fun openTeamCreateForm(player: Player) {
        // Get values from your configuration.
        val title = Config.TeamCreateForm.title
        val label = Config.TeamCreateForm.label      // e.g. "Enter Team Name"
        val labelPlaceholder = Config.TeamCreateForm.placeholder

        // Build the custom form.
        val form = CustomForm.builder().title(Service.applyColors(title)).input(Service.applyColors(label), labelPlaceholder)

        // Handle the response.
        form.validResultHandler { response ->
            val teamName = response.next<String>() ?: ""
            if (teamName.trim().isBlank()) {
                player.sendMessage("Team name cannot be empty!")
                // Reopen the form so the player can try again.
                openTeamListForm(player)
            } else {
                // Execute the command with the provided team name.
                player.performCommand("team create $teamName")
                Bukkit.getScheduler().runTaskLater(pluginInstance, Runnable {
                    GUIManager.openTeamGUI(player)
                }, 4)
            }
        }.build()
        api.getPlayer(player.uniqueId).sendForm(form)
    }

    fun openTeamLeaveForm(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val title = Config.TeamLeaveForm.title
        val text = Config.TeamLeaveForm.text
        val confirm = Config.TeamLeaveForm.confirm.getStringList("lines").joinToString("\n") { Service.applyLocalPlaceHolder(it, team, teamPlayer) }
        val cancel = Config.TeamLeaveForm.cancel.getStringList("lines").joinToString("\n") { Service.applyLocalPlaceHolder(it, team, teamPlayer) }
        val form = ModalForm.builder().title(Service.applyLocalPlaceHolder(title, team, teamPlayer)).content(
            text.joinToString("\n") { Service.applyLocalPlaceHolder(it, team, teamPlayer) }).button1(confirm).button2(cancel)
        form.validResultHandler { response ->
            if (response.clickedButtonId() == 0) {
                player.performCommand("team leave")
            } else {
                openTeamForm(player)
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)
    }

    fun openTeamListForm(player: Player) {
        val title = Config.TeamListForm.title
        val text = Config.TeamListForm.text
        val form = SimpleForm.builder().title(Service.applyColors(title)).content(text.joinToString("\n") { Service.applyColors(it) })
        val teams = Team.getTeamManager().loadedTeamListClone.values.filterNotNull()
        val actions = mutableListOf<() -> Unit>()
        teams.forEach { team ->
            val owner = team.getRank(PlayerRank.OWNER).first() ?: return@forEach
            val teamPlayer = team.getTeamPlayer(player) ?: TeamPlayer(player, PlayerRank.DEFAULT)
            createButton(form, Config.TeamListForm.teams, team, teamPlayer, owner.player) {
                openTeamOtherForm(player, team)
            }?.let { actions.add(it) }
        }
        val isInTeam = Team.getTeamManager().isInTeam(player)
        if (isInTeam) {
            createBackButton(form) {
                GUIManager.openTeamGUI(player)
            }.let { actions.add(it) }
        } else {
            createButton(form, Config.TeamListForm.create) {
                openTeamCreateForm(player)
            }?.let { actions.add(it) }
        }

        form.validResultHandler { response ->
            val buttonIndex = response.clickedButtonId()
            actions.getOrNull(buttonIndex)?.invoke()
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
        val actions = mutableListOf<() -> Unit>()

        createButton(form, Config.TeamSelfForm.chat, team, teamPlayer, player) {
            player.performCommand("team:team chat")
        }?.let { actions.add(it) }

        createButton(form, Config.TeamSelfForm.home, team, teamPlayer) {
            player.performCommand("team:team home")
        }?.let { actions.add(it) }

        if (teamPlayer.rank != PlayerRank.DEFAULT)
        createButton(form, Config.TeamSelfForm.balance, team, teamPlayer) {
            openTeamBalanceForm(team, teamPlayer)
        }?.let { actions.add(it) }

        createButton(form, Config.TeamSelfForm.warp, team, teamPlayer) {
            openTeamWarpForm(team, teamPlayer)
        }?.let { actions.add(it) }

        createButton(form, Config.TeamSelfForm.members, team, teamPlayer) {
            openTeamMemberForm(player, team)
        }?.let { actions.add(it) }

        createButton(form, Config.TeamSelfForm.enderchest, team, teamPlayer) {
            player.performCommand("team:team echest")
        }?.let { actions.add(it) }

        if (teamPlayer.rank != PlayerRank.DEFAULT)
        createButton(form, Config.TeamSelfForm.pvp, team, teamPlayer) {
            player.performCommand("team:team pvp")
        }?.let { actions.add(it) }

        createButton(form, Config.TeamSelfForm.ally, team, teamPlayer) {
            openTeamAllyForm(player, team)
        }?.let { actions.add(it) }

        if (teamPlayer.rank != PlayerRank.OWNER)
        createButton(form, Config.TeamSelfForm.leave, team, teamPlayer) {
            openTeamLeaveForm(player)
        }?.let { actions.add(it) }

        createButton(form, Config.TeamSelfForm.listButton, team, teamPlayer) {
            openTeamListForm(player)
        }?.let { actions.add(it) }

        if (teamPlayer.rank != PlayerRank.DEFAULT)
        createButton(form, Config.TeamSelfForm.settingButton, team, teamPlayer) {
            //Setting
            player.sendMessage("&c&lComming Soon!")
        }?.let { actions.add(it) }

        form.validResultHandler { response ->
            val buttonIndex = response.clickedButtonId()
            actions.getOrNull(buttonIndex)?.invoke()
        }
        api.getPlayer(player.uniqueId).sendForm(form)

    }

    fun openTeamOtherForm(player: Player, teamToView: Team) {
        val title = Config.otherTeamForm.getString("main.title", "Team Other") ?: ""
        val text = Config.otherTeamForm.getStringList("main.text")
        val teamPlayer = teamToView.getTeamPlayer(player) ?: TeamPlayer(player, PlayerRank.DEFAULT)
        val form = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, teamToView, teamPlayer)).content(
            text.joinToString("\n") { Service.applyLocalPlaceHolder(it, teamToView, teamPlayer) })
        val actions = mutableListOf<() -> Unit>()

        createButton(form, Config.TeamOtherForm.ally, teamToView, teamPlayer) {
            openTeamAllyForm(player, teamToView)
        }?.let { actions.add(it) }
        createButton(form, Config.TeamOtherForm.member, teamToView, teamPlayer) {
            openTeamMemberForm(player, teamToView)
        }?.let { actions.add(it) }
        if (!Team.getTeamManager().isInTeam(player))
        createButton(form,Config.TeamOtherForm.join, teamToView, teamPlayer){
            player.performCommand("team join ${teamToView.name}")
        }?.let { actions.add(it) }
        createBackButton(form) {
            openTeamForm(player)
        }.let { actions.add(it) }

        form.validResultHandler { response ->
            val buttonIndex = response.clickedButtonId()
            actions.getOrNull(buttonIndex)?.invoke()
        }

        api.getPlayer(player.uniqueId).sendForm(form)

    }

    /**
     * @param player Form will be sent to this player
     * @param team The team whose ally to view
     */
    fun openTeamAllyForm(player: Player, team: Team) {
        val title = Config.TeamAllyForm.title
        val text = Config.TeamAllyForm.text
        val teamAlly = team.allies.get().toMutableList()
        val form = SimpleForm.builder()
            .title(Service.applyLocalPlaceHolder(title, team, team.getTeamPlayer(player) ?: TeamPlayer(player, PlayerRank.DEFAULT))).content(
                text.joinToString("\n") {
                    Service.applyLocalPlaceHolder(
                        it, team, team.getTeamPlayer(player) ?: TeamPlayer(player, PlayerRank.DEFAULT)
                    )
                })
        val actions = mutableListOf<() -> Unit>()
        for (teamUUid in teamAlly) {
            val allyTeam = Team.getTeam(teamUUid) ?: continue
            val teamPlayer = team.getTeamPlayer(player) ?: TeamPlayer(player, PlayerRank.DEFAULT)
            val owner = allyTeam.getRank(PlayerRank.OWNER).first() ?: continue
            createButton(form, Config.TeamAllyForm.allies, allyTeam, teamPlayer, owner.player) {
                openTeamOtherForm(player, allyTeam)
            }?.let { actions.add(it) }
        }
        createBackButton(form) {
            openTeamForm(player)
        }.let { actions.add(it) }
        form.validResultHandler { response ->
            actions.getOrNull(response.clickedButtonId())?.invoke()
        }
        api.getPlayer(player.uniqueId).sendForm(form)
    }

    fun openTeamMemberForm(player: Player, team: Team) {
        val teamMembers = team.members.get().sortedBy { it.rank }.filterNotNull()
        val form = SimpleForm.builder().title(Config.TeamMemberForm.title).content(Config.TeamMemberForm.text.joinToString("\n"))
        val actions = mutableListOf<() -> Unit>()
        val currentTeamPlayer = team.getTeamPlayer(player)
        val isPlayerInSameTeam = currentTeamPlayer != null

        for (member in teamMembers) {
            when (member.rank) {
                PlayerRank.OWNER -> {
                    createButton(form, Config.TeamMemberForm.owner, team, member, member.player) {
                        if (isPlayerInSameTeam && currentTeamPlayer?.rank == PlayerRank.OWNER && currentTeamPlayer == member) {
                            openTeamMemberManagementForm(player, member, team)
                        } else openTeamMemberForm(player, team)
                    }?.let { actions.add(it) }
                }

                PlayerRank.ADMIN -> {
                    createButton(form, Config.TeamMemberForm.admin, team, member, member.player) {
                        if (isPlayerInSameTeam && currentTeamPlayer?.rank == PlayerRank.OWNER) {
                            openTeamMemberManagementForm(player, member, team)
                        } else {
                            openTeamMemberForm(player, team)
                        }
                    }?.let { actions.add(it) }
                }

                PlayerRank.DEFAULT -> {
                    // OWNER or ADMIN can manage a member.
                    createButton(form, Config.TeamMemberForm.member, team, member, member.player) {
                        if (isPlayerInSameTeam && (currentTeamPlayer?.rank == PlayerRank.OWNER || currentTeamPlayer?.rank == PlayerRank.ADMIN)) {
                            openTeamMemberManagementForm(player, member, team)
                        } else {
                            openTeamMemberForm(player, team)
                        }
                    }?.let { actions.add(it) }
                }
            }
        }
        if (isPlayerInSameTeam && (currentTeamPlayer?.rank == PlayerRank.OWNER || currentTeamPlayer?.rank == PlayerRank.ADMIN)) {
            createButton(form, Config.TeamMemberForm.invite, team, currentTeamPlayer) {
                openInvitePlayerListForm(player)
            }?.let { actions.add(it) }
        }
        createBackButton(form) {
            openTeamForm(player)
        }.let { actions.add(it) }
        form.validResultHandler { response ->
            actions.getOrNull(response.clickedButtonId())?.invoke()
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
        val actions = mutableListOf<() -> Unit>()
        val form = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, team, member))
            .content(text.joinToString("\n") { Service.applyLocalPlaceHolder(it, team, member) })

        createButton(form, Config.TeamMemberManagementForm.demote, team, member) {
            openConfirmedModal(Config.TeamMemberManagementForm.demote) {
                player.performCommand("team demote ${member.player.name}")
            }
        }?.let { actions.add(it) }

        createButton(form, Config.TeamMemberManagementForm.promote, team, member) {
            openConfirmedModal(Config.TeamMemberManagementForm.promote) {
                player.performCommand("team promote ${member.player.name}")
            }
        }?.let { actions.add(it) }

        createButton(form, Config.TeamMemberManagementForm.kick, team, member) {
            openConfirmedModal(Config.TeamMemberManagementForm.kick) {
                player.performCommand("team kick ${member.player.name}")
            }
        }?.let { actions.add(it) }

        createButton(form, Config.TeamMemberManagementForm.ban, team, member) {
            openConfirmedModal(Config.TeamMemberManagementForm.ban) {
                player.performCommand("team ban ${member.player.name}")
            }
        }?.let { actions.add(it) }

        createBackButton(form) {
            openTeamForm(player)
        }.let { actions.add(it) }

        form.validResultHandler { response ->
            actions.getOrNull(response.clickedButtonId())?.invoke()
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
            createButton(form, Config.TeamWarpForm.warpBtn, team, teamPlayer, null, { lines ->
                lines.replace("{warp}", warp.name)
            }) {}
        }
        createBackButton(form) {
            openTeamForm(teamPlayer.player as Player)
        }
        form.validResultHandler { response ->

            val index = response.clickedButtonId()
            if (index in warps.indices) {
                Bukkit.getScheduler().runTaskLater((pluginInstance), Runnable {
                    (teamPlayer.player as Player).teleport(warps[index].location)
                }, 4)
            }

            if (response.clickedButtonId() == warps.size) {
                openTeamForm(teamPlayer.player as Player)
            }
        }

        api.getPlayer(teamPlayer.player.uniqueId).sendForm(form)

    }

    fun openTeamBalanceForm(team: Team, teamPlayer: TeamPlayer) {
        val title = Config.TeamBalanceForm.title
        val text = Config.TeamBalanceForm.text
        val actions = mutableListOf<() -> Unit>()
        val player = teamPlayer.player as Player
        val transactionForm = SimpleForm.builder().title(Service.applyLocalPlaceHolder(title, team, teamPlayer)).content(text.joinToString("\n") {
            Service.applyLocalPlaceHolder(it, team, teamPlayer)
        })
        createButton(transactionForm, Config.TeamBalanceForm.withdraw, team, teamPlayer) {
            openTeamWithdrawForm(team, teamPlayer)
        }?.let { actions.add(it) }
        createButton(transactionForm, Config.TeamBalanceForm.deposit, team, teamPlayer) {
            openTeamDepositForm(team, teamPlayer)
        }?.let { actions.add(it) }
        createBackButton(transactionForm) {
            openTeamForm(player)
        }.let { actions.add(it) }
        transactionForm.validResultHandler { response ->
            actions.getOrNull(response.clickedButtonId())?.invoke()
        }
        api.getPlayer(player.uniqueId).sendForm(transactionForm)
    }

    fun openTeamWithdrawForm(team: Team, teamPlayer: TeamPlayer) {
        val title = Config.TeamBalanceForm.withdraw.getString("title") ?: ""
        val player = teamPlayer.player as Player
        val label = Config.TeamBalanceForm.label
        val placeholder = Config.TeamBalanceForm.placeholder
        val withdraw = Config.TeamBalanceForm.withdraw
        val form = CustomForm.builder().title(Service.applyLocalPlaceHolder(title, team, teamPlayer)).input(label, placeholder)
        form.validResultHandler { response ->
            val price = response.next<String>()?.toIntOrNull()
            if (price != null) {
                player.performCommand("team withdraw $price")
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)
    }

    fun openTeamDepositForm(team: Team, teamPlayer: TeamPlayer) {
        val title = Config.TeamBalanceForm.deposit.getString("title") ?: ""
        val player = teamPlayer.player as Player
        val label = Config.TeamBalanceForm.label
        val placeholder = Config.TeamBalanceForm.placeholder
        val deposit = Config.TeamBalanceForm.deposit
        val form = CustomForm.builder().title(Service.applyLocalPlaceHolder(title, team, teamPlayer)).input(label, placeholder)
        form.validResultHandler { response ->
            val price = response.next<String>()?.toIntOrNull()
            if (price != null) {
                player.performCommand("team deposit $price")
            }
        }
        api.getPlayer(player.uniqueId).sendForm(form)

    }

    //Open the list of Player who are not in Team to Inveite
    fun openInvitePlayerListForm(player: Player) {
        val team = Team.getTeam(player.name) ?: return
        val teamPlayer = team.getTeamPlayer(player) ?: return
        val onlinePlayers = Bukkit.getOnlinePlayers().sortedBy { it.name }.filter { !Team.getTeamManager().isInTeam(it) }
        val tile = Service.applyLocalPlaceHolder(Config.TeamInviteForm.title, team, teamPlayer)
        val text = Service.applyLocalPlaceHolder(Config.TeamInviteForm.text.joinToString("\n"), team, teamPlayer)
        val actions = mutableListOf<() -> Unit>()
        val form = SimpleForm.builder().title(tile).content(text)
        onlinePlayers.forEach { activePlayer ->
            val tempPlayer = TeamPlayer(activePlayer, PlayerRank.DEFAULT)
            createButton(form, Config.TeamInviteForm.playerBtn, team, tempPlayer, activePlayer) {
                player.performCommand("team invite ${activePlayer.name}")
            }?.let { actions.add(it) }
        }
        createBackButton(form) {
            openTeamMemberForm(player, team)
        }.let { actions.add(it) }
        form.validResultHandler { response ->
            actions.getOrNull(response.clickedButtonId())?.invoke()
        }
        api.getPlayer(player.uniqueId).sendForm(form)
    }

    /**
     * Adds a button if it is enabled in the configuration.
     * @param form the form builder
     * @param section the configuration section for the button (which should include an "enabled" property and text)
     * @param team the team instance
     * @param teamPlayer the team player instance
     * @param player (optional) the player instance (if needed for placeholder replacement or commands)
     * @param editLines (optional) a lambda to edit the lines before adding to the button
     * @param action the lambda to execute if the button is pressed
     * @return the action lambda if the button was added; null otherwise.
     */
    private fun createButton(
        form: Builder,
        section: ConfigurationSection,
        team: Team,
        teamPlayer: TeamPlayer,
        player: OfflinePlayer? = null,
        editLines: (String) -> String = { it },
        action: () -> Unit
    ): (() -> Unit)? {
        val enabled = section.getBoolean("enabled", true)
        if (!enabled) {
            return null
        }
        val lines = section.getStringList("lines").joinToString("\n §r") { Service.applyLocalPlaceHolder(it, team, teamPlayer) }

        val image = section.getConfigurationSection("image")?.takeIf { it.getBoolean("enabled", false) }

        createImageButton(form, editLines(lines), image, player)
        // Return the corresponding action.
        return action
    }

    private fun createButton(
        form: Builder, section: ConfigurationSection, playerHead: OfflinePlayer? = null, editLines: (String) -> String = { it }, action: () -> Unit
    ): (() -> Unit)? {
        val enabled = section.getBoolean("enabled", true)
        if (!enabled) {
            return null
        }
        val lines = section.getStringList("lines").joinToString("\n §r") { Service.applyColors(it) }
        val image = section.getConfigurationSection("image")?.takeIf { it.getBoolean("enabled", false) }
        createImageButton(form, editLines(lines), image, playerHead)
        return action
    }

    private fun createBackButton(form: Builder, actions: () -> Unit): (() -> Unit) {
        val lines = Config.backButton.getStringList("lines").joinToString("\n §r") { Service.applyColors(it) }
        val image = Config.backButton.getConfigurationSection("image")
        createImageButton(form, lines, image, null)
        return actions
    }

    private fun createImageButton(form: Builder, lines: String, image: ConfigurationSection?, player: OfflinePlayer?) {
        val imageEnabled = image?.getBoolean("enabled", false) ?: false
        if (imageEnabled) {
            var imageUrl = image?.getString("url", "") ?: ""
            if (imageUrl.isBlank()) imageUrl = avatarUrl(player)
            form.button(lines, FormImage.Type.URL, imageUrl)
        } else form.button(lines)
    }

}