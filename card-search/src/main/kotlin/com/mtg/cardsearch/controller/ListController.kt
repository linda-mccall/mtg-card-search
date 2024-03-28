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

    @PostMapping("/lists")
    fun createList(@RequestBody listSaveDto: ListSaveDto, @RequestHeader("Authorization") header: String): ResponseEntity<List> {
        val token = header.replace("Bearer ","")
        return ResponseEntity.ok(listService.createList(jwtTokenUtil.getEmail(token), listSaveDto.listName))
    }

    @PutMapping("/lists")
    fun updateList(@RequestBody listSaveDto: ListSaveDto, @RequestHeader("Authorization") header: String): Any {
        val token = header.replace("Bearer ","")
        if (listSaveDto.listId == null) {
            return ResponseEntity.notFound();
        }
        return try {
            ResponseEntity.ok(listService.update(listSaveDto.listId, jwtTokenUtil.getEmail(token), listSaveDto.listName))
        } catch (ex : UnauthorizedException) {
            ResponseEntity.status(401)
        }
    }

    @DeleteMapping("/lists/{id}")
    fun deleteList(@PathVariable(value = "id") id: Int, @RequestHeader("Authorization") header: String): Any {
        val token = header.replace("Bearer ","")
        return try {
            listService.deleteList(id, jwtTokenUtil.getEmail(token))
            ResponseEntity.noContent()
        } catch (ex : UnauthorizedException) {
            ResponseEntity.status(401)
        }
    }
}