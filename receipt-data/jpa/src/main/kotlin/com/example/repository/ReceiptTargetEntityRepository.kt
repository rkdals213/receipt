package com.example.repository

import com.example.entity.ReceiptTargetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReceiptTargetEntityRepository : JpaRepository<ReceiptTargetEntity, Long> {
    fun findByPaymentIdAndBillSequenceId(paymentId: String, billSequenceId: String): List<ReceiptTargetEntity>

    fun findByBillSequenceIdIn(billSequenceIds: List<String>): List<ReceiptTargetEntity>
}
