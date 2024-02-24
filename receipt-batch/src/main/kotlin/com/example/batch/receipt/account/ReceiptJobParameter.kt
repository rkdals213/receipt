package com.example.batch.receipt.account

import com.example.common.JobParameter
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JobParameter
class ReceiptJobParameter(
    val requestDate: LocalDateTime,
    val billYearMonth: String,
    val fileName: String
)

@Configuration
class JobParameterConfiguration {
    @Bean
    @JobScope
    fun jobParameter(
        @Value("#{jobParameters[requestDate]}") requestDate: String,
        @Value("#{jobParameters[billYearMonth]}") billYearMonth: String,
        @Value("#{jobParameters[fileName]}") fileName: String
    ): ReceiptJobParameter {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val dateTime = LocalDateTime.parse(requestDate, formatter)

        return ReceiptJobParameter(dateTime, billYearMonth, fileName)
    }
}
