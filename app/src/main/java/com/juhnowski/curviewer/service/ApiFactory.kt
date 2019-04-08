package com.juhnowski.curviewer.service

import com.juhnowski.curviewer.Settings.JSON_BASE_URL

object ApiFactory{
    val stockApi: StockApi = RetrofitFactory.retrofit(JSON_BASE_URL).create(StockApi::class.java)
}