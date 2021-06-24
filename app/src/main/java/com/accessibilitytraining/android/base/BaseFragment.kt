package com.accessibilitytraining.android.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.accessibilitytraining.android.MainActivity

abstract class BaseFragment : Fragment() {
    protected val mainActivity get() = (activity as? MainActivity?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenterShouldAttachView()
    }

    override fun onDestroyView() {
        presenterShouldDetachView()
        activity?.title = null
        super.onDestroyView()
    }

    abstract fun presenterShouldAttachView()
    abstract fun presenterShouldDetachView()
}
