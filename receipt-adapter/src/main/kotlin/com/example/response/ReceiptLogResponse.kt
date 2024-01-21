package com.example.response

import com.example.consts.ReceiptType
import java.math.BigDecimal

data class ReceiptResponses(
    val receiptResponses: List<ReceiptResponse>
)

data class ReceiptResponse(
    val id: Long,
    val receiptId: String,
    val billSequenceId: String,
    val contractId: String,
    val paymentId: String,
    val customerId: String,
    val serviceCode: String,
    val amount: BigDecimal,
    val receiptAmount: BigDecimal,
    val receiptType: ReceiptType,
    val targetId: String
)
