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
    sealed interface TeamButton: JGui{
        companion object {
            const val BACK = "back"
            const val HOME = "home"
            const val NEXT = "next"
            const val PREV = "prev"
        }

    }

    sealed interface TeamView : JGui {
        companion object {
            const val INFO = "info"
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

    sealed interface MoneyView : JGui{
        companion object{
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
            const val TEAM_ITEM = "team-item"
            const val SEARCH_ITEM = "search-item"
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
            const val SEARCH_TITLE = "anvil-ui.search-team.title"
            const val SEARCH_LABEL = "anvil-ui.search-team.label"
            const val SEARCH_INPUT_ITEM = "anvil-ui.search-team.input-item"
            const val SEARCH_OUTPUT_ITEM = "anvil-ui.search-team.output-item"
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

    sealed interface LeaveView : JGui{
        companion object{
            const val CONFIRM_ITEM = "confirm"
            const val CANCEL_ITEM = "cancel"
        }
    }

    sealed interface MemberView : JGui{
        companion object{
            const val SETTING = "main"
            const val BACKGROUND = "${SETTING}.background"
            const val BACK_SLOT = "${SETTING}.slot.back"
            const val HOME_SLOT = "${SETTING}.slot.home"
            const val MEMBER_ITEM = "member-item"
        }
    }

}