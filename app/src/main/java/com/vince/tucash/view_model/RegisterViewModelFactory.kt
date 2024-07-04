package com.vince.tucash.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.vince.tucash.repository.AuthRepository
import java.security.InvalidParameterException

class RegisterViewModelFactory(private val authRepository: AuthRepository, val application: Application): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(RegisterActivityViewModel::class.java)){
            return RegisterActivityViewModel(authRepository, application) as T
            }
        throw InvalidParameterException("Unable to create RegisterActivityViewModel")
    }
}