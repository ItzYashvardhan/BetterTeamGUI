package me.justlime.betterTeamGUI.gui.items

import me.justlime.betterTeamGUI.config.ConfigManager
import me.justlime.betterTeamGUI.enums.JFiles
import me.justlime.betterTeamGUI.enums.JGui
import net.justlime.limeframegui.impl.ConfigHandler

object TeamMoneyItem {
    val config = ConfigHandler(JFiles.MONEY_VIEW.filename)

    val depositTitle = ConfigManager.moneyView.getString(JGui.MoneyView.DEPOSIT_TITLE)
    val depositLabel = ConfigManager.moneyView.getString(JGui.MoneyView.DEPOSIT_LABEL)
    val depositInputItem = config.loadItem(JGui.MoneyView.DEPOSIT_INPUT_ITEM)
    val depositOutputItem = config.loadItem(JGui.MoneyView.DEPOSIT_OUTPUT_ITEM)

    val withdrawTitle = ConfigManager.moneyView.getString(JGui.MoneyView.WITHDRAW_TITLE)
    val withdrawLabel = ConfigManager.moneyView.getString(JGui.MoneyView.WITHDRAW_LABEL)
    val withdrawInputItem = config.loadItem(JGui.MoneyView.WITHDRAW_INPUT_ITEM)
    val withdrawOutputItem = config.loadItem(JGui.MoneyView.WITHDRAW_OUTPUT_ITEM)

}