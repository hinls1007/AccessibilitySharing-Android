package com.accessibilitytraining.android.flow.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.accessibilitytraining.android.databinding.DialogFragmentTooltipBinding
import com.accessibilitytraining.android.helper.asHeading
import com.accessibilitytraining.android.helper.buildAccessibilityDelegate

class TooltipDialogFragment : DialogFragment() {
    private var binding: DialogFragmentTooltipBinding? = null
    private val arguments: TooltipDialogFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        /**
         * if didn't set a dialog title(null or empty string), with consider first focusable element as the title
         * if dialog title same as tvTitle, will skip tvTitle focus
         */
        dialog?.setTitle(arguments.tooltipTitle + " ")
        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentTooltipBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            tvTitle.apply {
                text = arguments.tooltipTitle
                asHeading()
            }
            tvContent.text = arguments.tooltipContent
            ivClose.apply {
                setOnClickListener {
                    dismiss()
                }
                buildAccessibilityDelegate(traversalAfter = tvContent)
            }
        }
    }
}
