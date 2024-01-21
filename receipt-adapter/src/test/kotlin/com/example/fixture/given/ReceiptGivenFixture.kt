package com.example.fixture.given

import com.example.entity.ReceiptTargetEntity
import java.math.BigDecimal

fun 수납_타겟_데이터() = listOf(
    ReceiptTargetEntity(
        billSequenceId = "202401P000000001",
        contractId = "C000000001",
        paymentId = "P000000001",
        customerId = "100000000000000",
        serviceCode = "SR001",
        amount = BigDecimal.valueOf(20_000L),
        receiptAmount = BigDecimal.ZERO,
    ),
    ReceiptTargetEntity(
        billSequenceId = "202401P000000001",
        contractId = "C000000001",
        paymentId = "P000000001",
        customerId = "100000000000000",
        serviceCode = "sr002",
        amount = BigDecimal.valueOf(1_800),
        receiptAmount = BigDecimal.ZERO,
    ),
    ReceiptTargetEntity(
        billSequenceId = "202401P000000001",
        contractId = "C000000001",
        paymentId = "P000000001",
        customerId = "100000000000000",
        serviceCode = "sr144",
        amount = BigDecimal.valueOf(-2_000),
        receiptAmount = BigDecimal.ZERO,
    ),
    ReceiptTargetEntity(
        billSequenceId = "202402P000000001",
        contractId = "C000000001",
        paymentId = "P000000001",
        customerId = "100000000000000",
        serviceCode = "SR001",
        amount = BigDecimal.valueOf(20_000L),
        receiptAmount = BigDecimal.ZERO,
    ),
    ReceiptTargetEntity(
        billSequenceId = "202402P000000001",
        contractId = "C000000001",
        paymentId = "P000000001",
        customerId = "100000000000000",
        serviceCode = "sr002",
        amount = BigDecimal.valueOf(1_800),
        receiptAmount = BigDecimal.ZERO,
    ),
    ReceiptTargetEntity(
        billSequenceId = "202402P000000001",
        contractId = "C000000001",
        paymentId = "P000000001",
        customerId = "100000000000000",
        serviceCode = "sr144",
        amount = BigDecimal.valueOf(-2_000),
        receiptAmount = BigDecimal.ZERO,
    )
)
