package com.accessibilitytraining.android.flow.list

import com.accessibilitytraining.android.MainRepository
import com.accessibilitytraining.android.repository.ListDataResponse

class ListPresenter(private val mainRepository: MainRepository) : ListContract.Presenter {
    override var view: ListContract.View? = null
    private val listData = mutableListOf<ListData>()

    override fun requestListDataResponse() {
        //if requestListDataResponse already
        if (listData.isNotEmpty()) {
            view?.setList(listData)
        } else {
            view?.showLoading()
            mainRepository.requestListDataResponse { listDataResponse ->
                listDataResponse?.let {
                    listData.clear()
                    listData.addAll(it.toListData())
                    view?.setList(listData)
                }
                view?.hideLoading()
            }
        }
    }

    private fun ListDataResponse.toListData(): List<ListData> {
        val listData = mutableListOf<ListData>()
        section?.let { section ->
            section.forEach { listSectionModel ->
                listSectionModel.headerTitle?.let { headerTitle ->
                    listData.add(ListData.Header(headerTitle))
                }
                listSectionModel.rows?.forEach { listRowModel ->
                    listData.add(
                        ListData.Row(
                            listRowModel.title,
                            listRowModel.amount,
                            listRowModel.date,
                            listRowModel.detail
                        )
                    )
                }
            }
        }
        return listData
    }
}