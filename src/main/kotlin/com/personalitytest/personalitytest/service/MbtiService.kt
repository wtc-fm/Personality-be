package com.personalitytest.personalitytest.service

import com.personalitytest.personalitytest.domain.User
import com.personalitytest.personalitytest.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MbtiService(
    private val userRepository: UserRepository
) {

    fun saveMbti(user: User, mbti: String) {
        user.personalityJson = mbti
        userRepository.save(user)
    }

    fun getMbti(user: User): String {
        return user.personalityJson ?: "NONE"
    }
}