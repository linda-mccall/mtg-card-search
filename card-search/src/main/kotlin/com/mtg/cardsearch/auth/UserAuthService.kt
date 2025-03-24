package com.mtg.cardsearch.auth

import com.mtg.cardsearch.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAuthService(
        private val repository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        // Create a method in your repo to find a user by its username
        val user = repository.findFirstByEmail(username)
        return UserSecurity(
                user.id.toString(),
                user.email,
                user.password,
                Collections.singleton(SimpleGrantedAuthority("user"))
        )
    }
}
