package com.example.repository

import com.example.domain.AdvancedPayment
import com.example.repository.jpa.AdvancedPaymentJpaRepository
import org.springframework.stereotype.Component


interface LoadAdvancedPayment {
    fun byAdvancedPaymentId(advancedPaymentId: String): AdvancedPayment

    fun byPaymentId(paymentId: String): List<AdvancedPayment>
}

interface SaveAdvancedPayment {
    fun save(advancedPayment: AdvancedPayment)
    fun update(advancedPayment: AdvancedPayment)
}

@Component
class AdvancedPaymentAdapter(
    private val advancedPaymentJpaRepository: AdvancedPaymentJpaRepository
) : LoadAdvancedPayment, SaveAdvancedPayment {
    override fun byAdvancedPaymentId(advancedPaymentId: String): AdvancedPayment {
        return advancedPaymentJpaRepository.findByAdvancedPaymentId(advancedPaymentId)
    }

    override fun byPaymentId(paymentId: String): List<AdvancedPayment> {
        return advancedPaymentJpaRepository.findByPaymentId(paymentId)
    }

    override fun save(advancedPayment: AdvancedPayment) {
        advancedPaymentJpaRepository.save(advancedPayment)
    }

    override fun update(advancedPayment: AdvancedPayment) {
        advancedPaymentJpaRepository.update(advancedPayment)
    }
}
