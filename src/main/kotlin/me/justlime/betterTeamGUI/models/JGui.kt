package me.justlime.betterTeamGUI.models

sealed interface JGui {

    sealed interface Config : JGui {
        companion object {
            const val PREFIX = "command-prefix"
            const val USE_NATIVE_COMMAND = "use-native-command"
            const val LANG = "lang"
        }
    }

    sealed interface Main : JGui {

        companion object {
            const val MAIN = "main"
            const val BACKGROUND = "${MAIN}.background"
            const val BACK_SLOT = "${MAIN}.slot.back"
            const val NEXT_SLOT = "${MAIN}.slot.next"
            const val PREV_SLOT = "${MAIN}.slot.prev"
            const val HOME_SLOT = "${MAIN}.slot.home"
        }

    }

    sealed interface TeamButton : JGui {
        companion object {
            const val BACK = "back"
            const val HOME = "home"
            const val NEXT = "next"
            const val PREV = "prev"

            const val NO_PERMISSION_ITEM = "no-permission"
        }

    }

    sealed interface TeamView : JGui {
        companion object {
            const val INFO_WITH_DESC = "info-with-description"
            const val INFO_WITHOUT_DESC = "info-without-description"
            const val HOME = "home"
            const val BALANCE = "balance"
            const val WARP = "warps"
            const val MEMBERS_GLOBAL = "members.global"
            const val MEMBERS_ADMIN = "members.admin"
            const val TEAM_CHEST = "team-chest"
            const val ALLY = "allies"
            const val LEAVE = "leave"
            const val LIST = "list"
            const val SETTING = "team-setting"
            const val GLOBAL_CHAT = "chat.global"
            const val TEAM_CHAT = "chat.team"
            const val ALLY_CHAT = "chat.ally"

        }
    }

    sealed interface MoneyView : JGui {
        companion object {
            const val DEPOSIT_TITLE = "anvil-ui.deposit.title"
            const val DEPOSIT_LABEL = "anvil-ui.deposit.label"
            const val DEPOSIT_INPUT_ITEM = "anvil-ui.deposit.input-item"
            const val DEPOSIT_OUTPUT_ITEM = "anvil-ui.deposit.output-item"

            const val WITHDRAW_TITLE = "anvil-ui.withdraw.title"
            const val WITHDRAW_LABEL = "anvil-ui.withdraw.label"
            const val WITHDRAW_INPUT_ITEM = "anvil-ui.withdraw.input-item"
            const val WITHDRAW_OUTPUT_ITEM = "anvil-ui.withdraw.output-item"
        }
    }

    sealed interface ListView : JGui {
        companion object {
            const val TEAM_ITEM_WITH_DESC = "team-item-with-description-in-team"
            const val TEAM_ITEM_WITHOUT_DESC = "team-item-without-description-in-team"
            const val TEAM_ITEM_WITH_DESC_NO_TEAM = "team-item-with-description-no-team"
            const val TEAM_ITEM_WITHOUT_DESC_NO_TEAM = "team-item-without-description-no-team"

            const val SORT_ORDER_ASC = "sort.order.ASC"
            const val SORT_ORDER_DESC = "sort.order.DESC"
            const val SORT_TYPE_MONEY = "sort.type.MONEY"
            const val SORT_TYPE_SCORE = "sort.type.SCORE"
            const val SORT_TYPE_LEVEL = "sort.type.LEVEL"
            const val SORT_TYPE_MEMBERS = "sort.type.MEMBERS"

            const val FILTER_DEFAULT = "filter.DEFAULT"
            const val FILTER_OPEN_ONLY = "filter.OPEN_ONLY"
            const val FILTER_CURRENTLY_ONLINE = "filter.CURRENTLY_ONLINE"
            const val FILTER_NOT_FULL = "filter.NOT_FULL"

            const val SEARCH_ITEM = "search-item"
            const val SEARCH_TITLE = "anvil-ui.search-team.title"
            const val SEARCH_LABEL = "anvil-ui.search-team.label"
            const val SEARCH_INPUT_ITEM = "anvil-ui.search-team.input-item"
            const val SEARCH_OUTPUT_ITEM = "anvil-ui.search-team.output-item"

            const val INVITATION_ITEM = "invitation"
            const val NO_INVITATION_ITEM = "no-invitation"
            const val INVITATION_TEAM_ITEM = "invitation-team-item"

            const val CREATE_TEAM_ITEM = "create.item"
            const val CREATE_TEAM_TITLE = "anvil-ui.create-team.title"
            const val CREATE_TEAM_LABEL = "anvil-ui.create-team.label"
            const val CREATE_TEAM_INPUT_ITEM = "anvil-ui.create-team.input-item"
            const val CREATE_TEAM_OUTPUT_ITEM = "anvil-ui.create-team.output-item"
        }
    }

