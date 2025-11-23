package com.personalitytest.personalitytest.controller

import com.personalitytest.personalitytest.dto.LoginRequest
import com.personalitytest.personalitytest.dto.SignupRequest
import com.personalitytest.personalitytest.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody req: SignupRequest) =
        authService.signup(req)
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest) = authService.login(req)
}
