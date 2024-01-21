package com.example.cleanarchitecture

import com.google.common.base.CaseFormat
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional


@Service
@ActiveProfiles("test")
class DatabaseCleanup(
    @PersistenceContext
    private val entityManager: EntityManager
) : InitializingBean {
    private var tableNames: List<String>? = null

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities
            .filter { it.javaType.getAnnotation(Entity::class.java) != null }
            .map { it -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, it.name) }
            .toList()
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        for (tableName in tableNames!!) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}