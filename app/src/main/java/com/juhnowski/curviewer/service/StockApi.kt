package com.juhnowski.curviewer.service

import com.juhnowski.curviewer.Settings.get_path
import com.juhnowski.curviewer.data.Stock
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface StockApi{
    @GET(get_path)
    fun getStock(): Deferred<Response<Stock>>
}