package com.undira.annet.view_model.main.chat

import androidx.lifecycle.ViewModel
import com.undira.annet.model.Inbox

class ViewModel: ViewModel() {
    val data: ArrayList<Inbox> = arrayListOf(
        Inbox("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon doe", "Hello, World!", "20 jan 2023"),
        Inbox("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon doe", "Hello, World!", "20 jan 2023"),
        Inbox("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon doe", "Hello, World!", "20 jan 2023"),
        Inbox("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon doe", "Hello, World!", "20 jan 2023"),
        Inbox("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon doe", "Hello, World!", "20 jan 2023"),
    )
}