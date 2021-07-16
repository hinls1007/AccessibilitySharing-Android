package com.accessibilitytraining.android.builder

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK
import android.widget.Button
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

class AccessibilityBuilder {
    enum class ViewType {
        Heading,
        Button,
    }

    var viewType: ViewType? = null

    //Click action label [Double tap to %s]
    var clickActionLabel: String? = null

    //Replace content description
    var text: String? = null


    fun setViewType(viewType: ViewType): AccessibilityBuilder {
        this.viewType = viewType
        return this
    }

    fun setClickActionLabel(label: String): AccessibilityBuilder {
        this.clickActionLabel = label
        return this
    }

    fun setText(text: String): AccessibilityBuilder {
        this.text = text
        return this
    }

    fun build(view: View) {
        ViewCompat.setAccessibilityDelegate(view, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfoCompat?
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                if (info == null) return

                viewType?.let {
                    when (it) {
                        ViewType.Heading -> info.isHeading = true
                        ViewType.Button -> info.className = Button::class.java.name
                    }
                }

                clickActionLabel?.let {
                    info.addAction(
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            ACTION_CLICK,
                            clickActionLabel
                        )
                    )
                }

                text?.let {
                    info.text = it
                }

            }
        })
    }
}