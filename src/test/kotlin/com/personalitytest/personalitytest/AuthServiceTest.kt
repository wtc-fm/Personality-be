package com.personalitytest.personalitytest

import com.personalitytest.personalitytest.domain.User
import com.personalitytest.personalitytest.dto.LoginRequest
import com.personalitytest.personalitytest.dto.SignupRequest
import com.personalitytest.personalitytest.repository.UserRepository
import com.personalitytest.personalitytest.service.AuthService
import com.personalitytest.personalitytest.service.JwtTokenProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder

import kotlin.test.Test

class AuthServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var encoder: PasswordEncoder
    private lateinit var jwtProvider: JwtTokenProvider
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        userRepository = mock()
        encoder = mock()
        jwtProvider = JwtTokenProvider("ab13412536445d6sz3g54w635e5d4fd6s54q21fbg5f45g6e4e5")

        authService = AuthService(userRepository, encoder, jwtProvider)
    }


    @Test
    @DisplayName("회원가입이 동작한다")
    fun signupTest() {
        val req = SignupRequest(
            email = "test@test.com",
            password = "pass"
        )

        whenever(userRepository.existsByEmail(req.email)).thenReturn(false)
        whenever(encoder.encode(req.password)).thenReturn("PASS")
        whenever(userRepository.save(any())).thenAnswer { it.arguments[0] }

        val saved = authService.signup(req)

        assertThat(saved.email).isEqualTo(req.email)
        assertThat(saved.password).isEqualTo("PASS")

        verify(userRepository).save(any())
    }

    @Test
    @DisplayName("로그인이 동작한다")
    fun loginTest() {
        val email = "login@test.com"
        val pw = "1234"

        val savedUser = User(
            email = email,
            password = "password"
        )

        whenever(userRepository.findByEmail(email)).thenReturn(savedUser)
        whenever(encoder.matches(pw, "password")).thenReturn(true)

        val tokenResponse = authService.login(LoginRequest(email, pw))

        val extracted = jwtProvider.getEmail(tokenResponse.token)

        assertThat(extracted).isEqualTo(email)
    }
}