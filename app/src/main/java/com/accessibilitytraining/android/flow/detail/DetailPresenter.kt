package com.accessibilitytraining.android.flow.detail

import com.accessibilitytraining.android.repository.ListDataResponse

class DetailPresenter : DetailContract.Presenter {
    override var view: DetailContract.View? = null
    private var rawDetailModel: ListDataResponse.ListDetailModel? = null

    override fun initDetailModel(detailModel: ListDataResponse.ListDetailModel) {
        rawDetailModel = detailModel
        detailModel.apply {
            view?.setDetail(
                DetailData(
                    title = title,
                    amount = amount,
                    date = date,
                    item1 = item1,
                    item2 = item2,
                    item3 = item3,
                    item4 = item4
                )
            )
            images?.let {
                view?.setImageList(it)
            }
        }
    }

    override fun tooltipOnClick() {
        rawDetailModel?.title.let {
            view?.routeToTooltip(title = it, content = "$it Content")
        }
    }
}
