package com.example.domain

import com.example.consts.Bank
import com.example.consts.DepositType
import com.example.consts.ReceiptType
import java.math.BigDecimal

class Deposit(
    val id: Long,
    val depositId: String,
    val paymentId: String,
    val amount: BigDecimal,
    val receiptType: ReceiptType,
    val depositType: DepositType,
    val accountNumber: String,
    val bank: Bank
)
