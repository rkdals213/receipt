package com.example.presentation.web.controller

import com.example.presentation.web.request.ReceiptByAdvancedPaymentRequest
import com.example.presentation.web.request.ReceiptByDepositRequest
import com.example.presentation.web.response.ReceiptLogResponse
import com.example.presentation.web.response.ReceiptLogResponses
import com.example.usecase.ReceiptUseCase
import com.example.usecase.command.LoadReceiptCommand
import com.example.usecase.command.ReceiptByAdvancedPaymentCommand
import com.example.usecase.command.ReceiptByDepositCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/receipt")
class ReceiptController(
    private val receiptUseCase: ReceiptUseCase
) {

    @GetMapping("/bill-sequence-id/{billSequenceId}")
    fun loadReceiptByBillSequenceId(@PathVariable billSequenceId: String): ResponseEntity<ReceiptLogResponses> {
        val receiptLogs = receiptUseCase.loadReceiptByBillSequenceId(
            LoadReceiptCommand(billSequenceId)
        ).map {
            ReceiptLogResponse(
                id = it.id,
                receiptId = it.receiptId,
                billSequenceId = it.billSequenceId,
                contractId = it.contractId,
                paymentId = it.paymentId,
                customerId = it.customerId,
                serviceCode = it.serviceCode,
                amount = it.amount,
                receiptAmount = it.receiptAmount,
                receiptType = it.receiptType,
                targetId = it.targetId
            )
        }

        return ResponseEntity.ok().body(ReceiptLogResponses(receiptLogs))
    }

    @PostMapping("/by/deposit")
    fun receiptByDeposit(@RequestBody receiptByDepositRequest: ReceiptByDepositRequest) {
        receiptUseCase.receiptByDeposit(
            ReceiptByDepositCommand(
                paymentId = receiptByDepositRequest.paymentId,
                billSequenceId = receiptByDepositRequest.billSequenceId,
                depositAmount = receiptByDepositRequest.depositAmount,
                accountNumber = receiptByDepositRequest.accountNumber,
                depositType = receiptByDepositRequest.depositType,
                bank = receiptByDepositRequest.bank
            )
        )
    }

    @PostMapping("/by/advanced-payment")
    fun receiptByAdvancedPayment(@RequestBody receiptByAdvancedPaymentRequest: ReceiptByAdvancedPaymentRequest) {
        receiptUseCase.receiptByAdvancePayment(
            ReceiptByAdvancedPaymentCommand(
                paymentId = receiptByAdvancedPaymentRequest.paymentId,
                billSequenceId = receiptByAdvancedPaymentRequest.billSequenceId,
                advancedPaymentId = receiptByAdvancedPaymentRequest.advancedPaymentId
            )
        )
    }
}
