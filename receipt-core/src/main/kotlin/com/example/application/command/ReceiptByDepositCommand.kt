package com.example.application.command

import com.example.consts.Bank
import com.example.consts.DepositType
import java.math.BigDecimal

data class ReceiptByDepositCommand(
    val paymentId: String,
    val billSequenceId: String,
    val depositAmount: BigDecimal,
    val accountNumber: String,
    val depositType: DepositType,
    val bank: Bank
)
