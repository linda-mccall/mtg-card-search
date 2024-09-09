package com.mtg.cardsearch.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mtg.cardsearch.model.response.CardResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest @Autowired constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper) {

    @Test
    fun searchForCard() {
        val cardNameTypo = "Devi, Empyrial Tactician"
        val cardNameFull = "Derevi, Empyrial Tactician"

        val searchResult = mockMvc.get("/public/cards?searchTerm=$cardNameTypo") {
            contentType = MediaType.APPLICATION_JSON
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        val gson = Gson()

        val itemType = object : TypeToken<List<CardResponse>>() {}.type
        var cards = gson.fromJson<List<CardResponse>>(searchResult.response.contentAsString, itemType)
        assertNotNull(cards)
        assertEquals(cardNameFull, cards[0].name)
    }
}
