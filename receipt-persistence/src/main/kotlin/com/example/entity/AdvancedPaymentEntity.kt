package com.example.entity

import com.example.consts.AdvancedPaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class AdvancedPaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val paymentId: String,

    val advancedPaymentId: String,

    val depositId: String,

    val amount: BigDecimal,

    var replacedAmount: BigDecimal,

    @Enumerated(EnumType.STRING)
    var advancedPaymentStatus: AdvancedPaymentStatus,
) {
}
