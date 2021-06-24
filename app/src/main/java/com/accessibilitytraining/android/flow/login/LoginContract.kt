package com.accessibilitytraining.android.flow.login

import android.text.Editable
import com.accessibilitytraining.android.base.BasePresenter
import com.accessibilitytraining.android.base.BaseView

interface LoginContract {
    interface View : BaseView {
        fun showErrorMessage()
        fun routeToListPage()
    }

    interface Presenter : BasePresenter<View> {
        fun checkLoginInfo(username: Editable?, password: Editable?)
    }
}