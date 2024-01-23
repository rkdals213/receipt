package com.example.fixture.given

import com.example.consts.Bank
import com.example.consts.DepositType
import com.example.entity.ReceiptTargetEntity
import com.example.request.ReceiptByAdvancedPaymentRequest
import com.example.request.ReceiptByDepositRequest
import java.math.BigDecimal

fun 수납_대상_데이터(chargeType: ChargeType, paymentId: String, billSequenceId: String) = ReceiptTargetEntity(
    billSequenceId = billSequenceId,
    contractId = "C000000001",
    paymentId = paymentId,
    customerId = "100000000000000",
    serviceCode = chargeType.serviceCode,
    amount = chargeType.amount,
    receiptAmount = BigDecimal.ZERO,
)

fun 입금_요청_데이터(billSequenceId: String, amount: BigDecimal) = ReceiptByDepositRequest(
    paymentId = "P000000001",
    billSequenceId = billSequenceId,
    depositAmount = amount,
    accountNumber = "",
    depositType = DepositType.CARD,
    bank = Bank.KB
)

fun 선수금_대체_입금_요청_데이터(paymentId: String, billSequenceId: String, advancedPaymentId: String) = ReceiptByAdvancedPaymentRequest(
    paymentId = paymentId,
    billSequenceId = billSequenceId,
    advancedPaymentId = advancedPaymentId
)

data class ChargeType(
    var serviceCode: String,
    var amount: BigDecimal
)
