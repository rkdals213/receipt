package com.example.fixture

import com.example.domain.ReceiptTarget
import java.math.BigDecimal

fun 수납_대상(chargeType: ChargeType, paymentId: String, billSequenceId: String) = ReceiptTarget(
    id = 1L,
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
