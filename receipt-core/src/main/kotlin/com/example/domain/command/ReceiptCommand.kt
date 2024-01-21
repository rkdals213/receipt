package com.example.command

import com.example.consts.Bank
import com.example.consts.ReceiptType
import com.example.domain.AdvancedPayment
import java.math.BigDecimal

abstract class ReceiptCommand(
    open val receiptType: ReceiptType
)

data class ByDeposit(
    override val receiptType: ReceiptType,
    val amount: BigDecimal,
    val depositId: String,
    val accountNumber: String,
    val bank: Bank
) : ReceiptCommand(receiptType)

data class ByAdvancedPayment(
    override val receiptType: ReceiptType,
    val advancedPayment: AdvancedPayment
) : ReceiptCommand(receiptType)
