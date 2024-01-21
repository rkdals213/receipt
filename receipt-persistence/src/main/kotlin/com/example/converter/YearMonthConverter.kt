package com.example.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.YearMonth

@Converter(autoApply = true)
class YearMonthConverter : AttributeConverter<YearMonth, String> {

    override fun convertToDatabaseColumn(attribute: YearMonth): String {
        return "${attribute.year}${attribute.month.value.toString().padStart(2, '0')}"
    }

    override fun convertToEntityAttribute(dbValue: String): YearMonth {
        return YearMonth.of(dbValue.substring(0, 4).toInt(), dbValue.substring(4, 6).toInt())
    }
}
