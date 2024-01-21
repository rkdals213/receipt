package com.example.domain

import com.example.domain.command.ByAdvancedPayment
import com.example.domain.command.ReceiptCommand
import com.example.consts.AdvancedPaymentStatus
import com.example.consts.ReceiptType

class ReceiptByAdvancedPayment(
    paymentId: String,
    receiptTargets: MutableList<ReceiptTarget>,
    receiptLogs: MutableList<ReceiptLog>
) : Receipt(paymentId, receiptTargets, receiptLogs) {
    override fun receipt(receiptCommand: ReceiptCommand): AdvancedPayment {
        val advancedPayment = (receiptCommand as ByAdvancedPayment).advancedPayment

        var remains = advancedPayment.amount - advancedPayment.replacedAmount

        remains = receipt(discounts, remains, ReceiptType.ADVANCED_PAYMENT, advancedPayment.advancedPaymentId)
        remains = receipt(billings, remains, ReceiptType.ADVANCED_PAYMENT, advancedPayment.advancedPaymentId)

        val result = AdvancedPayment(
            id = advancedPayment.id,
            paymentId = paymentId,
            advancedPaymentId = advancedPayment.advancedPaymentId,
            depositId = advancedPayment.depositId,
            amount = advancedPayment.amount,
            replacedAmount = advancedPayment.amount - remains,
            advancedPaymentStatus = AdvancedPaymentStatus.OCCURRENCE
        )

        if (result.amount == result.replacedAmount) {
            result.advancedPaymentStatus = AdvancedPaymentStatus.REPLACED
        }

        return result
    }
}
