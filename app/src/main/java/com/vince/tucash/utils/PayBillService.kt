package com.vince.tucash.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PayBillService {
    private const val BASE_URL = "https://tucash-api-production.up.railway.app/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(payService : Class<PayAPIService>): PayAPIService {
        return retrofit.create(payService)
    }
}