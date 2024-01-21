package com.example.batch.receipt.account

import com.example.application.ReceiptUseCase
import com.example.application.command.ReceiptByDepositCommand
import com.example.common.CustomChunkListener
import com.example.common.CustomJobListener
import com.example.common.CustomStepExecutionListener
import com.example.common.ReceiptJobParameter
import com.example.consts.DepositType
import com.example.consts.ReceiptType
import com.example.domain.Deposit
import com.example.domain.ReceiptByDeposit
import com.example.domain.command.ByDeposit
import com.example.mapper.toDomain
import com.example.repository.ReceiptTargetEntityRepository
import com.example.repository.jpa.DepositJpaRepository
import com.example.repository.jpa.ReceiptJpaRepository
import com.example.utils.generateUUID
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ReceiptWithAccount(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val receiptJobParameter: ReceiptJobParameter,
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository,
    private val receiptJpaRepository: ReceiptJpaRepository,
    private val depositJpaRepository: DepositJpaRepository,
    private val receiptUseCase: ReceiptUseCase
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
            .reader(readReceiptFile())
            .writer(receiptWithAccountWriter1())
//            .writer(receiptWithAccountWriter2())
            .build()
    }


    @Bean
    @StepScope
    fun readReceiptFile(): FlatFileItemReader<BatchReceiptWithAccount> {
        return FlatFileItemReaderBuilder<BatchReceiptWithAccount>().name("readReceiptFile")
            .lineTokenizer(DelimitedLineTokenizer())
            .fieldSetMapper(BatchReceiptWithAccountMapper())
            .resource(FileSystemResource("receipt-batch/src/main/resources/account.txt"))
            .linesToSkip(1)
            .build()
    }

    @Bean
    @StepScope
    fun receiptWithAccountWriter1(): ItemWriter<BatchReceiptWithAccount> {
        return ItemWriter<BatchReceiptWithAccount> { list: Chunk<out BatchReceiptWithAccount> ->
            val receiptTargets = receiptTargetEntityRepository.findByBillSequenceIdIn(
                list.items.map { receiptJobParameter.billYearMonth + it.paymentId }
            )
                .toDomain()
                .groupBy { it.billSequenceId }

            list.items.forEach {
                receiptUseCase.receiptByDeposit(
                    ReceiptByDepositCommand(
                        paymentId = it.paymentId,
                        billSequenceId = receiptJobParameter.billYearMonth + it.paymentId,
                        depositAmount = it.amount,
                        accountNumber = it.accountNumber,
                        depositType = DepositType.ACCOUNT,
                        bank = it.bank
                    )
                )
            }
        }
    }

    @Bean
    @StepScope
    fun receiptWithAccountWriter2(): ItemWriter<BatchReceiptWithAccount> {
        return ItemWriter<BatchReceiptWithAccount> { list: Chunk<out BatchReceiptWithAccount> ->
            val receiptTargets = receiptTargetEntityRepository.findByBillSequenceIdIn(
                list.items.map { receiptJobParameter.billYearMonth + it.paymentId }
            )
                .toDomain()
                .groupBy { it.billSequenceId }

            for (item in list.items) {
                val target = receiptTargets[receiptJobParameter.billYearMonth + item.paymentId] ?: continue

                val receiptByDeposit = ReceiptByDeposit(item.paymentId, target.toMutableList(), mutableListOf())

                val deposit = Deposit(
                    id = 0L,
                    depositId = generateUUID("DEPOSIT"),
                    paymentId = item.paymentId,
                    amount = item.amount,
                    receiptType = ReceiptType.DEPOSIT,
                    depositType = DepositType.ACCOUNT,
                    accountNumber = item.accountNumber,
                    bank = item.bank
                )
                depositJpaRepository.save(deposit)

                val byDeposit = ByDeposit(
                    receiptType = ReceiptType.DEPOSIT,
                    paymentId = item.paymentId,
                    amount = deposit.amount,
                    depositId = deposit.depositId,
                    accountNumber = deposit.accountNumber,
                    bank = deposit.bank
                )
                receiptByDeposit.receipt(byDeposit)

                receiptJpaRepository.save(receiptByDeposit)
            }
        }
    }
}
