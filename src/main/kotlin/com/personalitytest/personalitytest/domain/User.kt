package com.personalitytest.personalitytest.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class User(
    @Id val id: String = UUID.randomUUID().toString(),
    val nickname: String
)
