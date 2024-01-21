package com.example.`in`

import com.example.port.`in`.ReceiptUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/receipt")
class ReceiptController(
    private val receiptUseCase: ReceiptUseCase
) {

    @PostMapping("/by/deposit")
    fun receiptByDeposit() {

    }
}
