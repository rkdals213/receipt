package com.example.domain

import java.math.BigDecimal

data class ReceiptTarget(
    val id: Long,

    val contractId: String,

    val paymentId: String,

    val customerId: String,

    val serviceCode: String,

    val amount: BigDecimal,

    var receiptAmount: BigDecimal
)
