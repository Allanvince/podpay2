package com.vince.tucash.utils

import com.google.gson.annotations.SerializedName
import com.vince.tucash.data.PaymentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class BuyGoods(@SerializedName("till_number")val till: String, val amount: Double,
    @SerializedName("user_id") val userId:Int)

interface BuyAPIService {
    @POST("till_transactions/")
    fun buyGoods(@Body request: BuyGoods): Call<PaymentResponse>
}