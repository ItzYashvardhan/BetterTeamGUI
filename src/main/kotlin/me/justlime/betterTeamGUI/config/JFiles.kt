package me.justlime.betterTeamGUI.config

enum class JFiles(val filename: String) {
    CONFIG("config.yml"),

    //Java Config
    FONT("gui/font.yml"),
    LIST_VIEW("gui/list_view.yml"),
    TEAM_VIEW("gui/team_view.yml"),
    WARPS_VIEW("warps_view.yml"),
    MEMBER_MANAGEMENT_VIEW("member_management_view.yml"),
    MEMBERS_VIEW("members_view.yml"),
    OTHER_TEAM_VIEW("other_team_view.yml"),
    LEAVE_VIEW("leave_view.yml"),
    BALANCE_VIEW("balance_view.yml"),
    ALLY_VIEW("ally_view.yml"),
    TEAM_LEADERBOARD_VIEW("team_lb.yml"),

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