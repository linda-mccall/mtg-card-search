package com.mtg.cardsearch.controller

import com.mtg.cardsearch.model.response.CardResponse
import com.mtg.cardsearch.service.CardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CardController constructor(private val cardService: CardService) {

    @GetMapping("/cards")
    fun cardSearch(@RequestParam(value = "searchTerm") searchTerm: String): ResponseEntity<List<CardResponse>> {
        return ResponseEntity.ok(cardService.searchForCards(searchTerm));
    }
}