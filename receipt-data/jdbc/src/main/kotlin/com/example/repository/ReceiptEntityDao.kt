package com.example.repository

import com.example.entity.ReceiptEntity
import org.springframework.data.repository.CrudRepository

interface ReceiptEntityDao : CrudRepository<ReceiptEntity, Long> {
}
