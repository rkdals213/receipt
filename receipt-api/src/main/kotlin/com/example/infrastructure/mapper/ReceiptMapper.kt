package com.example.infrastructure.mapper

import com.example.domain.ReceiptTarget
import com.example.entity.ReceiptTargetEntity

fun List<ReceiptTargetEntity>.toDomain() = map {
    ReceiptTarget(
        it.id,
        it.billSequenceId,
        it.contractId,
        it.paymentId,
        it.customerId,
        it.serviceCode,
        it.amount,
        it.receiptAmount
    )
}
