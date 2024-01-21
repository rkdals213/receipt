package com.example.repository

import com.example.entity.AdvancedPaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdvancedPaymentEntityRepository : JpaRepository<AdvancedPaymentEntity, Long> {
    fun findByAdvancedPaymentId(advancedPaymentId: String): AdvancedPaymentEntity?

    fun findByPaymentId(paymentId: String): List<AdvancedPaymentEntity>
}
