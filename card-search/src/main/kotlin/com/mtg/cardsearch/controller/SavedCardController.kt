package com.mtg.cardsearch.controller

import com.mtg.cardsearch.dto.SaveCardDto
import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.exception.UnauthorizedException
import com.mtg.cardsearch.model.response.CardResponse
import com.mtg.cardsearch.repository.ListRepository
import com.mtg.cardsearch.service.CardService
import com.mtg.cardsearch.service.ListService
import com.mtg.cardsearch.util.JwtTokenUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class SavedCardController constructor(private val listService: ListService, private val jwtTokenUtil: JwtTokenUtil) {

    @PostMapping("/saved-cards")
    fun saveCard(@RequestBody saveCardDto: SaveCardDto, @RequestHeader("Authorization") header: String): Any {
        val token = header.replace("Bearer ","")
        return try {
            val savedList = listService.saveCardToList(saveCardDto ,jwtTokenUtil.getEmail(token))
            if (savedList == null) {
                ResponseEntity.status(404)
            } else {
                ResponseEntity.ok(savedList)
            }
        } catch (ex: UnauthorizedException) {
            ResponseEntity.status(401)
        }
    }

    @DeleteMapping("/saved-cards")
    fun removeCardFromList(@RequestParam(value = "listId") listId: Int, @RequestParam(value = "cardId") cardId: Int, @RequestHeader("Authorization") header: String): Any {
        val token = header.replace("Bearer ","")

        return try {
            val savedList = listService.removeCardFromList(listId, cardId, jwtTokenUtil.getEmail(token))
            if (savedList == null) {
                ResponseEntity.status(404)
            } else {
                ResponseEntity.noContent()
            }
        } catch (ex: UnauthorizedException) {
            ResponseEntity.status(401)
        }
    }
}