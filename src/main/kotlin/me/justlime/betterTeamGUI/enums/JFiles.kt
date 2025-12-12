package me.justlime.betterTeamGUI.enums

enum class JFiles(val filename: String) {
    CONFIG("config.yml"),
    MESSAGES("messages.yml"),
    BUTTONS("gui/buttons.yml"),
    COLORS("gui/color_picker.yml"),

    //Java Config
    FONT("gui/font.yml"),
    LIST_VIEW("gui/list_view.yml"),
    DASHBOARD_VIEW("gui/dashboard_view.yml"),
    WARPS_VIEW("gui/warps_view.yml"),
    MEMBER_MANAGEMENT_VIEW("gui/members_management_view.yml"),
    MEMBERS_VIEW("gui/members_view.yml"),
    TEAM_VIEWER("gui/team_viewer.yml"),
    DIALOG_VIEW("gui/dialog_view.yml"),
    MONEY_VIEW("gui/money_view.yml"),
    ALLIES_VIEW("gui/allies_view.yml"),
    LEADERBOARD_VIEW("gui/leaderboard_view.yml"),
    SETTING_VIEW("gui/setting_view.yml"),
    BAN_VIEW("gui/ban_view.yml"),
    LEVELS_VIEW("gui/levels_view.yml"),

    //Bedrock Config
    TEAM_CREATE_FORM("form/create_form.yml"),
    TEAM_FORM("form/team_form.yml"),
    OTHER_TEAM_FORM("form/other_team_form.yml"),
    TEAM_MEMBER("form/members_form.yml"),
    TEAM_MEMBER_MANAGEMENT_FORM("form/member_management_form.yml"),
    INVITE_FORM("form/invite_form.yml"),
    LIST_FORM("form/list_form.yml"),
    ALLY_FORM("form/ally_form.yml"),
    BALANCE_FORM("form/balance_form.yml"),
    LEAVE_FORM("form/leave_form.yml"),
    WARP_FORM("form/warps_form.yml"),

}