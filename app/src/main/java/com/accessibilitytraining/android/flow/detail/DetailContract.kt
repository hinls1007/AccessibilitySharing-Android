package com.accessibilitytraining.android.flow.detail

import com.accessibilitytraining.android.base.BasePresenter
import com.accessibilitytraining.android.base.BaseView
import com.accessibilitytraining.android.repository.ListDataResponse

interface DetailContract {
    interface View : BaseView {
        fun setDetail(detailModel: DetailData)
        fun setImageList(imageModel: List<ListDataResponse.ListImageModel>)
        fun routeToTooltip(title: String?, content: String?)
    }

    interface Presenter : BasePresenter<View> {
        fun initDetailModel(detailModel: ListDataResponse.ListDetailModel)
        fun tooltipOnClick()
    }
}
