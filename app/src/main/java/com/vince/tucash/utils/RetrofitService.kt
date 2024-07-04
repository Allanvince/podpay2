package com.vince.tucash.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://tucash-api-production.up.railway.app/get_balance/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(apiService: Class<BalanceAPIService>): BalanceAPIService {
        return retrofit.create(apiService)
    }

}

