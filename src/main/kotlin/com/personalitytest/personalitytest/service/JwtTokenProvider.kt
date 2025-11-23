package com.personalitytest.personalitytest.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun createToken(email: String): String {
        val now = Date()
        val expiration = Date(now.time + 1000L * 60 * 60 * 24) // 24시간

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    fun getEmail(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body.subject
    }
}
