package com.undira.annet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class Post(
    val avatar: String,
    val name: String,
    val date: String,
    val content: String,
)

@Serializable
data class PostInsert(
    val id_users: String,
    val content: String
)

@Serializable
@Parcelize
data class PostGetAll(
    val id: String,
    val id_users: String,
    val content: String,
    val date: String,
    val users: UserPost
): Parcelable