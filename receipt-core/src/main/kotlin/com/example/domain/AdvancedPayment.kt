package com.example.domain

import com.example.consts.AdvancedPaymentStatus
import java.math.BigDecimal

data class AdvancedPayment(
    val id: Long,
    val paymentId: String,
    val advancedPaymentId: String,
    val depositId: String,
    val amount: BigDecimal,
    var replacedAmount: BigDecimal,
    var advancedPaymentStatus: AdvancedPaymentStatus,
)
