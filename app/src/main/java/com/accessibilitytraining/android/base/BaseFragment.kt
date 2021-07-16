package com.accessibilitytraining.android.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.accessibilitytraining.android.MainActivity

abstract class BaseFragment : Fragment() {
    protected val mainActivity get() = (activity as? MainActivity?)
    protected open fun getAccessibilityPageTitle(context: Context): CharSequence? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenterShouldAttachView()
        getAccessibilityPageTitle(view.context)?.let {
            /**
             * if didn't set a activity?.title(null or empty string), with consider android:label as the title
             * if activity?.title same as actionbar title, will skip actionbar focus
             */
            activity?.title = "$it "
            //Some old version TalkBack/Device will not announce Title, need announce manually.
            view.announceForAccessibility(it)
        }
    }

    override fun onDestroyView() {
        presenterShouldDetachView()
        activity?.title = null
        super.onDestroyView()
    }

    abstract fun presenterShouldAttachView()
    abstract fun presenterShouldDetachView()
}
