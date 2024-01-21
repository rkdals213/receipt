package com.example.fixture.`when`

import com.example.getResource
import com.example.givenRequestSpecification
import com.example.postResource
import com.example.request.ReceiptByAdvancedPaymentRequest
import com.example.request.ReceiptByDepositRequest

fun 입금으로_수납_요청(receiptByDepositRequest: ReceiptByDepositRequest) =
    postResource(
        request = givenRequestSpecification().body(receiptByDepositRequest),
        url = "/receipt/by/deposit",
    )


fun 선수금으로_수납_요청(receiptByAdvancedPaymentRequest: ReceiptByAdvancedPaymentRequest) =
    postResource(
        request = givenRequestSpecification().body(receiptByAdvancedPaymentRequest),
        url = "/receipt/by/advanced-payment",
    )


fun 수납_조회_요청(billSequenceId: String) =
    getResource(
        request = givenRequestSpecification(),
        url = "/receipt/bill-sequence-id/$billSequenceId"
    )

