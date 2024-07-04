package com.vince.tucash.view_model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vince.tucash.data.LoginResponse
import com.vince.tucash.data.WalletBalance
import com.vince.tucash.utils.BalanceAPIService
import com.vince.tucash.utils.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class TransactViewModel: ViewModel() {

    private val apiService = RetrofitService.create(BalanceAPIService::class.java)

    private val _walletBalance = MutableLiveData<String>()

    val walletBalance:LiveData<String>
        get() = _walletBalance


    private var userId: Int = -1

    private fun fetchBalance(userId:Int){
        val call = apiService.getBalance(userId)
       viewModelScope.launch {
           call.enqueue(object: retrofit2.Callback<WalletBalance>{
               override fun onResponse(call: Call<WalletBalance>, response: Response<WalletBalance>) {
                   if (response.isSuccessful){
                       val responseData = response.body()
                       if (responseData !=null){
                           _walletBalance.value = responseData.amount.toString()
                       }
                   }
               }

               override fun onFailure(call: Call<WalletBalance>, t: Throwable) {

               }

           })
       }
    }
    private fun fetchId(loginResponse: LoginResponse){
        userId = loginResponse.id
        fetchBalance(userId)
    }
}