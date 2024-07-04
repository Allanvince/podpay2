package com.vince.tucash.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class Deposit(@SerializedName("PhoneNumber") val phoneNumber: String,
                   @SerializedName("Amount") val amount: Double)

interface DepositApi{

    @POST("stkpush")
    fun deposit(@Body request: Deposit): Call<PaymentResponse>
 }