    sealed interface WarpsView : JGui {
        companion object {
            const val OCCUPIED_WARP = "occupied-warp"
            const val CLAIMABLE_WARP = "claimable-warp"
            const val NO_PERMISSION = "no-permission"
            const val LOCKED_WARP = "locked-warp"

            const val SET_WARP_NAME_TITLE = "anvil-ui.set-warp-name.title"
            const val SET_WARP_NAME_LABEL = "anvil-ui.set-warp-name.label"
            const val SET_WARP_NAME_INPUT_ITEM = "anvil-ui.set-warp-name.input-item"
            const val SET_WARP_NAME_OUTPUT_ITEM = "anvil-ui.set-warp-name.output-item"

            const val ENTER_WARP_PASSWORD_TITLE = "anvil-ui.enter-warp-password.title"
            const val ENTER_WARP_PASSWORD_LABEL = "anvil-ui.enter-warp-password.label"
            const val ENTER_WARP_PASSWORD_INPUT_ITEM = "anvil-ui.enter-warp-password.input-item"
            const val ENTER_WARP_PASSWORD_OUTPUT_ITEM = "anvil-ui.enter-warp-password.output-item"

            const val ENTER_WARP_PASSWORD_TO_TELEPORT_TITLE = "anvil-ui.enter-warp-password-to-teleport.title"
            const val ENTER_WARP_PASSWORD_TO_TELEPORT_LABEL = "anvil-ui.enter-warp-password-to-teleport.label"
            const val ENTER_WARP_PASSWORD_TO_TELEPORT_INPUT_ITEM = "anvil-ui.enter-warp-password-to-teleport.input-item"
            const val ENTER_WARP_PASSWORD_TO_TELEPORT_OUTPUT_ITEM = "anvil-ui.enter-warp-password-to-teleport.output-item"

        }
    }

    sealed interface DialogView : JGui {
        companion object {
            const val LEAVE_MAIN = "leave-view.main"
            const val LEAVE_CONFIRM = "leave-view.confirm"
            const val LEAVE_CANCEL = "leave-view.cancel"

            const val DISBAND_MAIN = "disband-view.main"
            const val DISBAND_CONFIRM = "disband-view.confirm"
            const val DISBAND_CANCEL = "disband-view.cancel"

            const val DELETE_WARP_MAIN = "delete-warp-view.main"
            const val DELETE_WARP_CONFIRM = "delete-warp-view.confirm"
            const val DELETE_WARP_CANCEL = "delete-warp-view.cancel"

            const val DELETE_HOME_MAIN = "delete-home-view.main"
            const val DELETE_HOME_CONFIRM = "delete-home-view.confirm"
            const val DELETE_HOME_CANCEL = "delete-home-view.cancel"

            const val UPDATE_HOME_MAIN = "update-home-view.main"
            const val UPDATE_HOME_CONFIRM = "update-home-view.confirm"
            const val UPDATE_HOME_CANCEL = "update-home-view.cancel"

            const val PROMOTE_TO_OWNER_MAIN = "promote-to-owner-view.main"
            const val PROMOTE_TO_OWNER_CONFIRM = "promote-to-owner-view.confirm"
            const val PROMOTE_TO_OWNER_CANCEL = "promote-to-owner-view.cancel"

            const val PROMOTE_TO_ADMIN_MAIN = "promote-to-admin-view.main"
            const val PROMOTE_TO_ADMIN_CONFIRM = "promote-to-admin-view.confirm"
            const val PROMOTE_TO_ADMIN_CANCEL = "promote-to-admin-view.cancel"

            const val DEMOTE_TO_ADMIN_MAIN = "demote-to-admin-view.main"
            const val DEMOTE_TO_ADMIN_CONFIRM = "demote-to-admin-view.confirm"
            const val DEMOTE_TO_ADMIN_CANCEL = "demote-to-admin-view.cancel"

            const val DEMOTE_TO_DEFAULT_MAIN = "demote-to-default-view.main"
            const val DEMOTE_TO_DEFAULT_CONFIRM = "demote-to-default-view.confirm"
            const val DEMOTE_TO_DEFAULT_CANCEL = "demote-to-default-view.cancel"

            const val KICK_MAIN = "kick-view.main"
            const val KICK_CONFIRM = "kick-view.confirm"
            const val KICK_CANCEL = "kick-view.cancel"

            const val BAN_MAIN = "ban-view.main"
            const val BAN_CONFIRM = "ban-view.confirm"
            const val BAN_CANCEL = "ban-view.cancel"

            const val NEUTRAL_MAIN = "neutral-view.main"
            const val NEUTRAL_CONFIRM = "neutral-view.confirm"
            const val NEUTRAL_CANCEL = "neutral-view.cancel"

        }
    }

