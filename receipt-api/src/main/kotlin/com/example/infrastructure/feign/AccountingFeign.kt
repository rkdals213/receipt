package com.example.infrastructure.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "accounting",
    url = "http://localhost",
    primary = false
)
interface AccountingClient {
    @PostMapping
    fun accountingTreatment()
}


