package com.example.fixture.then

import com.example.response.ReceiptLogResponses
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import java.math.BigDecimal

fun 수납_조회_요청됨(
    response: ExtractableResponse<Response>,
    receiptAmount: BigDecimal
) {
    val receiptLogResponses = response.`as`(ReceiptLogResponses::class.java)
    val totalReceiptAmount = receiptLogResponses.receiptLogResponses.map { it.receiptAmount }
        .reduce { x, y -> x + y }

    Assertions.assertThat(totalReceiptAmount.toLong()).isEqualTo(receiptAmount.toLong())
}
