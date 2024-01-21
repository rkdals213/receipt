package com.example.out.persistence.repository

import com.example.out.persistence.entity.ReceiptTargetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReceiptTargetEntityRepository : JpaRepository<ReceiptTargetEntity, Long> {
}
