package com.example.batch.receipt.account

import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class ReceiptWithAccountReader(
    private val receiptJobParameter: ReceiptJobParameter
) {
    @Bean
    @StepScope
    fun readReceiptFile(): FlatFileItemReader<BatchReceiptWithAccount> {
        return FlatFileItemReaderBuilder<BatchReceiptWithAccount>().name("readReceiptFile")
            .lineTokenizer(DelimitedLineTokenizer())
            .fieldSetMapper(BatchReceiptWithAccountMapper())
            .resource(ClassPathResource(receiptJobParameter.fileName))
            .linesToSkip(1)
            .build()
    }
}
