package com.example.command

import java.time.YearMonth

data class LoadReceiptTargetCommand(
    val billCycle: String,
    val billTargetYearMonth: YearMonth
)
