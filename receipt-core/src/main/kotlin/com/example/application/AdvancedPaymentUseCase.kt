package com.example.application

import com.example.application.command.LoadAdvancedPaymentCommand
import com.example.domain.AdvancedPayment
import com.example.repository.LoadAdvancedPayment
import org.springframework.stereotype.Service

interface AdvancedPaymentUseCase {
    fun loadAdvancedPayment(loadAdvancedPaymentCommand: LoadAdvancedPaymentCommand): List<AdvancedPayment>
}

@Service
class AdvancedPaymentService(
    private val loadAdvancedPayment: LoadAdvancedPayment
) : AdvancedPaymentUseCase {
    override fun loadAdvancedPayment(loadAdvancedPaymentCommand: LoadAdvancedPaymentCommand): List<AdvancedPayment> {
        return loadAdvancedPayment.byPaymentId(loadAdvancedPaymentCommand.paymentId)
    }
}
