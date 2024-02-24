package com.example

import com.example.entity.ReceiptTargetEntity
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@SpringBootApplication(scanBasePackages = ["com.example"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}

@Component
class InitService(
    private val init: Init
) {

    @PostConstruct
    fun init() {
        init.init()
    }

    @Component
    @Transactional
    class Init(private val em: EntityManager) {
        fun init() {
            for (i in 1..2) {
                val sr001 = ReceiptTargetEntity(
                    billSequenceId = "20240${i}P000000001",
                    contractId = "C000000001",
                    paymentId = "P000000001",
                    customerId = "100000000000000",
                    serviceCode = "SR001",
                    amount = BigDecimal.valueOf(20_000L),
                    receiptAmount = BigDecimal.ZERO,
                )

                val sr002 = ReceiptTargetEntity(
                    billSequenceId = "20240${i}P000000001",
                    contractId = "C000000001",
                    paymentId = "P000000001",
                    customerId = "100000000000000",
                    serviceCode = "SR002",
                    amount = BigDecimal.valueOf(1_800),
                    receiptAmount = BigDecimal.ZERO,
                )

                val sr144 = ReceiptTargetEntity(
                    billSequenceId = "20240${i}P000000001",
                    contractId = "C000000001",
                    paymentId = "P000000001",
                    customerId = "100000000000000",
                    serviceCode = "SR144",
                    amount = BigDecimal.valueOf(-2_000),
                    receiptAmount = BigDecimal.ZERO,
                )

                em.persist(sr001)
                em.persist(sr002)
                em.persist(sr144)
            }
        }
    }
}
