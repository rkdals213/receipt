package com.example.command

import java.time.YearMonth

data class ReceiptByAdvancedPaymentCommand(
    val billCycle: String,
    val billTargetYearMonth: YearMonth,
    val advancedPaymentId: String
)
