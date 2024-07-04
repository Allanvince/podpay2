package com.vince.tucash.utils

import com.vince.tucash.data.RegisterBody
import com.vince.tucash.data.AuthResponse
import com.vince.tucash.data.LoginBody
import com.vince.tucash.data.LoginResponse
import com.vince.tucash.data.WalletBalance
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIConsumer {


    @POST("register/")
    suspend fun registerUser(@Body body: RegisterBody): Response<AuthResponse>

    @POST("login/")
    suspend fun loginUser(@Body body: LoginBody): Response<LoginResponse>


}