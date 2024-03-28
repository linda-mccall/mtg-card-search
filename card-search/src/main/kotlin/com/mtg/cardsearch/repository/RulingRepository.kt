package com.mtg.cardsearch.repository

import com.mtg.cardsearch.entity.Card
import com.mtg.cardsearch.entity.CardRuling
import com.mtg.cardsearch.entity.Legality
import org.springframework.data.repository.CrudRepository

interface RulingRepository : CrudRepository<CardRuling, Int> {
        fun findByCard(card: Card): Iterable<CardRuling>
}

