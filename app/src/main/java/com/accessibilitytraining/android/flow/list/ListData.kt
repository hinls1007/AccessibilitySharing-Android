package com.accessibilitytraining.android.flow.list

import com.accessibilitytraining.android.repository.ListDataResponse

sealed class ListData {
    data class Header(val headerTitle: String? = null) : ListData()
    data class Row(
        val title: String? = null,
        val amount: String? = null,
        val date: String? = null,
        val detail: ListDataResponse.ListDetailModel? = null
    ) : ListData()
}