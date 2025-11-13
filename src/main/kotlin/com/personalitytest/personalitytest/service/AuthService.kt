package com.personalitytest.personalitytest.service

import com.personalitytest.personalitytest.domain.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.personalitytest.personalitytest.dto.SignupRequest
import com.personalitytest.personalitytest.global.error.CustomException
import com.personalitytest.personalitytest.global.error.ErrorCode
import com.personalitytest.personalitytest.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    private val encoder = BCryptPasswordEncoder()

    fun signup(req: SignupRequest) {
        if (userRepository.existsByEmail(req.email)) {
            throw CustomException(ErrorCode.EMAIL_ALREADY_EXISTS)
        }

        val user = User(
            email = req.email,
            password = encoder.encode(req.password)
        )
        userRepository.save(user)
    }
}