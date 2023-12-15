package com.undira.annet.view_model.profile

import androidx.lifecycle.ViewModel
import com.undira.annet.model.Post

class ViewModel: ViewModel() {
    val dataPost: ArrayList<Post> = arrayListOf(
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
    )
}