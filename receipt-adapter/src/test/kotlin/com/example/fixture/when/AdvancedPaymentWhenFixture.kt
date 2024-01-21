package com.example.fixture.`when`

import com.example.getResource
import com.example.givenRequestSpecification

fun 선수금_조회_요청(paymentId: String) =
    getResource(
        request = givenRequestSpecification(),
        url = "/advanced-payment/$paymentId",
    )
