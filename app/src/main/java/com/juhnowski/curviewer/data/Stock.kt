package com.juhnowski.curviewer.data

import java.math.RoundingMode


data class Stock(
    var stock:List<Rec>,
    var as_of:String)

data class Rec(
    val name:String,
    val price:Price,
    val percent_change:Double,
    val volume:Int,
    val symbol:String)

data class Price(
    val currency:String,
    var amount:Double)
