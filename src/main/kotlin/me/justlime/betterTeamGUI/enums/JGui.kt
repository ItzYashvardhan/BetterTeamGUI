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

    sealed interface ListView : JGui {
        companion object {
            const val TEAM_ITEM = "team-item"
            const val SORT_ORDER_ASC = "sort.order.asc"
            const val SORT_ORDER_DESC = "sort.order.desc"
            const val SORT_TYPE_MONEY = "sort.type.money"
            const val SORT_TYPE_SCORE = "sort.type.score"
            const val SORT_TYPE_LEVEL = "sort.type.level"
            const val SORT_TYPE_MEMBERS = "sort.type.members"
            const val FILTER_OPEN_ONLY = "filter.open_only"
            const val FILTER_CURRENTLY_ONLINE = "filter.currently_online"
            const val FILTER_NOT_FULL = "filter.not_full"
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

}