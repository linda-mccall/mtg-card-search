package com.mtg.cardsearch.repository

import com.mtg.cardsearch.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
        fun findFirstByEmail(email: String): User

        @Modifying
        @Transactional
        fun deleteByEmail(email: String)
}

