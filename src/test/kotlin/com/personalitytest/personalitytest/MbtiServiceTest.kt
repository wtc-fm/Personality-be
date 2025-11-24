package com.personalitytest.personalitytest

import com.personalitytest.personalitytest.domain.User
import com.personalitytest.personalitytest.repository.UserRepository
import com.personalitytest.personalitytest.service.MbtiService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test

class MbtiServiceTest {

    private val userRepository: UserRepository = mock()
    private val mbtiService = MbtiService(userRepository)

    @Test
    @DisplayName("MBTI가 저장된다")
    fun saveMbtiTest() {
        val user = User(email = "test@test.com", password = "1234")

        whenever(userRepository.save(any())).thenAnswer { it.arguments[0] }

        mbtiService.saveMbti(user, "INTJ")

        assertThat(user.personalityJson).isEqualTo("INTJ")
    }

    @Test
    @DisplayName("MBTI가 조회된다")
    fun getMbtiTest() {
        val user = User(
            email = "test@test.com",
            password = "1234",
            personalityJson = "ENFP"
        )

        whenever(userRepository.findById(any())).thenReturn(java.util.Optional.of(user))

        val result = mbtiService.getMbti(user)

        assertThat(result).isEqualTo("ENFP")
    }
}