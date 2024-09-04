package com.mtg.cardsearch.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.mtg.cardsearch.auth.JwtAuthenticationFilter
import com.mtg.cardsearch.auth.JwtAuthorizationFilter
import com.mtg.cardsearch.util.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfiguration (private val userDetailsService: UserDetailsService,
                             private val objectMapper: ObjectMapper) {
    private val jwtToken = JwtTokenUtil()

    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailsService)
        return authenticationManagerBuilder.build()
    }

    @Order(2)
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)

        return http
                .authorizeHttpRequests {
                    it.requestMatchers(HttpMethod.POST, "/login").permitAll()
                    it.anyRequest().authenticated()}
                .authenticationManager(authenticationManager)
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager, objectMapper))
                .addFilter(JwtAuthorizationFilter(jwtToken, userDetailsService, authenticationManager))
                .csrf { it.disable()}
                .build()
    }

    @Order(1)
    @Bean
    fun publicSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .securityMatcher("/public/**", "/swagger-ui/**", "/v3/api-docs*/**")
                .authorizeHttpRequests {
                    it.requestMatchers(HttpMethod.POST, "/public/users/**").permitAll()
                    it.requestMatchers(HttpMethod.GET, "/public/cards/**").permitAll()
                    it.requestMatchers("/swagger-ui/**", "/v3/api-docs*/**").permitAll()
                    it.anyRequest().authenticated()}
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .csrf { it.disable()}

                .build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
