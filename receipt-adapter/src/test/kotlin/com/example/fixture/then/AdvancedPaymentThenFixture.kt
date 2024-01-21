package com.example.fixture.then

import com.example.consts.AdvancedPaymentStatus
import com.example.response.AdvancedPaymentResponses
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import java.math.BigDecimal

fun 선수금_조회_요청됨(
    response: ExtractableResponse<Response>,
    advancedPaymentStatus: AdvancedPaymentStatus,
    amount: BigDecimal,
    replaceAmount: BigDecimal
) {
    val advancedPaymentResponse = response.`as`(AdvancedPaymentResponses::class.java).advancedPaymentResponses[0]
    Assertions.assertThat(advancedPaymentResponse.advancedPaymentStatus).isEqualTo(advancedPaymentStatus)
    Assertions.assertThat(advancedPaymentResponse.amount.toLong()).isEqualTo(amount.toLong())
    Assertions.assertThat(advancedPaymentResponse.replacedAmount.toLong()).isEqualTo(replaceAmount.toLong())
}
