package com.example.batch.receipt.account

import com.example.common.CustomChunkListener
import com.example.common.CustomJobListener
import com.example.common.CustomStepExecutionListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ReceiptWithAccount(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val receiptWithAccountReader: ReceiptWithAccountReader,
    private val receiptWithAccountWriter: ReceiptWithAccountWriter
) {

    @Bean
    fun receiptWithAccountJob(): Job {
        return JobBuilder("receiptWithAccountJob", jobRepository)
            .listener(CustomJobListener())
            .start(receiptStep())
            .build()
    }

    @Bean
    @JobScope
    fun receiptStep(): Step {
        return StepBuilder("receiptStep", jobRepository)
            .chunk<BatchReceiptWithAccount, BatchReceiptWithAccount>(10, transactionManager)
            .listener(CustomStepExecutionListener())
            .listener(CustomChunkListener())
            .reader(receiptWithAccountReader.readReceiptFile())
            .writer(receiptWithAccountWriter.receiptWithAccountWriter1())
            .build()
    }
}
