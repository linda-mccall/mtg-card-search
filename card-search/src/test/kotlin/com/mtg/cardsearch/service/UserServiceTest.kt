package com.mtg.cardsearch.service

import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.entity.User
import com.mtg.cardsearch.repository.CardRepository
import com.mtg.cardsearch.repository.RulingRepository
import com.mtg.cardsearch.repository.UserRepository
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    val userRepository = Mockito.mock(UserRepository::class.java)
    val passwordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)

    @Test
    fun createNewUser() {
        val user = User(id = 1, email = "email@email.com", password = "???", lists = Collections.emptySet())
        Mockito.`when`(passwordEncoder.encode(any())).thenReturn("???")
        Mockito.`when`(userRepository.save(any())).thenReturn(user)
        val userService = UserService(userRepository, passwordEncoder)
        userService.createNewUser(LoginDto("email@email.com", "???"))
        verify(userRepository, times(1)).save(any())
    }

    @Test
    fun deleteUser() {
        val userService = UserService(userRepository, passwordEncoder)
        userService.deleteUser("email@email.com")
        verify(userRepository, times(1)).deleteByEmail("email@email.com")
    }
}
