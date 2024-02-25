package com.example.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal

class ReceiptTargetEntity(
    @Id
    val id: Long = 0L,

    val billSequenceId: String,

    val contractId: String,

    val paymentId: String,

    val customerId: String,

    val serviceCode: String,

    val amount: BigDecimal,

    var receiptAmount: BigDecimal,
) {
    override fun toString(): String {
        return "ReceiptTargetEntity(id=$id, billSequenceId='$billSequenceId', contractId='$contractId', paymentId='$paymentId', customerId='$customerId', serviceCode='$serviceCode', amount=$amount, receiptAmount=$receiptAmount)"
    }
}
