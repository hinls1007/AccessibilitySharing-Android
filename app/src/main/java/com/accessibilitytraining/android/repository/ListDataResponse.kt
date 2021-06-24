package com.accessibilitytraining.android.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListDataResponse(
    val section: List<ListSectionModel>? = null
) : Parcelable {
    @Parcelize
    data class ListSectionModel(
        val headerTitle: String? = null,
        val rows: List<ListRowModel>? = null
    ) : Parcelable

    @Parcelize
    data class ListRowModel(
        val title: String? = null,
        val amount: String? = null,
        val date: String? = null,
        val detail: ListDetailModel? = null
    ) : Parcelable


    @Parcelize
    data class ListDetailModel(
        val title: String? = null,
        val amount: String? = null,
        val date: String? = null,
        val item1: String? = null,
        val item2: String? = null,
        val item3: String? = null,
        val item4: String? = null,
        val images: List<ListImageModel>? = null
    ) : Parcelable

    @Parcelize
    data class ListImageModel(
        val title: String? = null,
        var date: String? = null
    ) : Parcelable
}
