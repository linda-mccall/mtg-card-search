package com.mtg.cardsearch.controller

import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.entity.User
import com.mtg.cardsearch.service.UserService
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
class UserController constructor(private val userService: UserService) {

    @PostMapping("public/users")
    fun register(@RequestBody loginDto: LoginDto): ResponseEntity<User> {
        val user = userService.createNewUser(loginDto)
        return ResponseEntity.ok(user)
    }

    @GetMapping("private/users")
    fun getUser(): Any {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        if (authentication.isAuthenticated) {
            return ResponseEntity.ok(userService.getUserByEmail(authentication.name))
        }
        return ResponseEntity.status(401).body(null)
    }

    @DeleteMapping("private/users")
    fun deleteUser() : Any {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        if (authentication.isAuthenticated) {
            userService.deleteUser(authentication.name)
            return ResponseEntity.status(204).body(null)
        }
        return ResponseEntity.status(401).body(null)
    }
}
