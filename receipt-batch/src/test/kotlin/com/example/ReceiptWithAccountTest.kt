package com.example

import com.example.entity.ReceiptTargetEntity
import com.example.fixture.ChargeType
import com.example.fixture.수납_대상_데이터
import com.example.repository.ReceiptTargetEntityRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBatchTest
@SpringBootTest
class ReceiptWithAccountTest @Autowired constructor(
    private val jobLauncherTestUtils: JobLauncherTestUtils,
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository
) {

    @Test
    @DisplayName("계좌 수납파일로 수납을 진행한다")
    fun receiptWithAccountTest01() {
        // given
        수납_타겟_데이터_등록되어있음(
            수납_대상_데이터(기본료, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
            수납_대상_데이터(스타클럽할인, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
            수납_대상_데이터(부가세, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID)
        )

        val jobParameters = JobParametersBuilder()
            .addString("requestDate", REQUEST_DATE)
            .addString("billYearMonth", BILL_YEAR)
            .addString("fileName", FILE_NAME)
            .toJobParameters()

        // when
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        // then
        val results = receiptTargetEntityRepository.findByPaymentIdAndBillSequenceId(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID)
        수납처리_완료됨(jobExecution, results)
    }

    private fun 수납_타겟_데이터_등록되어있음(vararg receiptTargetEntity: ReceiptTargetEntity) {
        receiptTargetEntity.forEach {
            receiptTargetEntityRepository.save(it)
        }
    }

    private fun 수납처리_완료됨(jobExecution: JobExecution, results: List<ReceiptTargetEntity>) {
        Assertions.assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)
        for (result in results) {
            Assertions.assertThat(result.receiptAmount).isEqualTo(result.amount)
        }
    }

    companion object {
        private const val REQUEST_DATE = "20240204000000"
        private const val BILL_YEAR = "202401"
        private const val FILE_NAME = "account.txt"

        private const val PAYMENT_ID = "P000000001"
        private const val JANUARY_BILL_SEQUENCE_ID = "202401P000000001"

        private val 기본료 = ChargeType("SR001", BigDecimal.valueOf(20_000))
        private val 스타클럽할인 = ChargeType("SR144", BigDecimal.valueOf(-2_000))
        private val 부가세 = ChargeType("SR002", BigDecimal.valueOf(1_800))
    }
}
