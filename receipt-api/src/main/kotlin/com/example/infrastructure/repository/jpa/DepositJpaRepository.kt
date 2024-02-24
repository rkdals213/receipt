package com.example.infrastructure.repository.jpa

import com.example.domain.Deposit
import com.example.entity.DepositEntity
import com.example.repository.DepositEntityRepository
import org.springframework.stereotype.Repository

@Repository
class DepositJpaRepository(
    private val depositEntityRepository: DepositEntityRepository
) {
    fun save(deposit: Deposit) {
        val depositEntity = DepositEntity(
            depositId = deposit.depositId,
            paymentId = deposit.paymentId,
            amount = deposit.amount,
            receiptType = deposit.receiptType,
            depositType = deposit.depositType,
            accountNumber = deposit.accountNumber,
            bank = deposit.bank
        )
        depositEntityRepository.save(depositEntity)
    }
}
