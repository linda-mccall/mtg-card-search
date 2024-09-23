package com.mtg.cardsearch.controller

import com.mtg.cardsearch.dto.SaveCardDto
import com.mtg.cardsearch.exception.UnauthorizedException
import com.mtg.cardsearch.service.ListService
import com.mtg.cardsearch.util.JwtTokenUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SavedCardController constructor(private val listService: ListService, private val jwtTokenUtil: JwtTokenUtil) {

    @PostMapping("private/saved-cards")
    fun saveCard(@RequestBody saveCardDto: SaveCardDto, @RequestHeader("Authorization") authHeader: String): Any {
        return try {
            val savedList = listService.saveCardToList(saveCardDto ,jwtTokenUtil.getEmail(authHeader))
            if (savedList == null) {
                ResponseEntity.status(404).body(null)
            } else {
                ResponseEntity.ok(savedList)
            }
        } catch (ex: UnauthorizedException) {
            ResponseEntity.status(401).body(null)
        }
    }

    @DeleteMapping("private/saved-cards")
    fun removeCardFromList(@RequestParam(value = "listId") listId: Int, @RequestParam(value = "cardId") cardId: Int, @RequestHeader("Authorization") authHeader: String): Any {
        return try {
            val savedList = listService.removeCardFromList(listId, cardId, jwtTokenUtil.getEmail(authHeader))
            if (savedList == null) {
                ResponseEntity.status(404).body(null)
            } else {
                ResponseEntity.status(204).body(null)
            }
        } catch (ex: UnauthorizedException) {
            ResponseEntity.status(401).body(null)
        }
    }
}
