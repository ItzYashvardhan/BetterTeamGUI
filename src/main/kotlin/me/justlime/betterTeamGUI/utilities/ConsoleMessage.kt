/*
 * RedeemCodeX - Plugin License Agreement
 * Copyright © 2024 Yashvardhan
 *
 * This software is a paid plugin developed by Yashvardhan ("Author") and is provided to you ("User") under the following terms:
 *
 * 1. Usage Rights:
 *    - This plugin is licensed, not sold.
 *    - One license grants usage on **one server network only**, unless explicitly agreed otherwise.
 *    - You may not sublicense, share, leak, or resell the plugin or any part of it.
 *
 * 2. Restrictions:
 *    - You may not decompile, reverse engineer, or modify the plugin.
 *    - You may not redistribute the plugin in any form.
 *    - You may not upload this plugin to any public or private repository or distribution platform.
 *
 * 3. Support & Updates:
 *    - Support is provided to verified buyers only.
 *    - Updates are available as long as development continues or within the support duration stated at purchase.
 *
 * 4. Termination:
 *    - Any violation of this agreement terminates your rights to use this plugin immediately, without refund.
 *
 * 5. No Warranty:
 *    - The plugin is provided "as is", without warranty of any kind. Use at your own risk.
 *    - The Author is not responsible for any damages, data loss, or server issues resulting from usage.
 *
 * For inquiries,
 * Email: itsyashvardhan76@gmail.com
 * Discord: https://discord.gg/rVsUJ4keZN
 */

package me.justlime.betterTeamGUI.utilities

import org.bukkit.Bukkit

object ConsoleMessage {

    private val serverVersion = Bukkit.getVersion() // e.g. "git-PaperSpigot-445 (MC: 1.8.8)"
    private val mcVersionRegex = "\\(MC: ([^)]+)\\)".toRegex()
    private val actualVersion = mcVersionRegex.find(serverVersion)?.groupValues?.get(1) ?: "unknown"

    val majorJavaVersion: Int = try {
        val javaVersion = System.getProperty("java.version")
        val parts = javaVersion.split(".")
        if (parts[0] == "1") {
            // Old format for Java 8 and below: "1.8.0" -> 8
            parts[1].toInt()
        } else {
            // New format for Java 9 and above: "11.0.15" -> 11
            parts[0].toInt()
        }
    } catch (_: Exception) {
        // Fallback in case of an unexpected version string format
        8 // Assume legacy if detection fails
    }


    // ANSI Colors

    enum class Color(val code: String) {
        RED("\u001B[31m"), GREEN("\u001B[32m"), YELLOW("\u001B[33m"), BLUE("\u001B[34m"), PURPLE("\u001B[35m"), CYAN("\u001B[36m"), WHITE("\u001B[37m"), ORANGE(
            "\u001B[38;5;208m"
        ),
        BOLD_CYAN("\u001B[1;36m"), LIGHT_BLUE("\u001B[94m"), GRAY("\u001B[90m"), RESET("\u001B[0m"), BLACK("\u001B[30m"), BRIGHT_RED("\u001B[91m"), BRIGHT_GREEN(
            "\u001B[92m"
        ),
        BRIGHT_YELLOW(
            "\u001B[93m"
        ),
        BRIGHT_BLUE("\u001B[94m"), BRIGHT_PURPLE("\u001B[95m"), BRIGHT_CYAN("\u001B[96m"), BRIGHT_WHITE("\u001B[97m"), RESET_BG("\u001B[49m"), RED_BG(
            "\u001B[41m"
        ),
        GREEN_BG("\u001B[42m"), YELLOW_BG("\u001B[43m"), BLUE_BG("\u001B[44m"), PURPLE_BG("\u001B[45m"), CYAN_BG("\u001B[46m"), WHITE_BG("\u001B[47m"), BLACK_BG(
            "\u001B[40m"
        ),
        BRIGHT_RED_BG("\u001B[101m"), BRIGHT_GREEN_BG("\u001B[102m"), BRIGHT_YELLOW_BG("\u001B[103m"), BRIGHT_BLUE_BG("\u001B[104m"), BRIGHT_PURPLE_BG(
            "\u001B[105m"
        ),
        BRIGHT_CYAN_BG("\u001B[106m"), BRIGHT_WHITE_BG("\u001B[107m"), BOLD("\u001B[1m"), ITALIC("\u001B[3m"), UNDERLINE("\u001B[4m"), STRIKETHROUGH("\u001B[9m"), RESET_FONT(
            "\u001B[22m"
        ),
        RESET_ITALIC("\u001B[23m"), RESET_UNDERLINE("\u001B[24m"), RESET_STRIKETHROUGH("\u001B[29m");

