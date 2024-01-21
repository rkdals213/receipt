package com.example.repository

import com.example.entity.DepositEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DepositEntityRepository : JpaRepository<DepositEntity, Long> {
}
