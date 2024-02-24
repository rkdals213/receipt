package com.example.usecase

import com.example.infrastructure.feign.AccountingClient
import com.example.command.LoadReceiptLogCommand
import com.example.command.LoadReceiptTargetCommand
import com.example.consts.AdvancedPaymentStatus
import com.example.consts.ReceiptType
import com.example.domain.Deposit
import com.example.domain.ReceiptLog
import com.example.domain.command.ByAdvancedPayment
import com.example.domain.command.ByDeposit
import com.example.infrastructure.repository.*
import com.example.usecase.command.LoadReceiptCommand
import com.example.usecase.command.ReceiptByAdvancedPaymentCommand
import com.example.usecase.command.ReceiptByDepositCommand
import com.example.utils.generateUUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface ReceiptUseCase {
    fun loadReceiptByBillSequenceId(loadReceiptCommand: LoadReceiptCommand): List<ReceiptLog>
    fun receiptByDeposit(receiptByDepositCommand: ReceiptByDepositCommand)
    fun receiptByAdvancePayment(receiptByAdvancedPaymentCommand: ReceiptByAdvancedPaymentCommand)
}

@Service
class ReceiptService(
    private val loadReceipt: LoadReceipt,
    private val saveReceipt: SaveReceipt,
    private val saveDeposit: SaveDeposit,
    private val loadAdvancedPayment: LoadAdvancedPayment,
    private val saveAdvancedPayment: SaveAdvancedPayment,
    private val accountingClient: AccountingClient
) : ReceiptUseCase {
    override fun loadReceiptByBillSequenceId(loadReceiptCommand: LoadReceiptCommand): List<ReceiptLog> {
        return loadReceipt.loadReceiptLog(
            LoadReceiptLogCommand(loadReceiptCommand.billSequenceId)
        )
    }

    @Transactional
    override fun receiptByDeposit(receiptByDepositCommand: ReceiptByDepositCommand) {
        val receiptByDeposit = loadReceipt.loadReceiptByDeposit(
            LoadReceiptTargetCommand(receiptByDepositCommand.paymentId, receiptByDepositCommand.billSequenceId)
        )

        val deposit = Deposit(
            id = 0L,
            depositId = generateUUID("DEPOSIT"),
            paymentId = receiptByDepositCommand.paymentId,
            amount = receiptByDepositCommand.depositAmount,
            receiptType = ReceiptType.DEPOSIT,
            depositType = receiptByDepositCommand.depositType,
            accountNumber = receiptByDepositCommand.accountNumber,
            bank = receiptByDepositCommand.bank
        )

        saveDeposit.save(deposit)

        val byDeposit = ByDeposit(
            paymentId = receiptByDeposit.paymentId,
            receiptType = ReceiptType.DEPOSIT,
            depositId = deposit.depositId,
            amount = receiptByDepositCommand.depositAmount,
            accountNumber = receiptByDepositCommand.accountNumber,
            bank = receiptByDepositCommand.bank
        )

        val advancedPayment = receiptByDeposit.receipt(byDeposit)

        saveReceipt.save(receiptByDeposit)

        if (advancedPayment.advancedPaymentStatus == AdvancedPaymentStatus.OCCURRENCE) {
            saveAdvancedPayment.save(advancedPayment)
        }

        accountingClient.accountingTreatment()
    }

    @Transactional
    override fun receiptByAdvancePayment(receiptByAdvancedPaymentCommand: ReceiptByAdvancedPaymentCommand) {
        val receiptByAdvancedPayment = loadReceipt.loadReceiptByAdvancedPayment(
            LoadReceiptTargetCommand(receiptByAdvancedPaymentCommand.paymentId, receiptByAdvancedPaymentCommand.billSequenceId)
        )

        val byAdvancedPayment = ByAdvancedPayment(
            receiptType = ReceiptType.ADVANCED_PAYMENT,
            paymentId = receiptByAdvancedPayment.paymentId,
            advancedPayment = loadAdvancedPayment.byAdvancedPaymentId(receiptByAdvancedPaymentCommand.advancedPaymentId)
        )

        val advancedPayment = receiptByAdvancedPayment.receipt(byAdvancedPayment)

        saveReceipt.save(receiptByAdvancedPayment)

        saveAdvancedPayment.update(advancedPayment)
    }
}
