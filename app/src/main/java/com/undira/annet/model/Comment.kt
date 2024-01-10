package com.undira.annet.model

import kotlinx.serialization.Serializable

data class Comment(
    val id: String,
    val avatar: String,
    val date: String,
    val name: String,
    val comment: String
)

@Serializable
data class CommentInput(
    val id_post: String,
    val id_user: String,
    val comment: String
)

@Serializable
data class CommentList(
    val id: Int,
    val id_post: String,
    val id_user: String,
    val comment: String,
    val date: String,
    val users: UserPost
)