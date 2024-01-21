package com.example.repository

import com.example.domain.Deposit
import com.example.repository.jpa.DepositJpaRepository
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
