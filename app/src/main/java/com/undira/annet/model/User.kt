package com.undira.annet.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val date: String
)

@Serializable
data class UserInsert(
    val name: String,
    val email: String,
)

@Serializable
data class UserLogin(
    val email: String,
    val password: String
)