package com.vince.tucash.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(val id: Int, @SerializedName("PhoneNumber")val phoneNumber: String, @SerializedName("access_token")val accessToken: String, @SerializedName("refresh_token")val refreshToken: String,
                         var message:String)
