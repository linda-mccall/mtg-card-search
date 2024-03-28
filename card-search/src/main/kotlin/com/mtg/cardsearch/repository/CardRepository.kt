package com.mtg.cardsearch.repository

import com.mtg.cardsearch.entity.Card
import com.mtg.cardsearch.entity.CardRuling
import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.entity.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CardRepository : CrudRepository<Card, Int> {
        @Query(value = "SELECT * FROM public.card WHERE ?1 % ANY(STRING_TO_ARRAY(public.card.name,' '))", nativeQuery = true)
        fun findAllMatching(partialText: String): Iterable<Card>
}

