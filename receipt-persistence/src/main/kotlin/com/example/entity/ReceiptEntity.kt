package com.example.entity

import com.example.consts.ReceiptType
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class ReceiptEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val receiptId: String,

    val billSequenceId: String,

    val contractId: String,

    val paymentId: String,

    val customerId: String,

    val serviceCode: String,

    val amount: BigDecimal,

    val receiptTotalAmount: BigDecimal,

    val receiptAmount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val receiptType: ReceiptType,

    val targetId: String
) {
}
