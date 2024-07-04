package com.vince.tucash.utils

import com.vince.tucash.data.WalletBalance
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BalanceAPIService {
    @GET("{id}/")
    fun getBalance(@Path("id")userId: Int): Call<WalletBalance>
}