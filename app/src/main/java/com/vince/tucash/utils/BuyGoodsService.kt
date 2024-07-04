package com.vince.tucash.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BuyGoodsService {
    private const val BASE_URL = "https://tucash-api-production.up.railway.app/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(buyService: Class<BuyAPIService>): BuyAPIService {
        return retrofit.create(buyService)
    }
}