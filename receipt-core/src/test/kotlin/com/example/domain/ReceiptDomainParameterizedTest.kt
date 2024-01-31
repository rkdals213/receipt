package com.example.domain

import com.example.consts.Bank
import com.example.consts.ReceiptType
import com.example.domain.command.ByDeposit
import com.example.fixture.ChargeType
import com.example.fixture.수납_대상
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.util.stream.Stream
import kotlin.streams.asStream

@DisplayName("수납 대상 금액보다 큰 금액")
class ReceiptDomainParameterizedTest {

    @ParameterizedTest
    @MethodSource(value = ["선수금_발생_케이스"])
    @DisplayName("입금으로 수납처리에 성공하여 선수금이 발생한다")
    fun receiptByDepositTest00(
        receiptTargets: MutableList<ReceiptTarget>,
        depositAmount: BigDecimal,
        advancedAmount: BigDecimal,
        receiptAmount: BigDecimal
    ) {
        val receiptByDeposit = ReceiptByDeposit(
            PAYMENT_ID,
            receiptTargets,
            mutableListOf()
        )

        val byDeposit = ByDeposit(
            receiptType = ReceiptType.DEPOSIT,
            paymentId = PAYMENT_ID,
            amount = depositAmount,
            depositId = "",
            accountNumber = "",
            bank = Bank.KB
        )

        val advancedPayment = receiptByDeposit.receipt(byDeposit)

        Assertions.assertThat(advancedPayment.amount).isEqualTo(advancedAmount)
        Assertions.assertThat(receiptByDeposit.receiptAmount()).isEqualTo(receiptAmount)
    }

    private fun ReceiptByDeposit.receiptAmount() =
        receiptLogs.map { it.receiptAmount }
            .reduce { x, y -> x + y }

    companion object {
        private const val PAYMENT_ID = "P000000001"
        private const val CHARGE_TARGET_PATH = "/charge_target.csv"
        private const val RESULT_FILE_PATH = "/result.csv"

        @JvmStatic
        fun 선수금_발생_케이스(): Stream<Arguments> {
            val path = "deposit/occurrence-advanced-payment/case"

            return generateSequence(1) { it + 1 }.take(2)
                .map { i ->
                    val chargeTargetPath = "$path$i$CHARGE_TARGET_PATH"
                    val resultFilePath = "$path$i$RESULT_FILE_PATH"

                    val targetStream = readFileLines(chargeTargetPath)
                        .skip(1)
                        .map { line ->
                            val columns = line.split(",")
                            수납_대상(ChargeType(columns[2], BigDecimal.valueOf(columns[3].toLong())), columns[0], columns[1])
                        }.toList()

                    val results = readFileLines(resultFilePath)
                        .skip(1)
                        .findFirst()
                        .get()
                        .split(",")
                        .map { BigDecimal.valueOf(it.toLong()) }
                        .toTypedArray()

                    Arguments.of(
                        targetStream,
                        *results
                    )
                }.asStream()
        }

        private fun readFileLines(chargeTargetPath: String): Stream<String> = BufferedReader(InputStreamReader(ClassPathResource(chargeTargetPath).inputStream)).lines()
    }
}
