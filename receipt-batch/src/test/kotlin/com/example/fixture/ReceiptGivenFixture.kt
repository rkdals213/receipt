package com.example.fixture

import com.example.entity.ReceiptTargetEntity
import java.math.BigDecimal

fun 수납_대상_데이터(chargeType: ChargeType, paymentId: String, billSequenceId: String) = ReceiptTargetEntity(
    billSequenceId = billSequenceId,
    contractId = "C000000001",
    paymentId = paymentId,
    customerId = "100000000000000",
    serviceCode = chargeType.serviceCode,
    amount = chargeType.amount,
    receiptAmount = BigDecimal.ZERO,
)

data class ChargeType(
    var serviceCode: String,
    var amount: BigDecimal
)
