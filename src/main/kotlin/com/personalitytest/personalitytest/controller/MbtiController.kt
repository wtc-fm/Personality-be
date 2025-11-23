package com.personalitytest.personalitytest.controller

import com.personalitytest.personalitytest.domain.User
import com.personalitytest.personalitytest.dto.MbtiRequest
import com.personalitytest.personalitytest.dto.MbtiResponse
import com.personalitytest.personalitytest.repository.UserRepository
import com.personalitytest.personalitytest.service.MbtiService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class MbtiController(
    private val userRepository: UserRepository,
    private val mbtiService: MbtiService
) {
    @PostMapping("/mbti")
    fun saveMbti(
        @AuthenticationPrincipal user: User,
        @RequestBody req: MbtiRequest
    ): ResponseEntity<String> {
        mbtiService.saveMbti(user, req.mbti)
        return ResponseEntity.ok("saved")
    }

    @GetMapping("/mbti")
    fun getMbti(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<MbtiResponse> {
        return ResponseEntity.ok(MbtiResponse(mbtiService.getMbti(user)))
    }

}