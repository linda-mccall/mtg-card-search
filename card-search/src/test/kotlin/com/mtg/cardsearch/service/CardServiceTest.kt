package com.mtg.cardsearch.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mtg.cardsearch.entity.*
import com.mtg.cardsearch.repository.CardRepository
import com.mtg.cardsearch.repository.RulingRepository
import com.mtg.cardsearch.util.JwtTokenUtil
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.AuthenticationManager
import java.util.Collections

@ExtendWith(MockitoExtension::class)
class CardServiceTest {

    val cardRepository = Mockito.mock(CardRepository::class.java)
    val rulingRepository = Mockito.mock(RulingRepository::class.java)

    @Test
    fun searchForCards() {
        val rarity = Rarity(id=3, name = "Rare")
        val type = Type(id = 1, name = "Creature", cards = Collections.emptySet())
        val subType = Subtype(id = 1, name = "Bear", cards = Collections.emptySet())
        val legality = Legality(id = 1, name = "Modern", Collections.emptySet())
        val card = Card(id=1, name = "Kudo, King Among Bears", cardManaCost = 2, digitalOnly = false,
                rarity = rarity, types = Collections.singleton(type), subtypes = Collections.singleton(subType),
                legalities = Collections.singleton(legality), lists = Collections.emptySet()
        )
        val cardRuling = CardRuling(card = card, rulingText = "Kudo, King Among Bears's ability " +
                "overwrites any effects that set a creature's power and toughness. Any existing " +
                "effects or counters that raise, lower, or switch a creature's power and/or toughness " +
                "continue to apply to the creature's newly set power and toughness. Any power- or " +
                "toughness-setting effects that start to apply after Kudo enters the battlefield will overwrite this effect.")
        val cardService = CardService(cardRepository, rulingRepository)
        Mockito.`when`(rulingRepository.findByCard(card)).thenReturn(Collections.singleton(cardRuling))
        Mockito.`when`(cardRepository.findAllMatching(anyString())).thenReturn(Collections.singleton(card))
        val foundCards = cardService.searchForCards("Kudo");
        assertEquals("Kudo, King Among Bears", foundCards[0].name)
        assertEquals(1, foundCards[0].rulings.size)

    }
}
