package com.example.domain

import com.example.consts.AdvancedPaymentStatus
import com.example.consts.ReceiptType
import com.example.domain.command.ByDeposit
import com.example.domain.command.ReceiptCommand
import com.example.utils.generateUUID
import java.math.BigDecimal

class ReceiptByDeposit(
    paymentId: String,
    receiptTargets: MutableList<ReceiptTarget>,
    receiptLogs: MutableList<ReceiptLog>
) : Receipt(paymentId, receiptTargets, receiptLogs) {
    override fun receipt(receiptCommand: ReceiptCommand): AdvancedPayment {
        val byDeposit = receiptCommand as ByDeposit

        var remains = byDeposit.amount
        require(remains >= BigDecimal.ZERO) { "입금금액은 양수여야합니다" }

        remains = receipt(discounts, remains, ReceiptType.DEPOSIT, byDeposit.depositId)
        remains = receipt(billings, remains, ReceiptType.DEPOSIT, byDeposit.depositId)

        return if (remains > BigDecimal.ZERO) {
            AdvancedPayment(
                id = 0L,
                paymentId = paymentId,
                advancedPaymentId = generateUUID("ADVANCED"),
                depositId = byDeposit.depositId,
                amount = remains,
                replacedAmount = BigDecimal.ZERO,
                advancedPaymentStatus = AdvancedPaymentStatus.OCCURRENCE
            )
        } else {
            AdvancedPayment(
                id = 0L,
                paymentId = paymentId,
                advancedPaymentId = generateUUID("ADVANCED"),
                depositId = "",
                amount = BigDecimal.ZERO,
                replacedAmount = BigDecimal.ZERO,
                advancedPaymentStatus = AdvancedPaymentStatus.NONE
            )
        }
    }
}
