package com.vince.tucash.utils

import com.google.gson.annotations.SerializedName
import com.vince.tucash.data.PaymentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class PayBill(
    val paybill: String, @SerializedName("account_number") val accountNumber: String, val amount: Double,
    @SerializedName("user_id") val userId:Int)

interface PayAPIService {
    @POST("paybill_transactions/")
    fun payBill(@Body request: PayBill): Call<PaymentResponse>
}