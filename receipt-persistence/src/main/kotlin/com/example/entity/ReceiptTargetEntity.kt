package com.example.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.YearMonth

@Entity
class ChargeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val billTargetYearMonth: YearMonth,

    val contractId: String,

    val paymentId: String,

    val customerId: String,

    val serviceCode: String,

    val amount: BigDecimal,

    var receiptAmount: BigDecimal,
)

@Converter(autoApply = true)
class YearMonthConverter : AttributeConverter<YearMonth, String> {

    override fun convertToDatabaseColumn(attribute: YearMonth): String {
        return "${attribute.year}${attribute.month}"
    }

    override fun convertToEntityAttribute(dbValue: String): YearMonth {
        return YearMonth.of(dbValue.substring(0, 4).toInt(), dbValue.substring(4, 6).toInt())
    }
}
