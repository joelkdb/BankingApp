package fr.utbm.bindoomobile.ui.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.getFormattedDate(format: String): String {
    val formatter = SimpleDateFormat(format, Locale.US)
    return formatter.format(this)
}