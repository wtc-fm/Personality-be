package com.personalitytest.personalitytest.global.error

class CustomException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)
