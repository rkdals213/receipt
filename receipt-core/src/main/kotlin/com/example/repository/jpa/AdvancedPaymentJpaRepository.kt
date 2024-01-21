package com.example.repository.jpa

import com.example.domain.AdvancedPayment
import com.example.entity.AdvancedPaymentEntity
import com.example.repository.AdvancedPaymentEntityRepository
import org.springframework.stereotype.Repository

@Repository
class AdvancedPaymentJpaRepository(
    private val advancedPaymentEntityRepository: AdvancedPaymentEntityRepository
) {
    fun findByAdvancedPaymentId(advancedPaymentId: String): AdvancedPayment {
        val advancedPaymentEntity =
            advancedPaymentEntityRepository.findByAdvancedPaymentId(advancedPaymentId) ?: throw RuntimeException()

        return advancedPaymentEntity.let {
            AdvancedPayment(
                id = it.id,
                paymentId = it.paymentId,
                advancedPaymentId = it.advancedPaymentId,
                depositId = it.depositId,
                amount = it.amount,
                replacedAmount = it.replacedAmount,
                advancedPaymentStatus = it.advancedPaymentStatus
            )
        }
    }

    fun findByPaymentId(paymentId: String): List<AdvancedPayment> {
        val advancedPaymentEntities =
            advancedPaymentEntityRepository.findByPaymentId(paymentId)

        return advancedPaymentEntities.map {
            AdvancedPayment(
                id = it.id,
                paymentId = it.paymentId,
                advancedPaymentId = it.advancedPaymentId,
                depositId = it.depositId,
                amount = it.amount,
                replacedAmount = it.replacedAmount,
                advancedPaymentStatus = it.advancedPaymentStatus
            )
        }
    }

    fun save(advancedPayment: AdvancedPayment) {
        val advancedPaymentEntity = AdvancedPaymentEntity(
            id = advancedPayment.id,
            paymentId = advancedPayment.paymentId,
            advancedPaymentId = advancedPayment.advancedPaymentId,
            depositId = advancedPayment.depositId,
            amount = advancedPayment.amount,
            replacedAmount = advancedPayment.replacedAmount,
            advancedPaymentStatus = advancedPayment.advancedPaymentStatus
        )

        advancedPaymentEntityRepository.save(advancedPaymentEntity)
    }

    fun update(advancedPayment: AdvancedPayment) {
        val advancedPaymentEntity =
            advancedPaymentEntityRepository.findByAdvancedPaymentId(advancedPayment.advancedPaymentId) ?: throw RuntimeException()

        advancedPaymentEntity.apply {
            this.replacedAmount = advancedPayment.replacedAmount
            this.advancedPaymentStatus = advancedPayment.advancedPaymentStatus
        }
    }
}
