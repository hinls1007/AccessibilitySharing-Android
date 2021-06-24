package com.accessibilitytraining.android.flow.list

import com.accessibilitytraining.android.base.BasePresenter
import com.accessibilitytraining.android.base.BaseView

interface ListContract {
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun setList(listData: List<ListData>)
    }

    interface Presenter : BasePresenter<View> {
        fun requestListDataResponse()
    }
}