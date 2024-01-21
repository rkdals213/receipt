package com.example.repository

import com.example.command.LoadReceiptTargetCommand
import com.example.domain.Receipt
import com.example.domain.ReceiptByDeposit
import com.example.repository.jpa.ReceiptJpaRepository
import org.springframework.stereotype.Component

interface LoadReceipt {
    fun loadReceiptByDeposit(loadReceiptTargetCommand: LoadReceiptTargetCommand): ReceiptByDeposit
}

interface SaveReceipt {
    fun save(receipt: Receipt)
}

@Component
class ReceiptAdapter(
    private val receiptJpaRepository: ReceiptJpaRepository
) : LoadReceipt, SaveReceipt {
    override fun loadReceiptByDeposit(loadReceiptTargetCommand: LoadReceiptTargetCommand): ReceiptByDeposit {
        return receiptJpaRepository.loadReceiptByDeposit(loadReceiptTargetCommand)
    }

    override fun save(receipt: Receipt) {
        receiptJpaRepository.save(receipt)
    }
}
