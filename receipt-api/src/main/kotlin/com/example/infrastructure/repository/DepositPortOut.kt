package com.example.infrastructure.repository

import com.example.infrastructure.repository.jpa.DepositJpaRepository
import com.example.domain.Deposit
import org.springframework.stereotype.Component

interface SaveDeposit {
    fun save(deposit: Deposit)
}

@Component
class DepositAdapter(
    private val depositJpaRepository: DepositJpaRepository
) : SaveDeposit {
    override fun save(deposit: Deposit) {
        depositJpaRepository.save(deposit)
    }
}
