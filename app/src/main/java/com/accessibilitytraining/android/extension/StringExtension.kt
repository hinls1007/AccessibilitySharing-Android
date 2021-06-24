package com.accessibilitytraining.android.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDateContentDescription(
    formatString: String = "dd/MM/yyyy"
): String? {
    return try {
        val date = SimpleDateFormat(formatString, Locale.ENGLISH).parse(this) ?: return null
        SimpleDateFormat("dd/MMMM/yyyy", Locale.ENGLISH).format(date)
    } catch (e: ParseException) {
        null
    }
}
