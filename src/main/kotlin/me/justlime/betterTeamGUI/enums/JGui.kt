package me.justlime.betterTeamGUI.enums

sealed interface JGui {

    sealed interface Main : JGui {

        companion object {
            const val SETTING = "main"
            const val BACKGROUND = "${SETTING}.background"
            const val BACK_SLOT = "${SETTING}.slot.back"
            const val NEXT_SLOT = "${SETTING}.slot.next"
            const val PREV_SLOT = "${SETTING}.slot.prev"
            const val HOME_SLOT = "${SETTING}.slot.home"
            const val TITLE = "${SETTING}.title"
            const val ROW = "${SETTING}.row"
            const val FONT_TITLE = "${SETTING}.font-title"
            const val FONT_NAME = "${SETTING}.font-name"
            const val FONT_LORE = "${SETTING}.font-lore"

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
            const val TEAM_ITEM_WITH_DESC = "team-item-with-description"
            const val TEAM_ITEM_WITHOUT_DESC = "team-item-without-description"

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

            const val CREATE_TEAM_ITEM = "create.item"
            const val CREATE_TEAM_TITLE = "anvil-ui.create-team.title"
            const val CREATE_TEAM_LABEL = "anvil-ui.create-team.label"
            const val CREATE_TEAM_INPUT_ITEM = "anvil-ui.create-team.input-item"
            const val CREATE_TEAM_OUTPUT_ITEM = "anvil-ui.create-team.output-item"
        }
    }

    sealed interface WarpView : JGui {
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
            const val LEAVE_VIEW = "leave-view."
            const val LEAVE_VIEW_MAIN = LEAVE_VIEW + Main.SETTING
            const val LEAVE_VIEW_BACKGROUND = LEAVE_VIEW + Main.BACKGROUND
            const val LEAVE_CONFIRM_ITEM = LEAVE_VIEW + "confirm"
            const val LEAVE_CANCEL_ITEM = LEAVE_VIEW + "cancel"
            const val DISBAND_VIEW = "disband-view."
            const val DISBAND_VIEW_MAIN = DISBAND_VIEW + Main.SETTING
            const val DISBAND_VIEW_BACKGROUND = DISBAND_VIEW + Main.BACKGROUND
            const val DISBAND_CONFIRM_ITEM = DISBAND_VIEW + "confirm"
            const val DISBAND_CANCEL_ITEM = DISBAND_VIEW + "cancel"
            const val DELETE_HOME_VIEW = "delete-home-view."
            const val DELETE_HOME_VIEW_MAIN = DELETE_HOME_VIEW + Main.SETTING
            const val DELETE_HOME_VIEW_BACKGROUND = DELETE_HOME_VIEW + Main.BACKGROUND
            const val DELETE_HOME_CONFIRM_ITEM = DELETE_HOME_VIEW + "confirm"
            const val DELETE_HOME_CANCEL_ITEM = DELETE_HOME_VIEW + "cancel"
            const val UPDATE_HOME_VIEW = "update-home-view."
            const val UPDATE_HOME_VIEW_MAIN = UPDATE_HOME_VIEW + Main.SETTING
            const val UPDATE_HOME_VIEW_BACKGROUND = UPDATE_HOME_VIEW + Main.BACKGROUND
            const val UPDATE_HOME_CONFIRM_ITEM = UPDATE_HOME_VIEW + "confirm"
            const val UPDATE_HOME_CANCEL_ITEM = UPDATE_HOME_VIEW + "cancel"


        }
    }

    sealed interface MemberView : JGui {
        companion object {
            const val SETTING = "main"
            const val BACKGROUND = "${SETTING}.background"
            const val BACK_SLOT = "${SETTING}.slot.back"
            const val HOME_SLOT = "${SETTING}.slot.home"
            const val MEMBER_ITEM = "member-item"
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
            const val TITLE = "title"
            const val PVP = "pvp"
            const val BAN_LIST = "ban-list"
            const val DISBAND = "disband"

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


}