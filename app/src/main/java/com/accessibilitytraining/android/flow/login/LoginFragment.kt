package com.accessibilitytraining.android.flow.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.accessibilitytraining.android.R
import com.accessibilitytraining.android.base.BaseFragment
import com.accessibilitytraining.android.databinding.FragmentLoginBinding
import com.accessibilitytraining.android.helper.buildAccessibilityDelegate
import com.accessibilitytraining.android.helper.isTouchExplorationEnable

class LoginFragment : BaseFragment(), LoginContract.View {
    private var binding: FragmentLoginBinding? = null
    private val presenter = LoginPresenter()

    override fun getAccessibilityPageTitle(context: Context) =
        context.getString(R.string.login_page_title)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity?.setActionBarData(
            titleRes = R.string.login_page_title
        )

        binding?.btnLogin?.setOnClickListener {
            presenter.checkLoginInfo(
                binding?.etUsername?.text,
                binding?.etPassword?.text
            )
        }

        //if talkback is open, remove hint
        if (context?.isTouchExplorationEnable() == true) {
            binding?.etUsername?.hint = null
            binding?.etPassword?.hint = null
        }

        binding?.btnLogin?.buildAccessibilityDelegate(clickActionDescriptionRes = R.string.common_login)
    }

    override fun showErrorMessage() {
        binding?.tvUsernameErrorMsg?.apply {
            visibility = View.VISIBLE
            announceForAccessibility(text)
        }
    }

    override fun routeToListPage() {
        //clear login data
        binding?.tvUsernameErrorMsg?.visibility = View.GONE
        binding?.etUsername?.text = null
        binding?.etPassword?.text = null

        findNavController().navigate(R.id.action_LoginFragment_to_ListFragment)
    }

    override fun presenterShouldAttachView() {
        presenter.attachView(this)
    }

    override fun presenterShouldDetachView() {
        presenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
