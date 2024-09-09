package com.mtg.cardsearch.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.entity.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.Calendar

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper) {

    @Test
    fun registerThenDeleteUser() {
        val loginDto = LoginDto(Calendar.getInstance().time.toString() + "linda.fenton@unosquare.com", "Password123!")
        var gson = Gson()
        val registerResult = mockMvc.post("/public/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginDto)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        var registeredUser = gson?.fromJson(registerResult.response.contentAsString, User::class.java)

        val loginResponse = mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginDto)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        val authenticationHeaderName = "Authorization"
        val bearer = "Bearer " + loginResponse.response.getHeaderValue(authenticationHeaderName);

        val userResponse = mockMvc.get("/private/users") {
            contentType = MediaType.APPLICATION_JSON
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        mockMvc.delete("/private/users") {
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { isNoContent() } }
    }
}
