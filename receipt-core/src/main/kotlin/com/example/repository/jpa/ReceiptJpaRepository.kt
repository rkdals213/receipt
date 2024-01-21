package com.example.repository.jpa

import com.example.command.LoadReceiptTargetCommand
import com.example.domain.ReceiptByDeposit
import com.example.mapper.toDomain
import com.example.repository.ReceiptTargetEntityRepository
import org.springframework.stereotype.Repository

@Repository
class ReceiptTargetJpaRepository(
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository
) {

    fun loadReceiptByDeposit(receiptTargetCommand: LoadReceiptTargetCommand): ReceiptByDeposit {
        val receiptTargets = receiptTargetEntityRepository.findAll()
            .toDomain()
            .toMutableList()

        return ReceiptByDeposit(receiptTargets, mutableListOf())
    }
}
