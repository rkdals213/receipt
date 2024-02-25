package com.example.entity

import com.example.consts.Bank
import com.example.consts.DepositType
import com.example.consts.ReceiptType
import org.springframework.data.annotation.Id
import java.math.BigDecimal

class DepositEntity(
    @Id
    val id: Long = 0L,

    val depositId: String,

    val paymentId: String,

    val amount: BigDecimal,

    val receiptType: ReceiptType,

    val depositType: DepositType,

    val accountNumber: String,

    val bank: Bank
) {
}
