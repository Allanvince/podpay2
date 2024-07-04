package com.vince.tucash.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vince.tucash.data.LoginBody
import com.vince.tucash.data.LoginData
import com.vince.tucash.data.LoginResponse
import com.vince.tucash.repository.AuthRepository
import com.vince.tucash.utils.RequestStatus
import kotlinx.coroutines.launch

class LoginActivityViewModel(private val authRepository: AuthRepository, val application: Context): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var user: MutableLiveData<LoginResponse> = MutableLiveData()
    private var navigateToFingerprintActivity: MutableLiveData<Boolean> = MutableLiveData(false)


    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getUser(): LiveData<LoginResponse> = user
    fun getNavigateToFingerprintActivity(): LiveData<Boolean> = navigateToFingerprintActivity


    fun setNavigateToFingerprintActivity(navigate: Boolean) {
        navigateToFingerprintActivity.value = navigate
    }

    fun loginUser(body: LoginBody){
        viewModelScope.launch {
            authRepository.loginUser(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false

                        val loginResponse = it.data
                        LoginData.LoginResponse = it.data
                        Log.d("LOGIN_RESPONSE", LoginData.LoginResponse.toString())
                        if (loginResponse.message == "user logged in successful") {
                            // Set the LiveData to trigger navigation
                            navigateToFingerprintActivity.value = true
                        }

                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message
                    }
                }
            }
        }

    }
}
