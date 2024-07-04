package com.vince.tucash.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vince.tucash.data.RegisterBody
import com.vince.tucash.data.User
import com.vince.tucash.data.ValidateEmailBody
import com.vince.tucash.repository.AuthRepository
import com.vince.tucash.utils.AuthToken
import com.vince.tucash.utils.RequestStatus
import kotlinx.coroutines.launch

class RegisterActivityViewModel(private val authRepository: AuthRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var user: MutableLiveData<User> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getUser(): LiveData<User> = user


    fun registerUser(body:RegisterBody){
        viewModelScope.launch {
            authRepository.registerUser(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        user.value = it.data.user
//                        AuthToken.getInstance(application.baseContext) = it.data.token
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
