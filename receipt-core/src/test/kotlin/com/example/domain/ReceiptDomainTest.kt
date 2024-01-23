package com.example.domain

import com.example.consts.Bank
import com.example.consts.ReceiptType
import com.example.domain.command.ByDeposit
import com.example.fixture.ChargeType
import com.example.fixture.수납_대상
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ReceiptDomainTest {

    @Test
    @DisplayName("입금으로 수납처리에 성공하여 선수금이 발생한다")
    fun receiptByDepositTest01() {
        val receiptByDeposit = ReceiptByDeposit(
            PAYMENT_ID,
            mutableListOf(
                수납_대상(기본료, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
                수납_대상(부가세, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
                수납_대상(스타클럽할인, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID)
            ),
            mutableListOf()
        )

        val byDeposit = ByDeposit(
            receiptType = ReceiptType.DEPOSIT,
            paymentId = PAYMENT_ID,
            amount = LARGE_DEPOSIT_AMOUNT,
            depositId = "",
            accountNumber = "",
            bank = Bank.KB
        )

        val advancedPayment = receiptByDeposit.receipt(byDeposit)

        Assertions.assertThat(advancedPayment.amount).isEqualTo(LARGE_DEPOSIT_AMOUNT - (기본료.amount + 부가세.amount + 스타클럽할인.amount))
        Assertions.assertThat(receiptByDeposit.receiptAmount()).isEqualTo(기본료.amount + 부가세.amount + 스타클럽할인.amount)
    }

    @Test
    @DisplayName("입금으로 수납처리에 성공하여 선수금이 발생하지 않는다")
    fun receiptByDepositTest02() {
        val receiptByDeposit = ReceiptByDeposit(
            PAYMENT_ID,
            mutableListOf(
                수납_대상(기본료, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
                수납_대상(부가세, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
                수납_대상(스타클럽할인, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID)
            ),
            mutableListOf()
        )

        val byDeposit = ByDeposit(
            receiptType = ReceiptType.DEPOSIT,
            paymentId = PAYMENT_ID,
            amount = SMALL_DEPOSIT_AMOUNT,
            depositId = "",
            accountNumber = "",
            bank = Bank.KB
        )

        val advancedPayment = receiptByDeposit.receipt(byDeposit)

        Assertions.assertThat(advancedPayment.amount).isEqualTo(BigDecimal.ZERO)
        Assertions.assertThat(receiptByDeposit.receiptAmount()).isEqualTo(SMALL_DEPOSIT_AMOUNT)
    }

    @Test
    @DisplayName("음수금액 입금으로 수납하면 실패한다")
    fun receiptByDepositTest03() {
        val receiptByDeposit = ReceiptByDeposit(
            PAYMENT_ID,
            mutableListOf(
                수납_대상(기본료, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
                수납_대상(부가세, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID),
                수납_대상(스타클럽할인, PAYMENT_ID, JANUARY_BILL_SEQUENCE_ID)
            ),
            mutableListOf()
        )

        val byDeposit = ByDeposit(
            receiptType = ReceiptType.DEPOSIT,
            paymentId = PAYMENT_ID,
            amount = BigDecimal.valueOf(-1_000),
            depositId = "",
            accountNumber = "",
            bank = Bank.KB
        )

        Assertions.assertThatThrownBy {
            receiptByDeposit.receipt(byDeposit)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("입금금액은 양수여야합니다")
    }

    private fun ReceiptByDeposit.receiptAmount() =
        receiptLogs.map { it.receiptAmount }
            .reduce { x, y -> x + y }

    companion object {
        private const val PAYMENT_ID = "P000000001"
        private val SMALL_DEPOSIT_AMOUNT = BigDecimal.valueOf(1_000)
        private val LARGE_DEPOSIT_AMOUNT = BigDecimal.valueOf(100_000)
        private const val JANUARY_BILL_SEQUENCE_ID = "202401P000000001"

        private val 기본료 = ChargeType("SR001", BigDecimal.valueOf(20_000))
        private val 부가세 = ChargeType("SR002", BigDecimal.valueOf(1_800))
        private val 스타클럽할인 = ChargeType("SR144", BigDecimal.valueOf(-2_000))
    }
}
