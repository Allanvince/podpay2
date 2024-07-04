package com.vince.tucash.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class TuSend(@SerializedName("sender_id") val senderId: Int, @SerializedName("receiver_phone_number") val receiverPhoneNumber: String,
                  val amount: Double
)

interface PaymentApi {

    @POST("transact")
    fun makePayment(@Body request: TuSend): Call<PaymentResponse>
}