package com.example.repository

import com.example.entity.ReceiptTargetEntity
import org.springframework.data.repository.CrudRepository

interface ReceiptTargetEntityDao : CrudRepository<ReceiptTargetEntity, Long> {
    fun findByBillSequenceIdIn(billSequenceIds: List<String>): List<ReceiptTargetEntity>

}
