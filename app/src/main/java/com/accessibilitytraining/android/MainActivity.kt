package com.accessibilitytraining.android

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.accessibilitytraining.android.databinding.ActivityMainBinding
import com.accessibilitytraining.android.extension.getResString
import com.accessibilitytraining.android.helper.buildAccessibilityDelegate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun setActionBarData(
        @DrawableRes leftButtonIconRes: Int? = null,
        @StringRes leftButtonContentDescriptionRes: Int? = null,
        leftButtonContentDescription: String? = null,
        leftButtonClickListener: View.OnClickListener? = null,
        @StringRes titleRes: Int? = null,
        title: CharSequence? = null,
        @DrawableRes rightButtonIconRes: Int? = null,
        @StringRes rightButtonContentDescriptionRes: Int? = null,
        rightButtonContentDescription: String? = null,
        rightButtonClickListener: View.OnClickListener? = null,
    ) {
        binding.leftIcon.apply {
            leftButtonIconRes?.let {
                setImageResource(leftButtonIconRes)
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                contentDescription = getResString(
                    leftButtonContentDescriptionRes,
                    leftButtonContentDescription
                )
                setOnClickListener(leftButtonClickListener)
                visibility = View.VISIBLE
            } ?: run {
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                visibility = View.INVISIBLE
            }
        }

        binding.rightIcon.apply {
            rightButtonIconRes?.let {
                setImageResource(rightButtonIconRes)
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                contentDescription = getResString(
                    rightButtonContentDescriptionRes,
                    rightButtonContentDescription
                )
                setOnClickListener(rightButtonClickListener)
                visibility = View.VISIBLE
            } ?: run {
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                visibility = View.INVISIBLE
            }
        }

        getResString(titleRes, title)?.let { titleString ->
            binding.tvTitle.apply {
                text = titleString
                buildAccessibilityDelegate(isHeading = true)
            }
        } ?: run {
            binding.tvTitle.text = null
        }
    }
}
