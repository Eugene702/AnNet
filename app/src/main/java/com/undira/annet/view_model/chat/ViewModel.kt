package com.undira.annet.view_model.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.Chat
import com.undira.annet.model.ChatMessagesInsert
import com.undira.annet.model.ChatParticipantsGetIdChatRoom
import com.undira.annet.model.ChatParticipantsInsert
import com.undira.annet.model.ChatRoomCheck
import com.undira.annet.model.GetChatByRoom
import com.undira.annet.model.GetIdRoomChat
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.result.PostgrestResult
import io.github.jan.supabase.postgrest.rpc
class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy { Provider().supabase }

    suspend fun createNewRoom(): PostgrestResult {
        return provider.postgrest.rpc("create_chat_room")
    }

    suspend fun getIdRoom(idUser: String, idUserOpponent: String): String {
        val data = provider.postgrest.rpc("get_chatroom_id", GetIdRoomChat(idUser, idUserOpponent))
        Log.d("Data Id Room", data.data)
        return data.data
    }

    suspend fun checkRoomExists(idUser: String, idUserOpponent: String): Boolean {
        return provider.postgrest.rpc("check_chat_exists", ChatRoomCheck(idUser, idUserOpponent)).data.toBoolean()
    }

    suspend fun addMessage(idRoom: String, idUser: String, message: String): PostgrestResult {
        return provider.from("chat_messages").insert(ChatMessagesInsert(idRoom, idUser, message))
    }

    suspend fun addParticipants(idChatroom: String, idUser: String): PostgrestResult {
        return provider.from("chat_participants").insert(ChatParticipantsInsert(idChatroom, idUser))
    }

    suspend fun getChat(idRoom: String): List<Chat> {
        return provider.postgrest.rpc("get_chat_by_room", GetChatByRoom(idRoom)).decodeList<Chat>()
    }
}