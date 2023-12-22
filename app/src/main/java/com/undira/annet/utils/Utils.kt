package com.undira.annet.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun convertTimestampToDate(timeStamp: String): String? {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault()).parse(timeStamp)
    return date?.let { SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(it) }
}