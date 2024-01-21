package com.example

import com.example.consts.AdvancedPaymentStatus
import com.example.fixture.given.기본료_수납_대상_데이터
import com.example.fixture.given.부가세_수납_대상_데이터
import com.example.fixture.given.스타클럽할인_수납_대상_데이터
import com.example.fixture.given.입금_요청_데이터
import com.example.fixture.then.선수금_조회_요청됨
import com.example.fixture.`when`.선수금_조회_요청
import com.example.fixture.`when`.입금_요청_데이터로_수납_요청
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

class ReceiptAcceptanceTest @Autowired constructor(
    databaseCleanup: DatabaseCleanup,
    @PersistenceContext
    private val entityManager: EntityManager
) : AcceptanceTest(databaseCleanup) {

    @Test
    @Transactional
    fun test() {
        수납_타겟_데이터_등록되어있음()

        val receiptByDepositRequest = 입금_요청_데이터(JANUARY_BILL_SEQUENCE_ID, LARGE_DEPOSIT_AMOUNT)
        입금_요청_데이터로_수납_요청(receiptByDepositRequest)

        val advancedPaymentResponse = 선수금_조회_요청(PAYMENT_ID)
        선수금_조회_요청됨(advancedPaymentResponse, AdvancedPaymentStatus.OCCURRENCE)

    }

    private fun 수납_타겟_데이터_등록되어있음() {
        entityManager.persist(기본료_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, BigDecimal.valueOf(20_000)))
        entityManager.persist(스타클럽할인_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, BigDecimal.valueOf(-2_000)))
        entityManager.persist(부가세_수납_대상_데이터(PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID, BigDecimal.valueOf(18_000)))
    }

    companion object {
        private const val PAYMENT_ID = "P000000001"
        private val SMALL_DEPOSIT_AMOUNT = BigDecimal.valueOf(1_000)
        private val LARGE_DEPOSIT_AMOUNT = BigDecimal.valueOf(100_000)
        private const val JANUARY_BILL_SEQUENCE_ID = "202401P000000001"
    }
}
