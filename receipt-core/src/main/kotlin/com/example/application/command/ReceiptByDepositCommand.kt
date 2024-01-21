package com.example.command

import com.example.consts.Bank
import com.example.consts.DepositType
import java.math.BigDecimal
import java.time.YearMonth

data class ReceiptByDepositCommand(
    val billCycle: String,
    val billTargetYearMonth: YearMonth,
    val depositAmount: BigDecimal,
    val accountNumber: String,
    val depositType: DepositType,
    val bank: Bank
)
