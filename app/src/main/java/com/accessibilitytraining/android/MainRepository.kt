package com.accessibilitytraining.android

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.accessibilitytraining.android.repository.ListDataResponse
import com.google.gson.Gson

class MainRepository(private val context: Context?) {
    fun requestListDataResponse(callback: (response: ListDataResponse?) -> Unit) {
        val rawDataString = context?.assets?.open("list_data.json")?.reader()?.readText()
        val listData = Gson().fromJson(rawDataString, ListDataResponse::class.java)

        //Hardcode 3sec response time
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(listData)
        }, 3000)
    }
}
