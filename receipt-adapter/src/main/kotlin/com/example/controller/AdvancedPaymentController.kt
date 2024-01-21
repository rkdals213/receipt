package com.example.controller

import com.example.application.AdvancedPaymentUseCase
import com.example.application.command.LoadAdvancedPaymentCommand
import com.example.response.AdvancedPaymentResponse
import com.example.response.AdvancedPaymentResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/advanced-payment")
class AdvancedPaymentController(
    private val advancedPaymentUseCase: AdvancedPaymentUseCase
) {

    @GetMapping("/{paymentId}")
    fun loadAdvancedPayment(@PathVariable paymentId: String): ResponseEntity<AdvancedPaymentResponses> {
        val advancedPayments = advancedPaymentUseCase.loadAdvancedPayment(
            LoadAdvancedPaymentCommand(paymentId)
        ).map {
            AdvancedPaymentResponse(
                id = it.id,
                paymentId = it.paymentId,
                advancedPaymentId = it.advancedPaymentId,
                depositId = it.depositId,
                amount = it.amount,
                replacedAmount = it.replacedAmount,
                advancedPaymentStatus = it.advancedPaymentStatus
            )
        }

        return ResponseEntity.ok().body(AdvancedPaymentResponses(advancedPayments))
    }
}
