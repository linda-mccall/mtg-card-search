package com.mtg.cardsearch.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.mtg.cardsearch.dto.LoginDto
import com.mtg.cardsearch.util.JwtTokenUtil
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

@ExtendWith(MockitoExtension::class)
class JwtAuthenticationFilterTest {

    val jwtTokenUtil = mock(JwtTokenUtil::class.java)
    val authenticationManager = mock(AuthenticationManager::class.java)
    val objectMapper = mock(ObjectMapper::class.java)

    @Test
    fun attemptAuthentication() {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(jwtTokenUtil, authenticationManager, objectMapper)
        val loginDto = LoginDto("email@email.com", "password")
        val httpServletRequest = mock(HttpServletRequest::class.java)
        val httpServletResponse = mock(HttpServletResponse::class.java)
        val servletInputStream = mock(ServletInputStream::class.java)
        val authentication = mock(Authentication::class.java)
        `when`(httpServletRequest.inputStream).thenReturn(servletInputStream)
        `when`(objectMapper.readValue(any(ServletInputStream::class.java), eq(LoginDto::class.java))).thenReturn(loginDto)
        `when`(authenticationManager.authenticate(any())).thenReturn(authentication)
        assertEquals(authentication, jwtAuthenticationFilter.attemptAuthentication(httpServletRequest, httpServletResponse))
    }
}
