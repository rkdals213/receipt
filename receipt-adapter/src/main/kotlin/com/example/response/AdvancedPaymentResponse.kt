package com.example.response

import com.example.consts.AdvancedPaymentStatus
import java.math.BigDecimal

data class AdvancedPaymentResponses(
    val advancedPaymentResponses: List<AdvancedPaymentResponse>
)

data class AdvancedPaymentResponse(
    val id: Long,
    val paymentId: String,
    val advancedPaymentId: String,
    val depositId: String,
    val amount: BigDecimal,
    var replacedAmount: BigDecimal,
    var advancedPaymentStatus: AdvancedPaymentStatus,
)
