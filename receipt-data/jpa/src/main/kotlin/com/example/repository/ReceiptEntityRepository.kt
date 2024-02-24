package com.example.repository

import com.example.entity.ReceiptEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReceiptEntityRepository : JpaRepository<ReceiptEntity, Long> {
    fun findByBillSequenceId(billSequenceId: String): List<ReceiptEntity>
}
