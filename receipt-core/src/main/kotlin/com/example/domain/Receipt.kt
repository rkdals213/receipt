package com.example.domain

import com.example.consts.ReceiptType
import com.example.domain.command.ReceiptCommand
import com.example.utils.generateUUID
import java.math.BigDecimal

abstract class Receipt(
    val paymentId: String,
    val receiptTargets: List<ReceiptTarget>,
    val receiptLogs: MutableList<ReceiptLog>
) {
    val discounts: List<ReceiptTarget>
        get() = receiptTargets.filter { it.amount < BigDecimal.ZERO }

    val billings: List<ReceiptTarget>
        get() = receiptTargets.filter { it.amount >= BigDecimal.ZERO }

    abstract fun receipt(receiptCommand: ReceiptCommand): AdvancedPayment

    protected fun receipt(
        targets: List<ReceiptTarget>,
        amount: BigDecimal,
        receiptType: ReceiptType,
        targetId: String
    ): BigDecimal {
        var remains = amount

        for (target in targets) {
            val receiptTargetAmount = remains.min(target.amount - target.receiptAmount)
            target.receiptAmount += receiptTargetAmount
            remains -= receiptTargetAmount

            val receiptLog = ReceiptLog(
                id = 0L,
                receiptId = generateUUID("RECEIPT"),
                billSequenceId = target.billSequenceId,
                contractId = target.contractId,
                paymentId = target.paymentId,
                customerId = target.customerId,
                serviceCode = target.serviceCode,
                amount = target.amount,
                receiptTotalAmount = target.receiptAmount,
                receiptAmount = receiptTargetAmount,
                receiptType = receiptType,
                targetId = targetId
            )
            receiptLogs.add(receiptLog)

            if (remains <= BigDecimal.ZERO) {
                break
            }
        }

        return remains
    }

    override fun toString(): String {
        return "Receipt(paymentId='$paymentId', receiptTargets=$receiptTargets, receiptLogs=$receiptLogs)"
    }
}

class ReceiptTarget(
    val id: Long,

    val billSequenceId: String,

    val contractId: String,

    val paymentId: String,

    val customerId: String,

    val serviceCode: String,

    val amount: BigDecimal,

    var receiptAmount: BigDecimal
) {
    override fun toString(): String {
        return "ReceiptTarget(id=$id, billSequenceId='$billSequenceId', contractId='$contractId', paymentId='$paymentId', customerId='$customerId', serviceCode='$serviceCode', amount=$amount, receiptAmount=$receiptAmount)"
    }
}

class ReceiptLog(
    val id: Long,

    val receiptId: String,

    val billSequenceId: String,

    val contractId: String,

    val paymentId: String,

    val customerId: String,

    val serviceCode: String,

    val amount: BigDecimal,

    val receiptTotalAmount: BigDecimal,

    val receiptAmount: BigDecimal,

    val receiptType: ReceiptType,

    val targetId: String
) {
    override fun toString(): String {
        return "ReceiptLog(id=$id, receiptId='$receiptId', billSequenceId='$billSequenceId', contractId='$contractId', paymentId='$paymentId', customerId='$customerId', serviceCode='$serviceCode', amount=$amount, receiptTotalAmount=$receiptTotalAmount, receiptAmount=$receiptAmount, receiptType=$receiptType, targetId='$targetId')"
    }
}