    sealed interface MembersView : JGui {
        companion object {
            const val INVITE_BUTTON = "invite-button"
            const val MEMBER_ITEM = "member-item"
            const val MEMBER_ITEM_NO_ADMIN = "member-item-no-admin"
            const val LOCKED_INVITE = "locked-invite"
            const val BAN_LIST = "ban-list"


        }
    }

    sealed interface SettingView : JGui {
        companion object {
            const val COLOR_PICKER = "color-picker"
            const val DESCRIPTION = "description"
            const val TAG = "tag"
            const val STATUS_OPEN = "status-open"
            const val STATUS_CLOSED = "status-closed"
            const val ANCHOR = "anchor"
            const val NO_ANCHOR = "no-anchor"
            const val TITLE = "title"
            const val PVP = "pvp"
            const val DISBAND = "disband"
            const val RENAME = "rename"

            const val DESCRIPTION_TITLE = "anvil-ui.description.title"
            const val DESCRIPTION_LABEL = "anvil-ui.description.label"
            const val DESCRIPTION_INPUT_ITEM = "anvil-ui.description.input-item"
            const val DESCRIPTION_OUTPUT_ITEM = "anvil-ui.description.output-item"

            const val TAG_TITLE = "anvil-ui.tag.title"
            const val TAG_LABEL = "anvil-ui.tag.label"
            const val TAG_INPUT_ITEM = "anvil-ui.tag.input-item"
            const val TAG_OUTPUT_ITEM = "anvil-ui.tag.output-item"

            const val TITLE_TITLE = "anvil-ui.title.title"
            const val TITLE_LABEL = "anvil-ui.title.label"
            const val TITLE_INPUT_ITEM = "anvil-ui.title.input-item"
            const val TITLE_OUTPUT_ITEM = "anvil-ui.title.output-item"

            const val RENAME_TITLE = "anvil-ui.rename.title"
            const val RENAME_LABEL = "anvil-ui.rename.label"
            const val RENAME_INPUT_ITEM = "anvil-ui.rename.input-item"
            const val RENAME_OUTPUT_ITEM = "anvil-ui.rename.output-item"

        }
    }

    sealed interface ColorPicker : JGui {
        companion object {

            const val COLORS = "colors"
            const val AQUA = "${COLORS}.aqua"
            const val BLACK = "${COLORS}.black"
            const val BLUE = "${COLORS}.blue"
            const val DARK_AQUA = "${COLORS}.dark_aqua"
            const val DARK_BLUE = "${COLORS}.dark_blue"
            const val DARK_GRAY = "${COLORS}.dark_gray"
            const val DARK_GREEN = "${COLORS}.dark_green"
            const val DARK_PURPLE = "${COLORS}.dark_purple"
            const val DARK_RED = "${COLORS}.dark_red"
            const val GOLD = "${COLORS}.gold"
            const val GRAY = "${COLORS}.gray"
            const val GREEN = "${COLORS}.green"
            const val RED = "${COLORS}.red"
            const val STRIKETHROUGH = "${COLORS}.strikethrough"
            const val WHITE = "${COLORS}.white"
            const val YELLOW = "${COLORS}.yellow"
        }
    }

