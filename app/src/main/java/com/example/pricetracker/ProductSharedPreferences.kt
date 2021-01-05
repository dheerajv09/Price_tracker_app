package com.example.pricetracker

import android.content.Context
import android.content.SharedPreferences


class ProductSharedPreferences(private val context: Context) {

    fun saveData(url: String,name:String, expectedPrice: Int,isTracking:Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREDERENCES, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(PRODUCT_URL, url)
        editor.putString(PRODUCT_NAME,name)
        editor.putInt(Expected_PRICE, expectedPrice)
        editor.putBoolean(TRACK_PRICE,isTracking)
        editor.apply()
    }

    fun getProductUrl(): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREDERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PRODUCT_URL,"")
    }

    fun getProductName(): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREDERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PRODUCT_NAME,"")
    }

    fun getExpectedPrice(): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREDERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(Expected_PRICE, -1)
    }

    fun getIsTracking(): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREDERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(TRACK_PRICE,false)
    }
}