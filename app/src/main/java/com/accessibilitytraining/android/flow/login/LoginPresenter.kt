package com.accessibilitytraining.android.flow.login

import android.text.Editable

class LoginPresenter : LoginContract.Presenter {
    override var view: LoginContract.View? = null

    override fun checkLoginInfo(username: Editable?, password: Editable?) {
        if (username != null && username.length >= 6 && username.length <= 16) {
            view?.routeToListPage()
        } else {
            view?.showErrorMessage()
        }
    }
}