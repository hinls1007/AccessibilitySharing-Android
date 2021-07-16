package com.accessibilitytraining.android.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.accessibilitytraining.android.builder.AccessibilityBuilder
import com.accessibilitytraining.android.databinding.CustomRowViewBinding

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
            tvDate.text = date

            onClickListener?.let {
                ivForward.visibility = View.VISIBLE
                rootView.setOnClickListener {
                    onClickListener.invoke()
                }
                AccessibilityBuilder().setViewType(AccessibilityBuilder.ViewType.Button).build(this@CustomRowView)
            } ?: run {
                ivForward.visibility = View.INVISIBLE
            }
        }
    }
}
