package com.undira.annet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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

@Serializable
@Parcelize
data class UserPost(
    val name: String
): Parcelable

@Serializable
data class UserSearch(
    val id: String,
    val name: String
)