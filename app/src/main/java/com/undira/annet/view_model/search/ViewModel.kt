package com.undira.annet.view_model.search

import androidx.lifecycle.ViewModel
import com.undira.annet.model.UserSearch

class ViewModel: ViewModel() {
    val data: ArrayList<UserSearch> = arrayListOf(
        UserSearch("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe"),
        UserSearch("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe"),
        UserSearch("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe"),
        UserSearch("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe"),
        UserSearch("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe"),
        UserSearch("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe"),
    )
}
