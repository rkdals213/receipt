package com.example.command

data class LoadReceiptTargetCommand(
    val paymentId: String,
    val billSequenceId: String
)
