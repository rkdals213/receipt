package com.example.domain.command

import com.example.consts.Bank
import com.example.consts.ReceiptType
import com.example.domain.AdvancedPayment
import java.math.BigDecimal

abstract class ReceiptCommand(
    open val receiptType: ReceiptType,
    open val paymentId: String
)

data class ByDeposit(
    override val receiptType: ReceiptType,
    override val paymentId: String,
    val amount: BigDecimal,
    val depositId: String,
    val accountNumber: String,
    val bank: Bank
) : ReceiptCommand(receiptType, paymentId)

data class ByAdvancedPayment(
    override val receiptType: ReceiptType,
    override val paymentId: String,
    val advancedPayment: AdvancedPayment
) : ReceiptCommand(receiptType, paymentId)
