package com.mtg.cardsearch.repository

import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.entity.User
import org.springframework.data.repository.CrudRepository

interface ListRepository : CrudRepository<List, Int> {
        fun findByUser(user: User): Iterable<List>
}
