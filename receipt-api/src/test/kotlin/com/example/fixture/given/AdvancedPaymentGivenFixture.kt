package com.example.fixture.given

import com.example.consts.AdvancedPaymentStatus
import com.example.entity.AdvancedPaymentEntity
import com.example.utils.generateUUID
import java.math.BigDecimal

fun 선수금_데이터(paymentId: String, amount: BigDecimal) = AdvancedPaymentEntity(
    paymentId = paymentId,
    advancedPaymentId = generateUUID("ADVANCED"),
    depositId = generateUUID("DEPOSIT"),
    amount = amount,
    replacedAmount = BigDecimal.ZERO,
    advancedPaymentStatus = AdvancedPaymentStatus.OCCURRENCE
)
