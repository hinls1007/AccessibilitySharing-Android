package com.accessibilitytraining.android.base

import androidx.annotation.CallSuper

interface BasePresenter<VIEW : BaseView> {
    var view: VIEW?

    @CallSuper
    fun attachView(view: VIEW?) {
        this.view = view
    }

    @CallSuper
    fun detachView() {
        this.view = null
    }
}