package com.example.usecase.command

data class LoadReceiptTargetCommand(
    val paymentId: String,
    val billSequenceId: String
)
