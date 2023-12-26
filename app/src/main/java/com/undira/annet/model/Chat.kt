package com.undira.annet.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat (
    val id: String,
    val id_chatroom: String,
    val id_user: String,
    val messages: String,
    val message_date: String
)

@Serializable
data class GetChatByRoom(
    val _chatroom_id: String
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
data class GetIdRoomChat(
    val _user_id: String,
    val _opponent_id: String
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

@Serializable
data class Inbox(
    val user_id: String,
    val user_name: String,
    val messages: String,
    val message_date: String
)

@Serializable
data class GetInbox(
    val _user_id: String
)