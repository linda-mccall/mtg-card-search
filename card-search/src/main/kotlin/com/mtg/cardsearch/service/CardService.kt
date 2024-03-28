package com.mtg.cardsearch.service

import com.mtg.cardsearch.entity.Card
import com.mtg.cardsearch.model.response.CardResponse
import com.mtg.cardsearch.repository.CardRepository
import com.mtg.cardsearch.repository.RulingRepository
import org.springframework.stereotype.Service

@Service
class CardService(private val cardRepository: CardRepository, private val rulingRepository: RulingRepository) {

    fun searchForCards(searchTerm: String): List<CardResponse> {
        val cards = cardRepository.findAllMatching(searchTerm)

        val cardResponses = cards.map {
            mapCardToCardResponse(it)
        }
        return cardResponses;
    }

    private fun mapCardToCardResponse(card: Card) : CardResponse {
        return CardResponse(
                name = card.name,
                text = card.text,
                cardManaCost = card.cardManaCost,
                power = card.power,
                toughness = card.toughness,
                rarity = card.rarity.name,
                types = card.types.map { it.name },
                subtypes = card.subtypes.map { it.name },
                legalities = card.legalities.map { it.name },
                rulings = rulingRepository.findByCard(card).map { it.rulingText }
        )
    }
}