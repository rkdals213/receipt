package com.example.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
class ReceiptTargetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
