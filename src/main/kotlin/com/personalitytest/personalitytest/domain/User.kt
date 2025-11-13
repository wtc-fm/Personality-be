package com.personalitytest.personalitytest.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val email: String,

    var password: String,

    @Column(columnDefinition = "TEXT")
    var personalityJson: String? = null
)

