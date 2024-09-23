package com.mtg.cardsearch.controller

import com.mtg.cardsearch.dto.ListSaveDto
import com.mtg.cardsearch.entity.List
import com.mtg.cardsearch.exception.UnauthorizedException
import com.mtg.cardsearch.service.ListService
import com.mtg.cardsearch.util.JwtTokenUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ListController constructor(private val listService: ListService, private val jwtTokenUtil: JwtTokenUtil) {

    @PostMapping("private/lists")
    fun createList(@RequestBody listSaveDto: ListSaveDto, @RequestHeader("Authorization") authHeader: String): ResponseEntity<List> {
        return ResponseEntity.ok(listService.createList(jwtTokenUtil.getEmail(authHeader), listSaveDto.listName))
    }

    @PutMapping("private/lists")
    fun updateList(@RequestBody listSaveDto: ListSaveDto, @RequestHeader("Authorization") authHeader: String): Any {
        if (listSaveDto.listId == null) {
            return ResponseEntity.notFound()
        }
        return try {
            ResponseEntity.ok(listService.update(listSaveDto.listId, jwtTokenUtil.getEmail(authHeader), listSaveDto.listName))
        } catch (ex : UnauthorizedException) {
            ResponseEntity.status(401)
        }
    }

    @DeleteMapping("private/lists/{id}")
    fun deleteList(@PathVariable(value = "id") id: Int,
                   @RequestHeader("Authorization") authHeader: String) : Any {
        try {
            listService.deleteList(id, jwtTokenUtil.getEmail(authHeader))
        } catch (ex : UnauthorizedException) {
            return ResponseEntity.status(401)
        }
        return ResponseEntity.status(204).body(null)
    }
}
