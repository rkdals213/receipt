package com.example.repository

import com.example.entity.DepositEntity
import org.springframework.data.repository.CrudRepository

interface DepositEntityDao : CrudRepository<DepositEntity, Long> {
}
