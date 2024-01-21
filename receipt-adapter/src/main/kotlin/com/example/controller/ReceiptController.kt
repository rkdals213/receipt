package com.example.controller

import com.example.application.ReceiptUseCase
import com.example.application.command.LoadReceiptCommand
import com.example.application.command.ReceiptByAdvancedPaymentCommand
import com.example.application.command.ReceiptByDepositCommand
import com.example.request.ReceiptByAdvancedPaymentRequest
import com.example.request.ReceiptByDepositRequest
import com.example.response.ReceiptLogResponse
import com.example.response.ReceiptLogResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
