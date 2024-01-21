package com.example

import io.restassured.module.kotlin.extensions.Given
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.springframework.http.MediaType

fun givenRequestSpecification(): RequestSpecification = Given {
    contentType(MediaType.APPLICATION_JSON_VALUE)
    log().all()
}

fun getResource(
    request: RequestSpecification = givenRequestSpecification(),
    url: String
): ExtractableResponse<Response> {
    return request.with()
        .get(url)
        .then()
        .log().all()
        .extract()
}

fun postResource(
    request: RequestSpecification = givenRequestSpecification(),
    url: String
): ExtractableResponse<Response> {
    return request.with()
        .post(url)
        .then()
        .log().all()
        .extract()
}
