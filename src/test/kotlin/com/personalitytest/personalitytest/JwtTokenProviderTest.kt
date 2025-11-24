package com.personalitytest.personalitytest

import com.personalitytest.personalitytest.service.JwtTokenProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class JwtTokenProviderTest {

    private val secret =
        "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"

    private val provider = JwtTokenProvider(secret)

    @Test
    @DisplayName("JWT 토큰을 생성하고 이메일을 추출한다")
    fun createAndParseToken() {
        val email = "test@test.com"

        val token = provider.createToken(email)
        val parsed = provider.getEmail(token)

        assertThat(parsed).isEqualTo(email)
    }

    @Test
    @DisplayName("서명이 다른 토큰은 파싱 시 예외가 발생한다")
    fun invalidSignature() {
        val email = "test@test.com"
        val token = provider.createToken(email)

        val otherProvider = JwtTokenProvider(
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        )

        val result = kotlin.runCatching {
            otherProvider.getEmail(token)
        }

        assertThat(result.isFailure).isTrue()
    }
}