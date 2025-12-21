package me.justlime.betterTeamGUI.enums

import me.justlime.betterTeamGUI.pluginInstance

enum class JFiles(val filename: String) {
    CONFIG("config.yml"),
    SOUND("sound.yml"),
    FONT("font.yml"),

    //Java Config
    MESSAGES("lang/${pluginInstance.lang}/messages.yml"),
    ALLIES_VIEW("lang/${pluginInstance.lang}/gui/allies_view.yml"),
    BAN_VIEW("lang/${pluginInstance.lang}/gui/ban_view.yml"),
    BUTTONS("lang/${pluginInstance.lang}/gui/buttons.yml"),
    COLORS("lang/${pluginInstance.lang}/gui/color_picker.yml"),
    DASHBOARD_VIEW("lang/${pluginInstance.lang}/gui/dashboard_view.yml"),
    DIALOG_VIEW("lang/${pluginInstance.lang}/gui/dialog_view.yml"),
    LEADERBOARD_VIEW("lang/${pluginInstance.lang}/gui/leaderboard_view.yml"),
    LEVELS_VIEW("lang/${pluginInstance.lang}/gui/levels_view.yml"),
    LIST_VIEW("lang/${pluginInstance.lang}/gui/list_view.yml"),
    INVITE_VIEW("lang/${pluginInstance.lang}/gui/invite_view.yml"),
    INVITED_PLAYERS_VIEW("lang/${pluginInstance.lang}/gui/invited_players_view.yml"),
    MEMBER_MANAGEMENT_VIEW("lang/${pluginInstance.lang}/gui/members_management_view.yml"),
    MEMBERS_VIEW("lang/${pluginInstance.lang}/gui/members_view.yml"),
    MONEY_VIEW("lang/${pluginInstance.lang}/gui/money_view.yml"),
    SETTING_VIEW("lang/${pluginInstance.lang}/gui/setting_view.yml"),
    TEAM_VIEWER("lang/${pluginInstance.lang}/gui/team_viewer.yml"),
    WARPS_VIEW("lang/${pluginInstance.lang}/gui/warps_view.yml"),

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