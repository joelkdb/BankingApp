package fr.utbm.bindoomobile.ui.core.extensions

import java.util.Locale

fun String.splitStringWithDivider(
    groupCharCount: Int = 4,
    divider: Char = ' '
): String {
    val formattedStringBuilder = StringBuilder()
    var count = 0

    for (char in this) {
        if (count > 0 && count % groupCharCount == 0) {
            formattedStringBuilder.append(divider)
        }
        formattedStringBuilder.append(char)
        count++
    }

    return formattedStringBuilder.toString()
}

fun Float.floatToString(): String {
    return if (this == this.toInt().toFloat()) {
        String.format(Locale.getDefault(), "%d", this.toInt())
    } else {
        this.toString()
    }
}

fun String.maskCardId(visibleCharacters: Int = 4): String {
    return "*" + substring(length - visibleCharacters)
}