package com.example.infrastructure

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Configuration
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

@Configuration
@EnableFeignClients
class FeignConfiguration
