package me.justlime.betterTeamGUI.models

sealed interface JGui {
    sealed interface Config : JGui {
        companion object {
            const val PREFIX = "command-prefix"
            const val USE_NATIVE_COMMAND = "use-native-command"
            const val LANG = "lang"
        }
    }
}