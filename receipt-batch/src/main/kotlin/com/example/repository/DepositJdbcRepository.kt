package com.example.repository

import com.example.domain.Deposit
import com.example.entity.DepositEntity
import org.springframework.stereotype.Repository

@Repository
class DepositJdbcRepository(
    private val depositEntityDao: DepositEntityDao
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
        depositEntityDao.save(depositEntity)
    }
}
