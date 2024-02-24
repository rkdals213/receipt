package com.example.batch.receipt.account

import com.example.consts.DepositType
import com.example.consts.ReceiptType
import com.example.domain.Deposit
import com.example.domain.ReceiptByDeposit
import com.example.domain.command.ByDeposit
import com.example.infrastructure.mapper.toDomain
import com.example.repository.DepositJpaRepository
import com.example.repository.ReceiptJpaRepository
import com.example.repository.ReceiptTargetEntityRepository
import com.example.utils.generateUUID
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReceiptWithAccountWriter(
    private val receiptJobParameter: ReceiptJobParameter,
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository,
    private val receiptJpaRepository: ReceiptJpaRepository,
    private val depositJpaRepository: DepositJpaRepository,
) {

    @Bean
    @StepScope
    fun receiptWithAccountWriter1(): ItemWriter<BatchReceiptWithAccount> {
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
