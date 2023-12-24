package com.undira.annet.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat (
    val id: String,
    val id_chatroom: String,
    val id_user: String,
    val messages: String,
    val date: String
)

@Serializable
data class ChatParticipants(
    val id: String,
    val id_chatroom: String,
    val id_user: String,
    val date: String
)

@Serializable
data class ChatParticipantsGetIdChatRoom(
    val id_chatroom: String
)

@Serializable
data class ChatRoomCheck(
    val user1: String,
    val user2: String
)

@Serializable
data class ChatRoomInserts(
    val id: String?,
    val date: String?
)

@Serializable
data class ChatMessagesInsert(
    val id_chatroom: String,
    val id_user: String,
    val messages: String
)

@Serializable
data class ChatParticipantsInsert(
    val id_chatroom: String,
    val id_user: String
)