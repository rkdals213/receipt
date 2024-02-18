package com.example

import com.example.infrastructure.AccountingClient
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
class BatchTestConfig {
}

@Component
@Primary
class FakeAccountingClient: AccountingClient {
    override fun accountingTreatment() {
        println("fake account client")
    }
}
