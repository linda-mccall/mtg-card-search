package com.mtg.cardsearch.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.dto.SaveCardDto
import com.mtg.cardsearch.entity.User
import com.mtg.cardsearch.repository.ListRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class SavedCardControllerTest @Autowired constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper, val listRepository: ListRepository
) {

    val testUsername = "integration@test.com"
    val testPassword = "Password123!"
    var bearer = ""
    val authenticationHeaderName = "Authorization"
    val gson = Gson()

    fun setBearer() {
        val loginDto = LoginDto(testUsername, testPassword)

        val loginResponse = mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginDto)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        bearer = "Bearer " + loginResponse.response.getHeaderValue(authenticationHeaderName)
    }

    fun getUser() : User? {
        setBearer()
        val userResponse = mockMvc.get("/private/users") {
            contentType = MediaType.APPLICATION_JSON
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()
        return gson.fromJson(userResponse.response.contentAsString, User::class.java)
    }

    @Test
    fun saveCardToListThenRemove() {

        val user = getUser()
        val list = listRepository.findByUser(user!!)
        val saveCardDto = SaveCardDto(list.first().id!!, 1)

        mockMvc.post("/private/saved-cards") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(saveCardDto)
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        val deleteUrl = "/private/saved-cards?listId=" + saveCardDto.listId + "&cardId=1"
        mockMvc.delete(deleteUrl) {
            contentType = MediaType.APPLICATION_JSON
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()
    }

}