    sealed interface MemberManagement : JGui {
        companion object {
            const val PLAYER_INFO = "player-info"
            const val PROMOTE_TO_OWNER = "promote-to-owner"
            const val PROMOTE_TO_ADMIN = "promote-to-admin"
            const val DEMOTE_TO_ADMIN = "demote-to-admin"
            const val DEMOTE_TO_DEFAULT = "demote-to-default"
            const val KICK = "kick"
            const val BAN = "ban"
        }
    }

    sealed interface BanList : JGui {
        companion object {
            const val BANNED_PLAYER_ITEM = "banned-player-item"
        }
    }

    sealed interface AlliesView : JGui {
        companion object {
            const val ALLY_ITEM = "ally-item"
            const val ALLY_REQUEST_INBOX = "ally-request-inbox"
            const val ALLY_REQUEST_ITEM = "ally-request-item"
        }
    }

    sealed interface LeaderBoardView : JGui {
        companion object {
            const val TEAM_LEADERBOARD_ITEM = "team-leaderboard-item"
        }
    }

    sealed interface TeamViewer : JGui {
        companion object {
            const val MAIN = "team.main"
            const val BACKGROUND = "team.main.background"
            const val BACK_SLOT = "team.main.slot.back"
            const val HOME_SLOT = "team.main.slot.home"
            const val INFO_WITH_DESC = "team.info-with-description"
            const val INFO_WITHOUT_DESC = "team.info-without-description"
            const val BALANCE = "team.balance"
            const val MEMBERS = "team.members"
            const val ALLIES = "team.allies"

            const val MEMBERS_MAIN = "members.main"
            const val MEMBER_ITEM = "members.member-item"
            const val MEMBERS_BACKGROUND = "members.main.background"
            const val MEMBERS_BACK_SLOT = "members.slot.back"
            const val MEMBERS_HOME_SLOT = "members.slot.home"
            const val MEMBERS_PREV_SLOT = "members.slot.prev"
            const val MEMBERS_NEXT_SLOT = "members.slot.next"

            const val ALLIES_MAIN = "allies.main"
            const val ALLY_ITEM = "allies.ally-item"
            const val ALLIES_BACKGROUND = "allies.main.background"
            const val ALLIES_BACK_SLOT = "allies.slot.back"
            const val ALLIES_HOME_SLOT = "allies.slot.home"
            const val ALLIES_PREV_SLOT = "allies.slot.prev"
            const val ALLIES_NEXT_SLOT = "allies.slot.next"
        }
    }

    sealed interface LevelsView : JGui {
        companion object {
            const val UNLOCKED_LEVEL_ITEM = "level-item.default.unlocked"
            const val CURRENT_LEVEL_ITEM = "level-item.default.current"
            const val PROGRESS_LEVEL_ITEM = "level-item.default.progress"
            const val PROGRESS_UNLOCKABLE_LEVEL_ITEM = "level-item.default.progress-unlockable"
            const val LOCKED_LEVEL_ITEM = "level-item.default.locked"

        }
    }

    sealed interface InviteView : JGui {
        companion object {
            const val INVITE_ITEM = "invite-item"
            const val INVITED_LIST_ITEM = "invited-list-item"
            const val PLAYER_INVITED_ITEM = "player-invited-item"

            const val SEARCH_ITEM = "search-item"
            const val SEARCH_ITEM_CLEAR = "search-item-clear"
            const val SEARCH_TITLE = "anvil-ui.search-item.title"
            const val SEARCH_LABEL = "anvil-ui.search-item.label"
            const val SEARCH_INPUT_ITEM = "anvil-ui.search-item.input-item"
            const val SEARCH_OUTPUT_ITEM = "anvil-ui.search-item.output-item"

            const val INVITE_PLAYER = "invite-player"
            const val INVITE_TITLE = "anvil-ui.invite.title"
            const val INVITE_LABEL = "anvil-ui.invite.label"
            const val INVITE_INPUT_ITEM = "anvil-ui.invite.input-item"
            const val INVITE_OUTPUT_ITEM = "anvil-ui.invite.output-item"
        }
    }

    sealed interface InvitedPlayersView : JGui {
        companion object {
            const val INVITED_PLAYER_ITEM = "invited-player-item"
        }
    }

}