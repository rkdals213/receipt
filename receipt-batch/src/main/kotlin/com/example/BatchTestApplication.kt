package com.example

import com.example.entity.ReceiptTargetEntity
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import kotlin.system.exitProcess

@SpringBootApplication
class BatchTestApplication

fun main(args: Array<String>) {
    val exitCode = try {
        SpringApplication.exit(runApplication<BatchTestApplication>(*args))
    } catch (e: Exception) {
        5
    }

    exitProcess(exitCode)
}

@Component
@Profile("local")
class InitService(
    private val init: Init
) {

    @PostConstruct
    fun init() {
        init.init()
    }

    companion object {
        @Component
        @Transactional
        class Init(
            private val entityManager: EntityManager,
        ) {
            fun init() {
                for (i in 1..100) {
                    val paymentId = "P" + "$i".padStart(9, '0')
                    val sr001 = ReceiptTargetEntity(
                        billSequenceId = "202401${paymentId}",
                        contractId = paymentId.replace('P', 'C'),
                        paymentId = paymentId,
                        customerId = "100000000000000",
                        serviceCode = "SR001",
                        amount = BigDecimal.valueOf(20_000L),
                        receiptAmount = BigDecimal.ZERO,
                    )

                    val sr002 = ReceiptTargetEntity(
                        billSequenceId = "202401${paymentId}",
                        contractId = paymentId.replace('P', 'C'),
                        paymentId = paymentId,
                        customerId = "100000000000000",
                        serviceCode = "SR002",
                        amount = BigDecimal.valueOf(1_800),
                        receiptAmount = BigDecimal.ZERO,
                    )

                    val sr144 = ReceiptTargetEntity(
                        billSequenceId = "202401${paymentId}",
                        contractId = paymentId.replace('P', 'C'),
                        paymentId = paymentId,
                        customerId = "100000000000000",
                        serviceCode = "SR144",
                        amount = BigDecimal.valueOf(-2_000),
                        receiptAmount = BigDecimal.ZERO,
                    )

                    entityManager.persist(sr001)
                    entityManager.persist(sr002)
                    entityManager.persist(sr144)
                }
            }
        }
    }
}
