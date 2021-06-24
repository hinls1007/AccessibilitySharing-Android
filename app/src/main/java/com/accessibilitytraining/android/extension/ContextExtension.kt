package com.accessibilitytraining.android.extension

import android.content.Context
import androidx.annotation.StringRes

fun Context.getResString(
    @StringRes resId: Int?,
    text: String?
): String? {
    return when {
        resId != null -> {
            getString(resId)
        }
        text != null -> {
            text
        }
        else -> {
            null
        }
    }
}

fun Context.getResString(
    @StringRes resId: Int?,
    text: CharSequence?
): CharSequence? {
    return when {
        resId != null -> {
            getString(resId)
        }
        text != null -> {
            text
        }
        else -> {
            null
        }
    }
}