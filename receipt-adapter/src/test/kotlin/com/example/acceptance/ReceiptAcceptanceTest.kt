package com.example.acceptance

import com.example.AcceptanceTest
import com.example.DatabaseCleanup
import com.example.consts.AdvancedPaymentStatus
import com.example.entity.AdvancedPaymentEntity
import com.example.entity.ReceiptTargetEntity
import com.example.fixture.given.*
import com.example.fixture.then.선수금_조회_요청됨
import com.example.fixture.then.수납_조회_요청됨
import com.example.fixture.`when`.선수금_조회_요청
import com.example.fixture.`when`.선수금으로_수납_요청
import com.example.fixture.`when`.수납_조회_요청
import com.example.fixture.`when`.입금으로_수납_요청
import com.example.repository.AdvancedPaymentEntityRepository
import com.example.repository.ReceiptTargetEntityRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class ReceiptAcceptanceTest @Autowired constructor(
    databaseCleanup: DatabaseCleanup,
    private val receiptTargetEntityRepository: ReceiptTargetEntityRepository,
    private val advancedPaymentEntityRepository: AdvancedPaymentEntityRepository
) : AcceptanceTest(databaseCleanup) {

    @Test
    @DisplayName("입금 금액이 청구금액보다 큰 경우")
    fun receiptByDeposit01() {
        수납_타겟_데이터_등록되어있음(
            기본료_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 기본료),
            스타클럽할인_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 스타클럽할인),
            부가세_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 부가세)
        )

        val receiptByDepositRequest = 입금_요청_데이터(JANUARY_BILL_SEQUENCE_ID, LARGE_DEPOSIT_AMOUNT)
        입금으로_수납_요청(receiptByDepositRequest)

        val receiptResponse = 수납_조회_요청(JANUARY_BILL_SEQUENCE_ID)
        수납_조회_요청됨(receiptResponse, 기본료 + 스타클럽할인 + 부가세)

        val advancedPaymentResponse = 선수금_조회_요청(PAYMENT_ID)
        선수금_조회_요청됨(advancedPaymentResponse, AdvancedPaymentStatus.OCCURRENCE, LARGE_DEPOSIT_AMOUNT - 기본료 - 스타클럽할인 - 부가세, BigDecimal.ZERO)
    }

    @Test
    @DisplayName("입금 금액이 청구금액보다 작은 경우")
    fun receiptByDeposit02() {
        수납_타겟_데이터_등록되어있음(
            기본료_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 기본료),
            스타클럽할인_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 스타클럽할인),
            부가세_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 부가세)
        )

        val receiptByDepositRequest = 입금_요청_데이터(JANUARY_BILL_SEQUENCE_ID, SMALL_DEPOSIT_AMOUNT)
        입금으로_수납_요청(receiptByDepositRequest)

        val receiptResponse = 수납_조회_요청(JANUARY_BILL_SEQUENCE_ID)
        수납_조회_요청됨(receiptResponse, SMALL_DEPOSIT_AMOUNT)
    }

    @Test
    @DisplayName("선수금 금액이 청구금액보다 큰 경우")
    fun receiptByAdvancedPayment02() {
        수납_타겟_데이터_등록되어있음(
            기본료_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 기본료),
            스타클럽할인_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 스타클럽할인),
            부가세_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, 부가세)
        )

        val advancedPaymentEntity = 선수금_데이터(PAYMENT_ID, LARGE_DEPOSIT_AMOUNT)
        선수금_데이터_등록되어있음(advancedPaymentEntity)

        val receiptByAdvancedPaymentRequest = 선수금_대체_입금_요청_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, advancedPaymentEntity.advancedPaymentId)
        선수금으로_수납_요청(receiptByAdvancedPaymentRequest)

        val advancedPaymentResponse = 선수금_조회_요청(PAYMENT_ID)
        선수금_조회_요청됨(advancedPaymentResponse, AdvancedPaymentStatus.OCCURRENCE, LARGE_DEPOSIT_AMOUNT, 기본료 + 스타클럽할인 + 부가세)
    }

    private fun 수납_타겟_데이터_등록되어있음(vararg receiptTargetEntity: ReceiptTargetEntity) {
        receiptTargetEntity.forEach {
            receiptTargetEntityRepository.save(it)
        }
    }

    private fun 선수금_데이터_등록되어있음(vararg advancedPaymentEntity: AdvancedPaymentEntity) {
        advancedPaymentEntity.forEach {
            advancedPaymentEntityRepository.save(it)
        }
    }

    companion object {
        private const val PAYMENT_ID = "P000000001"
        private val SMALL_DEPOSIT_AMOUNT = BigDecimal.valueOf(1_000)
        private val LARGE_DEPOSIT_AMOUNT = BigDecimal.valueOf(100_000)
        private const val JANUARY_BILL_SEQUENCE_ID = "202401P000000001"

        private val 기본료 = BigDecimal.valueOf(20_000)
        private val 스타클럽할인 = BigDecimal.valueOf(-2_000)
        private val 부가세 = BigDecimal.valueOf(1_800)
    }
}
