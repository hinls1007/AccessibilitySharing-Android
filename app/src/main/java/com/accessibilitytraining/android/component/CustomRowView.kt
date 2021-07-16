package com.accessibilitytraining.android.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.accessibilitytraining.android.databinding.CustomRowViewBinding
import com.accessibilitytraining.android.extension.toDateContentDescription

class CustomRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var binding: CustomRowViewBinding? = null

    init {
        binding = CustomRowViewBinding.inflate(LayoutInflater.from(context), this)
    }

    fun setData(title: String?, amount: String?, date: String?, onClickListener: (() -> Unit)?) {
        binding?.apply {
            tvTitle.text = title
            tvAmount.text = amount
            tvDate.apply {
                text = date
                contentDescription = date?.toDateContentDescription()
            }

            onClickListener?.let {
                ivForward.visibility = View.VISIBLE
                rootView.setOnClickListener {
                    onClickListener.invoke()
                }
            } ?: run {
                ivForward.visibility = View.INVISIBLE
            }
        }
    }
}
