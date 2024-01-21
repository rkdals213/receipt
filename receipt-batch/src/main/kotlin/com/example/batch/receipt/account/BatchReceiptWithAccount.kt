package com.example.batch.receipt.account

import com.example.consts.Bank
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import java.math.BigDecimal

data class BatchReceiptWithAccount(
    val paymentId: String,
    val amount: BigDecimal,
    val accountNumber: String,
    val bank: Bank
)

class BatchReceiptWithAccountMapper : FieldSetMapper<BatchReceiptWithAccount> {
    override fun mapFieldSet(fieldSet: FieldSet): BatchReceiptWithAccount {
        return BatchReceiptWithAccount(
            paymentId = fieldSet.readString(0),
            amount = BigDecimal.valueOf(fieldSet.readLong(1)),
            accountNumber = fieldSet.readString(2),
            bank = Bank.valueOf(fieldSet.readString(3)),
        )
    }
}
