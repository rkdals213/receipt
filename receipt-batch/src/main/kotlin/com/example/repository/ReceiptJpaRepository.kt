package com.example.repository

import com.example.domain.Receipt
import com.example.entity.ReceiptEntity
import com.example.utils.generateUUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ReceiptJpaRepository(
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository,
    private val receiptEntityRepository: ReceiptEntityRepository
) {
    fun save(receipt: Receipt) {
        val receiptEntities = receipt.receiptLogs.map {
            ReceiptEntity(
                receiptId = generateUUID("RECEIPT"),
                billSequenceId = it.billSequenceId,
                contractId = it.contractId,
                paymentId = it.paymentId,
                customerId = it.customerId,
                serviceCode = it.serviceCode,
                amount = it.amount,
                receiptTotalAmount = it.receiptTotalAmount,
                receiptAmount = it.receiptAmount,
                receiptType = it.receiptType,
                targetId = it.targetId
            )
        }

        receiptEntityRepository.saveAll(receiptEntities)

        receipt.receiptTargets.forEach {
            val receiptTargetEntity = receiptTargetEntityRepository.findByIdOrNull(it.id) ?: throw RuntimeException()
            receiptTargetEntity.receiptAmount = it.receiptAmount
            receiptTargetEntityRepository.save(receiptTargetEntity)
        }
    }
}
