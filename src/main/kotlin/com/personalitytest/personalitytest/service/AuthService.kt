package com.personalitytest.personalitytest.service

import com.personalitytest.personalitytest.domain.User
import com.personalitytest.personalitytest.dto.LoginRequest
import com.personalitytest.personalitytest.dto.LoginResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.personalitytest.personalitytest.dto.SignupRequest
import com.personalitytest.personalitytest.global.error.CustomException
import com.personalitytest.personalitytest.global.error.ErrorCode
import com.personalitytest.personalitytest.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    private val encoder = BCryptPasswordEncoder()

    fun signup(req: SignupRequest) {
        if (userRepository.existsByEmail(req.email)) {
            throw CustomException(ErrorCode.EMAIL_ALREADY_EXISTS)
        }

        val user = User(
            email = req.email.trim(),
            password = encoder.encode(req.password.trim())
        )
        userRepository.save(user)
    }

    fun login(req: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(req.email.trim())
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        if (!encoder.matches(req.password.trim(), user.password)) {
            throw CustomException(ErrorCode.INVALID_PASSWORD)
        }

        val token = jwtTokenProvider.createToken(user.email)

        return LoginResponse(
            email = user.email,
            token = token
        )
    }
}
