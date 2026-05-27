package me.justlime.betterTeamGUI.models.enums

import me.justlime.betterTeamGUI.pluginInstance

enum class JFiles(val filename: String) {
    CONFIG("config.yml"),
    SOUND("sound.yml"),
    FONT("gui/component/font.yml"),
    //Java Config
    MESSAGES("lang/${pluginInstance.lang}/messages.yml"),
    ALLIES_VIEW("gui/pages/pager/allies_list.yml"),
    BAN_VIEW("gui/pages/pager/ban_view.yml"),
    BUTTONS("gui/pages/buttons.yml"),
    COLORS("gui/pages/color_picker.yml"),
    DASHBOARD_VIEW("gui/pages/dashboard_view.yml"),
    DIALOG_VIEW("gui/pages/dialog_view.yml"),
    LEADERBOARD_VIEW("gui/pages/pager/leaderboard_view.yml"),
    LEVELS_VIEW("gui/pages/pager/levels_view.yml"),
    LIST_VIEW("gui/pages/pager/team_list.yml"),
    INVITE_VIEW("gui/pages/pager/invite_view.yml"),
    INVITED_PLAYERS_VIEW("gui/pages/invited_players_view.yml"),
    MEMBER_MANAGEMENT_VIEW("gui/pages/members_management_view.yml"),
    MEMBERS_VIEW("gui/pages/pager/members_view.yml"),
    MONEY_VIEW("gui/inputs/withdraw-money.yml"),
    SETTING_VIEW("gui/pages/setting_view.yml"),
    TEAM_VIEWER("gui/pages/view/team.yml"),
    WARPS_VIEW("gui/pages/pager/warps_view.yml"),

    //Bedrock Config
    TEAM_CREATE_FORM("lang/${pluginInstance.lang}/form/create_form.yml"),
    TEAM_FORM("lang/${pluginInstance.lang}/form/team_form.yml"),
    OTHER_TEAM_FORM("lang/${pluginInstance.lang}/form/other_team_form.yml"),
    TEAM_MEMBER("lang/${pluginInstance.lang}/form/members_form.yml"),
    TEAM_MEMBER_MANAGEMENT_FORM("lang/${pluginInstance.lang}/form/member_management_form.yml"),
    INVITE_FORM("lang/${pluginInstance.lang}/form/invite_form.yml"),
    LIST_FORM("lang/${pluginInstance.lang}/form/list_form.yml"),
    ALLY_FORM("lang/${pluginInstance.lang}/form/ally_form.yml"),
    BALANCE_FORM("lang/${pluginInstance.lang}/form/balance_form.yml"),
    LEAVE_FORM("lang/${pluginInstance.lang}/form/leave_form.yml"),
    WARP_FORM("lang/${pluginInstance.lang}/form/warps_form.yml"),

}