package com.example.entity

import com.example.consts.ReceiptType
import org.springframework.data.annotation.Id
import java.math.BigDecimal

class ReceiptEntity(
    @Id
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

    val receiptType: ReceiptType,

    val targetId: String
) {
}
