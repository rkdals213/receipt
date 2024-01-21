package com.example.entity

import com.example.consts.Bank
import com.example.consts.DepositType
import com.example.consts.ReceiptType
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class DepositEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val depositId: String,

    val paymentId: String,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val receiptType: ReceiptType,

    @Enumerated(EnumType.STRING)
    val depositType: DepositType,

    val accountNumber: String,

    @Enumerated(EnumType.STRING)
    val bank: Bank
) {
}
