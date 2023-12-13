package com.undira.annet.view_model.main.notification

import androidx.lifecycle.ViewModel
import com.undira.annet.model.Notification

class ViewModel: ViewModel() {
    val data: ArrayList<Notification> = arrayListOf(
        Notification("Comment", "Hello, World!"),
        Notification("Comment", "Hello, World!"),
        Notification("Comment", "Hello, World!"),
        Notification("Comment", "Hello, World!"),
        Notification("Comment", "Hello, World!"),
        Notification("Comment", "Hello, World!"),
        Notification("Comment", "Hello, World!"),
    )
}