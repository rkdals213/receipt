package com.example.request

data class ReceiptByAdvancedPaymentRequest(
    val paymentId: String,
    val billSequenceId: String,
    val advancedPaymentId: String
)
