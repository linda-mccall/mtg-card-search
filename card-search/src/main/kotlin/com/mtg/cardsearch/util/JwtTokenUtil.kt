package com.mtg.cardsearch.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil {
    private val secret = "0ddf5597e02d981f8803c4cc11f015a4e52679d706edb29b595d9e466def5bcf95273a3053ab5d97ee893c23e4023b912daefaade316406a33b7685d4d223dfa"
    private val expiration = 6000000

    fun generateToken(username: String): String =
            Jwts.builder().setSubject(username).setExpiration(Date(System.currentTimeMillis() + expiration))
                    .signWith(SignatureAlgorithm.HS512, secret.toByteArray()).compact()

    private fun getClaims(token: String) =
            Jwts.parser().setSigningKey(secret.toByteArray()).build().parseSignedClaims(token).payload

    fun getEmail(token: String): String{
        val cleanedToken = token.replace("Bearer ","")
        return getClaims(cleanedToken).subject
    }

    fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token)
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        return now.before(expirationDate)
    }
}
