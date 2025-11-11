package com.personalitytest.personalitytest.global.error

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    // Auth
    EMAIL_ALREADY_EXISTS(400, "이미 존재하는 이메일입니다."),
    INVALID_LOGIN(400, "이메일 또는 비밀번호가 잘못되었습니다."),
    UNAUTHORIZED(401, "인증 정보가 유효하지 않습니다."),

    // User
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),

    // Common
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.")
}