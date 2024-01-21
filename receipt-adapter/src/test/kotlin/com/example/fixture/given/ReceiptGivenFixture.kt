package com.example.fixture.given

import com.example.consts.Bank
import com.example.consts.DepositType
import com.example.entity.ReceiptTargetEntity
import com.example.request.ReceiptByAdvancedPaymentRequest
import com.example.request.ReceiptByDepositRequest
import java.math.BigDecimal

fun 기본료_수납_대상_데이터(paymentId: String, billSequenceId: String, amount: BigDecimal) = ReceiptTargetEntity(
    billSequenceId = billSequenceId,
    contractId = "C000000001",
    paymentId = paymentId,
    customerId = "100000000000000",
    serviceCode = "SR001",
    amount = amount,
    receiptAmount = BigDecimal.ZERO,
)

fun 스타클럽할인_수납_대상_데이터(paymentId: String, billSequenceId: String, amount: BigDecimal) = ReceiptTargetEntity(
    billSequenceId = billSequenceId,
    contractId = "C000000001",
    paymentId = paymentId,
    customerId = "100000000000000",
    serviceCode = "SR144",
    amount = amount,
    receiptAmount = BigDecimal.ZERO,
)

fun 부가세_수납_대상_데이터(paymentId: String, billSequenceId: String, amount: BigDecimal) = ReceiptTargetEntity(
    billSequenceId = billSequenceId,
    contractId = "C000000001",
    paymentId = paymentId,
    customerId = "100000000000000",
    serviceCode = "SR002",
    amount = amount,
    receiptAmount = BigDecimal.ZERO,
)

fun 입금_요청_데이터(billSequenceId: String, amount: BigDecimal) = ReceiptByDepositRequest(
    paymentId = "P000000001",
    billSequenceId = billSequenceId,
    depositAmount = amount,
    accountNumber = "1010101010",
    depositType = DepositType.CARD,
    bank = Bank.KB
)

fun 선수금_대체_입금_요청_데이터(paymentId: String, billSequenceId: String, advancedPaymentId: String) = ReceiptByAdvancedPaymentRequest(
    paymentId = paymentId,
    billSequenceId = billSequenceId,
    advancedPaymentId = advancedPaymentId
)
