package com.accessibilitytraining.android.helper

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.accessibilitytraining.android.extension.getResString

internal class AccessibilityHelper {
    internal data class Builder(
        private val contentDescription: String? = null,
        private val clickActionDescription: String? = null,
        private val className: String? = null,
        private val getChecked: (() -> Boolean)? = null,
        private val isCheckable: Boolean? = null,
        private val isHeading: Boolean? = null,
        private val traversalAfter: View? = null,
        private val traversalBefore: View? = null
    ) {
        fun buildDelegateTo(view: View?) {
            view ?: return
            ViewCompat.setAccessibilityDelegate(
                view,
                object : AccessibilityDelegateCompat() {
                    override fun onInitializeAccessibilityNodeInfo(
                        host: View?,
                        info: AccessibilityNodeInfoCompat?
                    ) {
                        super.onInitializeAccessibilityNodeInfo(host, info)
                        clickActionDescription?.let {
                            val clickAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                AccessibilityNodeInfo.ACTION_CLICK, it
                            )
                            info?.addAction(clickAction)
                        }
                        className?.let { info?.className = it }
                        isCheckable?.let { info?.isCheckable = it }
                        isHeading?.let { info?.isHeading = it }
                        getChecked?.let { info?.isChecked = it() }
                        traversalAfter?.let { info?.setTraversalAfter(it) }
                        traversalBefore?.let { info?.setTraversalBefore(it) }
                    }

                    override fun onInitializeAccessibilityEvent(
                        host: View?,
                        event: AccessibilityEvent?
                    ) {
                        super.onInitializeAccessibilityEvent(host, event)
                        getChecked?.let { event?.isChecked = it() }
                    }
                }
            )

            contentDescription?.let {
                view.contentDescription = it
            }
        }
    }
}

internal fun View.asButton() {
    buildAccessibilityDelegate(className = Button::class.java.name)
}

internal fun View.asHeading() {
    buildAccessibilityDelegate(isHeading = true)
}

internal fun View.buildAccessibilityDelegate(
    @StringRes contentDescriptionRes: Int? = null,
    contentDescription: String? = null,
    @StringRes clickActionDescriptionRes: Int? = null,
    clickActionDescription: String? = null,
    className: String? = null,
    getChecked: (() -> Boolean)? = null,
    isCheckable: Boolean? = null,
    isHeading: Boolean? = null,
    traversalAfter: View? = null,
    traversalBefore: View? = null
) {
    AccessibilityHelper.Builder(
        contentDescription = context?.getResString(contentDescriptionRes, contentDescription),
        clickActionDescription = context?.getResString(
            clickActionDescriptionRes,
            clickActionDescription
        ),
        className = className,
        getChecked = getChecked,
        isCheckable = isCheckable,
        isHeading = isHeading,
        traversalAfter = traversalAfter,
        traversalBefore = traversalBefore
    ).buildDelegateTo(this)
}

internal fun Context?.isAccessibilityEnable(): Boolean {
    return (this?.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager)?.isEnabled == true
}

internal fun Context?.isTouchExplorationEnable(): Boolean {
    return (this?.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager)?.isTouchExplorationEnabled == true
}

internal fun TextView.disableDynamicFontSize() {
    val sp = textSize / context.resources.displayMetrics.scaledDensity
    setTextSize(TypedValue.COMPLEX_UNIT_DIP, sp)
}