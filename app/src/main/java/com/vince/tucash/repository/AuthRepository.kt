package com.vince.tucash.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.vince.tucash.data.LoginBody
import com.vince.tucash.data.RegisterBody
import com.vince.tucash.utils.APIConsumer
import com.vince.tucash.utils.RequestStatus
import com.vince.tucash.utils.SimplifiedLoginMsg
import com.vince.tucash.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow
import java.lang.Exception


class AuthRepository(private val consumer: APIConsumer){

    fun registerUser(body: RegisterBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerUser(body)
        if (response.isSuccessful){
            emit(RequestStatus.Success(response.body()!!))
        }
        else{
            emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
        }
    }
    fun loginUser(body: LoginBody) = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.loginUser(body)
            if (response.isSuccessful){
                val loginResponse = response.body()

                if (loginResponse !=null){
                    val userId = loginResponse.id

                    Log.d("LoginResponse", "Response Body: ${response.body()}")
                    println(loginResponse)

//                    val sharedPreferences: SharedPreferences =
//                        context.getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)

                    emit(RequestStatus.Success(response.body()!!))

                }
            }
            else{
                emit(RequestStatus.Error(SimplifiedLoginMsg.get(response.errorBody()!!.byteStream().reader().readText())))
            }
        }catch (e: Exception){
            e.message
        }

    }
}

//                    val phoneNumber = loginResponse.phoneNumber
//                    val accessToken = loginResponse.token
//                    val refreshToken = loginResponse.refreshToken
//

//
//                    val editor = sharedPreferences.edit()
//                    editor.putString("user_id", userId.toString())
//                    editor.putString("phone_number", phoneNumber)
//                    editor.putString("access_token", accessToken)
//                    editor.putString("refresh_token", refreshToken)
//                    editor.apply()