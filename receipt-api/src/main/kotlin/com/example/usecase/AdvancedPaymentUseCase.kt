package com.example.usecase

import com.example.infrastructure.repository.LoadAdvancedPayment
import com.example.domain.AdvancedPayment
import com.example.usecase.command.LoadAdvancedPaymentCommand
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
