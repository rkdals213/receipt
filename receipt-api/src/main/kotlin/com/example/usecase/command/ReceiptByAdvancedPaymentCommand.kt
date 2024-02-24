package com.example.usecase.command

data class ReceiptByAdvancedPaymentCommand(
    val paymentId: String,
    val billSequenceId: String,
    val advancedPaymentId: String
)
