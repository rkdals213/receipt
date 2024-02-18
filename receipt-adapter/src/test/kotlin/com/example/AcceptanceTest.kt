package com.example

import com.example.infrastructure.AccountingClient
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AcceptanceTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup,
) {
    @LocalServerPort
    var port = 0

    @BeforeEach
    fun setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port
            databaseCleanup.afterPropertiesSet()
        }
        databaseCleanup.execute()
    }
}

@Component
@Primary
class FakeAccountingClient: AccountingClient {
    override fun accountingTreatment() {
        println("fake account client")
    }
}
