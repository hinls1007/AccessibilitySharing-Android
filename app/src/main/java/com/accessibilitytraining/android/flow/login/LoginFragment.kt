package com.accessibilitytraining.android.flow.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.accessibilitytraining.android.R
import com.accessibilitytraining.android.base.BaseFragment
import com.accessibilitytraining.android.builder.AccessibilityBuilder
import com.accessibilitytraining.android.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment(), LoginContract.View {
    private var binding: FragmentLoginBinding? = null
    private val presenter = LoginPresenter()

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

        binding?.btnLogin?.apply {
            setOnClickListener {
                presenter.checkLoginInfo(
                    binding?.etUsername?.text,
                    binding?.etPassword?.text
                )
            }
            AccessibilityBuilder().setClickActionLabel(getString(R.string.login_button)).build(this)
        }

        binding?.etUsername?.apply {
            AccessibilityBuilder()
                .setText("")
                .build(this)
        }

        binding?.etPassword?.apply {
            AccessibilityBuilder()
                .setText("")
                .build(this)
        }

    }

    override fun showErrorMessage() {
        with(binding?.tvUsernameErrorMsg) {
            this?.visibility = View.VISIBLE
            this?.announceForAccessibility(getString(R.string.username_error_message))
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
