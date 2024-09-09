package com.mtg.cardsearch.service

import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.entity.User
import com.mtg.cardsearch.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository,
                  private val passwordEncoder: BCryptPasswordEncoder) {

    fun createNewUser(loginDto: LoginDto): User {
        return repository.save(User(null, loginDto.email,
                passwordEncoder.encode(loginDto.password), emptySet()))
    }

    fun getUserByEmail(email: String): User {
        return repository.findFirstByEmail(email)
    }

    fun deleteUser(email: String) {
        repository.deleteByEmail(email)
    }
}
