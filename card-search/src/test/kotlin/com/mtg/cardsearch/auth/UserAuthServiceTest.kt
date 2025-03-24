package com.mtg.cardsearch.auth

import com.mtg.cardsearch.entity.User
import com.mtg.cardsearch.repository.UserRepository
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserAuthServiceTest {

    val repository = Mockito.mock(UserRepository::class.java)

    @Test
    fun loadUserByUsername() {
        val user = User(1, "email@email.com", "password", Collections.emptySet())
        Mockito.`when`(repository.findFirstByEmail(anyString())).thenReturn(user)
        val userAuthService = UserAuthService(repository)
        val userSecurity = userAuthService.loadUserByUsername("email@email.com")
        assertEquals("password", userSecurity.password)
    }
}
