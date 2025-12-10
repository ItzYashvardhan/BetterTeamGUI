package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamMoneyItem {
    val config = ConfigHandler(JFiles.MONEY_VIEW.filename)

    var depositTitle = ConfigManager.moneyView.getString(JGui.MoneyView.DEPOSIT_TITLE)
    var depositLabel = ConfigManager.moneyView.getString(JGui.MoneyView.DEPOSIT_LABEL)
    var depositInputItem = config.loadItem(JGui.MoneyView.DEPOSIT_INPUT_ITEM)
    var depositOutputItem = config.loadItem(JGui.MoneyView.DEPOSIT_OUTPUT_ITEM)

    var withdrawTitle = ConfigManager.moneyView.getString(JGui.MoneyView.WITHDRAW_TITLE)
    var withdrawLabel = ConfigManager.moneyView.getString(JGui.MoneyView.WITHDRAW_LABEL)
    var withdrawInputItem = config.loadItem(JGui.MoneyView.WITHDRAW_INPUT_ITEM)
    var withdrawOutputItem = config.loadItem(JGui.MoneyView.WITHDRAW_OUTPUT_ITEM)

    fun reload() {
        config.reload()
        depositTitle = ConfigManager.moneyView.getString(JGui.MoneyView.DEPOSIT_TITLE)
        depositLabel = ConfigManager.moneyView.getString(JGui.MoneyView.DEPOSIT_LABEL)
        depositInputItem = config.loadItem(JGui.MoneyView.DEPOSIT_INPUT_ITEM)
        depositOutputItem = config.loadItem(JGui.MoneyView.DEPOSIT_OUTPUT_ITEM)

        withdrawTitle = ConfigManager.moneyView.getString(JGui.MoneyView.WITHDRAW_TITLE)
        withdrawLabel = ConfigManager.moneyView.getString(JGui.MoneyView.WITHDRAW_LABEL)
        withdrawInputItem = config.loadItem(JGui.MoneyView.WITHDRAW_INPUT_ITEM)
        withdrawOutputItem = config.loadItem(JGui.MoneyView.WITHDRAW_OUTPUT_ITEM)
    }
}