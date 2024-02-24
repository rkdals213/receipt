package com.example.presentation.web.request

data class ReceiptByAdvancedPaymentRequest(
    val paymentId: String,
    val billSequenceId: String,
    val advancedPaymentId: String
)
