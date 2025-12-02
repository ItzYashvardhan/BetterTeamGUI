package me.justlime.betterTeamGUI.enums

sealed interface JGui {

    sealed interface Main : JGui {

        companion object {
            const val SETTING = "main"
            const val BACKGROUND = "${SETTING}.background"
        }

    }

    sealed interface Pages: JGui{
        companion object {
            const val TEAM_VIEW = "team-view"
            const val TEAM_VIEW_BACKGROUND = "team-view.background"
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
            const val WARP = "warp"
        }
    }

}