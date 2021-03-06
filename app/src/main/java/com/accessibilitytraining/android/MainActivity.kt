package com.accessibilitytraining.android

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.accessibilitytraining.android.databinding.ActivityMainBinding
import com.accessibilitytraining.android.extension.getResString

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
        leftButtonClickListener: View.OnClickListener? = null,
        @StringRes titleRes: Int? = null,
        title: CharSequence? = null,
        @DrawableRes rightButtonIconRes: Int? = null,
        rightButtonClickListener: View.OnClickListener? = null,
    ) {
        binding.leftIcon.apply {
            leftButtonIconRes?.let {
                setImageResource(leftButtonIconRes)
                setOnClickListener(leftButtonClickListener)
                visibility = View.VISIBLE
            } ?: run {
                visibility = View.INVISIBLE
            }
        }

        binding.rightIcon.apply {
            rightButtonIconRes?.let {
                setImageResource(rightButtonIconRes)
                setOnClickListener(rightButtonClickListener)
                visibility = View.VISIBLE
            } ?: run {
                visibility = View.INVISIBLE
            }
        }

        binding.tvTitle.text = getResString(titleRes, title)
    }
}
