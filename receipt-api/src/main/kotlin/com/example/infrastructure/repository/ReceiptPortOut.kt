package com.example.infrastructure.repository

import com.example.command.LoadReceiptLogCommand
import com.example.command.LoadReceiptTargetCommand
import com.example.infrastructure.repository.jpa.ReceiptJpaRepository
import com.example.domain.Receipt
import com.example.domain.ReceiptByAdvancedPayment
import com.example.domain.ReceiptByDeposit
import com.example.domain.ReceiptLog
import org.springframework.stereotype.Component

interface LoadReceipt {
    fun loadReceiptLog(loadReceiptLogCommand: LoadReceiptLogCommand): List<ReceiptLog>

    fun loadReceiptByDeposit(loadReceiptTargetCommand: LoadReceiptTargetCommand): ReceiptByDeposit

    fun loadReceiptByAdvancedPayment(loadReceiptTargetCommand: LoadReceiptTargetCommand): ReceiptByAdvancedPayment
}

interface SaveReceipt {
    fun save(receipt: Receipt)
}

@Component
class ReceiptAdapter(
    private val receiptJpaRepository: ReceiptJpaRepository
) : LoadReceipt, SaveReceipt {
    override fun loadReceiptLog(loadReceiptLogCommand: LoadReceiptLogCommand): List<ReceiptLog> {
        return receiptJpaRepository.loadReceiptLog(loadReceiptLogCommand)
    }

    override fun loadReceiptByDeposit(loadReceiptTargetCommand: LoadReceiptTargetCommand): ReceiptByDeposit {
        return receiptJpaRepository.loadReceiptByDeposit(loadReceiptTargetCommand)
    }

    override fun loadReceiptByAdvancedPayment(loadReceiptTargetCommand: LoadReceiptTargetCommand): ReceiptByAdvancedPayment {
        return receiptJpaRepository.loadReceiptByAdvancedPayment(loadReceiptTargetCommand)
    }

    override fun save(receipt: Receipt) {
        receiptJpaRepository.save(receipt)
    }
}
