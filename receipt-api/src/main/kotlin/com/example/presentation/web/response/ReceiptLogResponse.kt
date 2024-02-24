package com.example.presentation.web.response

import com.example.consts.ReceiptType
import java.math.BigDecimal

data class ReceiptLogResponses(
    val receiptLogResponses: List<ReceiptLogResponse>
)

data class ReceiptLogResponse(
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