        override fun toString(): String = code
    }

    // Modern Header/Footer (no indentation)
    private val modernHeader = """
┌──────────────────────────────────────────────────────────────┐
│${Color.RESET}${Color.BOLD}${Color.ORANGE} BetterTeamGUI ${Color.RESET}${Color.GRAY}- ${Color.CYAN}Redefining Team Interaction${Color.RESET}                  │
├──────────────────────────────────────────────────────────────┤
""".trimIndent()

    private val modernFooter = """
└──────────────────────────────────────────────────────────────┘
${Color.RESET}""".trimIndent()

    // Message line formatter
    private fun stripAnsi(input: String): String {
        return input.replace(Regex("\u001B\\[[;\\d]*m"), "")
    }

    private fun step(message: String, color: Color, customColor: String = ""): String {
        val prefix = if (customColor.isNotBlank()) "${Color.RESET} ${Color.GRAY}$customColor» "
        else "${Color.RESET} ${Color.GRAY}$color» "

        val reset = "${Color.RESET}${Color.GRAY}"
        val prefixLength = stripAnsi(prefix).length
        val wrapWidth = 62 - prefixLength

        val lines = mutableListOf<String>()
        val visibleMessage = stripAnsi(message)

        var index = 0
        while (index < visibleMessage.length) {
            val end = (index + wrapWidth).coerceAtMost(visibleMessage.length)
            val chunk = visibleMessage.substring(index, end)
            lines.add(chunk)
            index = end
        }

        return buildString {
            lines.forEachIndexed { i, line ->
                val padding = " ".repeat(maxOf(0, 62 - if (i == 0) (prefixLength + line.length) else (2 + line.length)))
                val content = if (i == 0) "$prefix$line$reset" else "$reset  $line$reset"
                appendLine("│$content$padding${Color.RESET}│")
            }
        }.trimEnd()
    }

    // Section Methods
    fun printHeader() {
        if (majorJavaVersion < 11) {
            println("+------------------------------------------------------+")
            println("BetterTeamGUI - Redefining Team Interaction")
            println("+------------------------------------------------------+")
        } else println(modernHeader)
    }

    fun printStep(message: String, color: Color = Color.BRIGHT_CYAN, customColor: String = "") {
        if (majorJavaVersion <11) {
            println(message)
        } else println(step(message, color, customColor))
    }

    fun printNext(message: String, color: Color = Color.BRIGHT_CYAN) {
        if (majorJavaVersion <11) {
            println(message)
        } else {
            println("${Color.RESET} ${Color.GRAY}$color» $message ")
        }
    }

    fun printNext(messages: MutableList<String>, color: Color = Color.BRIGHT_CYAN) {
        if (majorJavaVersion <11) {
            messages.forEach { message ->
                println(message)
            }
        } else {
            printHeader()
            messages.forEach { message ->
                println("${Color.RESET} ${Color.GRAY}$color» $message")
            }
            printFooter()
        }
    }

    fun printFooter() {
        if (majorJavaVersion < 11) {
            println("+------------------------------------------------------+")
        } else {
            println(modernFooter)
        }
    }
}

