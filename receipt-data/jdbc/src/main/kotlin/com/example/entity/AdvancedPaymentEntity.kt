package com.example.entity

import com.example.consts.AdvancedPaymentStatus
import org.springframework.data.annotation.Id
import java.math.BigDecimal

class AdvancedPaymentEntity(
    @Id
    val id: Long = 0L,

    val paymentId: String,

    val advancedPaymentId: String,

    val depositId: String,

    val amount: BigDecimal,

    var replacedAmount: BigDecimal,

    var advancedPaymentStatus: AdvancedPaymentStatus,
) {
}
