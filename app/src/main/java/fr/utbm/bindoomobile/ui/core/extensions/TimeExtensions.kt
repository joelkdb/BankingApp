package fr.utbm.bindoomobile.ui.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.getFormattedDate(format: String): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(this)
}

fun String.getFormattedDate(format: String): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(this)
}

fun String.convertDateToMillis(): Long {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(this)
    return date?.time ?: 0L
}