package com.mtg.cardsearch.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.mtg.cardsearch.dto.ListSaveDto
import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.entity.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ListControllerTest @Autowired constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper) {

    val testUsername = "integration@test.com"
    val testPassword = "password"
    var bearer = ""
    val authenticationHeaderName = "Authorization"


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

    @Test
    fun createUpdateThenDeleteList() {
        setBearer()
        val listSaveDto = ListSaveDto(null, Calendar.getInstance().time.toString())
        var gson = Gson()

        val createResult =  mockMvc.post("/private/lists") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(listSaveDto)
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        var createdList = gson?.fromJson(createResult.response.contentAsString, List::class.java)
        Assertions.assertNotNull(createdList?.id ?: null)

        val listSaveUpdateDto = ListSaveDto(createdList!!.id, Calendar.getInstance().time.toString() + 2)
        val updateResponse = mockMvc.put("/private/lists") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(listSaveUpdateDto)
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { is2xxSuccessful() } }
                .andReturn()

        var updatedList = gson?.fromJson(updateResponse.response.contentAsString, List::class.java)
        Assertions.assertEquals(createdList.id, updatedList!!.id)
        Assertions.assertNotEquals(createdList.name, updatedList!!.name)

        mockMvc.delete("/private/lists/" + createdList.id) {
            header(authenticationHeaderName, bearer)
        }
                .andDo { print() }
                .andExpect { status { isNoContent() } }
    }

}
