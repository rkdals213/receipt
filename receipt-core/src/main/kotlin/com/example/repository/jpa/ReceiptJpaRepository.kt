package com.example.repository.jpa

import com.example.repository.command.LoadReceiptTargetCommand
import com.example.domain.Receipt
import com.example.domain.ReceiptByAdvancedPayment
import com.example.domain.ReceiptByDeposit
import com.example.domain.ReceiptLog
import com.example.entity.ReceiptEntity
import com.example.mapper.toDomain
import com.example.repository.ReceiptEntityRepository
import com.example.repository.ReceiptTargetEntityRepository
import com.example.repository.command.LoadReceiptLogCommand
import com.example.utils.generateUUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ReceiptJpaRepository(
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository,
    private val receiptEntityRepository: ReceiptEntityRepository
) {
    fun loadReceiptLog(loadReceiptLogCommand: LoadReceiptLogCommand): List<ReceiptLog> {
        return receiptEntityRepository.findByBillSequenceId(loadReceiptLogCommand.billSequenceId)
            .map {
                ReceiptLog(
                    id = it.id,
                    receiptId = it.receiptId,
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
    }

    fun loadReceiptByDeposit(receiptTargetCommand: LoadReceiptTargetCommand): ReceiptByDeposit {
        val receiptTargets = receiptTargetEntityRepository.findByPaymentIdAndBillSequenceId(
            receiptTargetCommand.paymentId,
            receiptTargetCommand.billSequenceId
        )
            .toDomain()
            .toMutableList()
        println("loadReceiptByDeposit $receiptTargets")

        return ReceiptByDeposit(receiptTargetCommand.paymentId, receiptTargets, mutableListOf())
    }

    fun loadReceiptByAdvancedPayment(receiptTargetCommand: LoadReceiptTargetCommand): ReceiptByAdvancedPayment {
        val receiptTargets = receiptTargetEntityRepository.findByPaymentIdAndBillSequenceId(
            receiptTargetCommand.paymentId,
            receiptTargetCommand.billSequenceId
        )
            .toDomain()
            .toMutableList()

        return ReceiptByAdvancedPayment(receiptTargetCommand.paymentId, receiptTargets, mutableListOf())
    }

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
