package com.example.repository.command

data class LoadReceiptTargetCommand(
    val paymentId: String,
    val billSequenceId: String
)
