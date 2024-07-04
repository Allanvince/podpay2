package com.vince.tucash.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vince.tucash.R
import com.vince.tucash.data.LoginBody
import com.vince.tucash.databinding.ActivityLoginBinding
import com.vince.tucash.repository.AuthRepository
import com.vince.tucash.utils.APIConsumer
import com.vince.tucash.utils.APIService
import com.vince.tucash.utils.VibrateView
import com.vince.tucash.view_model.LoginActivityViewModel
import com.vince.tucash.view_model.LoginViewModelFactory


class LoginActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var binding : ActivityLoginBinding

    private lateinit var mViewModel: LoginActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
            finish()
        }
        binding.signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }

        binding.loginWithGoogle.setOnKeyListener(this)
        binding.loginInBtn.setOnKeyListener(this)
        binding.emailAddress.onFocusChangeListener = this
        binding.password.onFocusChangeListener = this
        binding.password.setOnKeyListener(this)

        binding.loginInBtn.setOnClickListener(this)

        mViewModel = ViewModelProvider(this, LoginViewModelFactory(AuthRepository
            (APIService.getService(APIConsumer::class.java)), application))[LoginActivityViewModel::class.java]

        val navigateToFingerPrintActivityObserver: Observer<Boolean> = Observer {
            if (it) {
                Log.d("BOOLEAN", it.toString())
                startActivity(Intent(this, FingerPrintActivity::class.java))
                finish()
            }
        }
        mViewModel.getNavigateToFingerprintActivity().observe(this, navigateToFingerPrintActivityObserver)

        setupObservers()
    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this){

            binding.progressBar.isVisible = it
        }


        mViewModel.getErrorMessage().observe(this){
            val formErrorKeys = arrayOf("email", "password" )
            val messages = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)){
                    when(entry.key){

                        "email" -> {
                            binding.emailAddressTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "password" -> {
                            binding.passwordTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                    }
                }
                else{
                    messages.append(entry.value).append("\n")
                }
                if (messages.isNotEmpty()){
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.baseline_info_24)
                        .setTitle("Information")
                        .setMessage(messages)
                        .setPositiveButton("OK"){dialog, _ -> dialog!!.dismiss()}
                        .show()
                }
            }
        }
        mViewModel.getNavigateToFingerprintActivity().observe(this) { navigate ->
            if (navigate) {
                val intent = Intent(this, FingerPrintActivity::class.java)
                val userId = mViewModel.getUser().value?.id
                if (userId != null) {
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    finish()
                }
            }
        }

        mViewModel.getUser().observe(this) { loginResponse ->
            if (loginResponse != null && loginResponse.message == "user logged in successful") {
                // Set the LiveData to trigger navigation
                mViewModel.setNavigateToFingerprintActivity(true)
            }
        }
    }
    private fun validateEmail(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val email = binding.emailAddress.text.toString().trim()

        if (email.isEmpty()){
            errorMessage = "Enter your email address"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Invalid email syntax"
        }
        if (errorMessage != null && shouldUpdateView){
            binding.emailAddressTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@LoginActivity, this)
            }
        }

        return errorMessage == null
    }
    private fun validatePassword(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val password = binding.password.text.toString().trim()

        if (password.isEmpty()) {
            errorMessage = "Enter your password"
        }
        if (password.length < 8) {
            errorMessage = "Should be at least 8 characters"
        }
        if (errorMessage != null && shouldUpdateView){
            binding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@LoginActivity, this)
            }
        }
        return errorMessage == null
    }
    private fun validate(): Boolean{
        var isValid = true

        if (!validateEmail(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, binding.cardView)

        return isValid
    }


    private fun submitForm() {
        val email = binding.emailAddress.text!!.toString()
        val password = binding.password.text!!.toString()
        if (validate()){

            mViewModel.loginUser(LoginBody(email, password))
        }
    }

    override fun onClick(view: View?) {
        if (view != null){
            when(view.id){
                R.id.loginInBtn ->{
                    submitForm()
                }
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null){
            when(view.id){
                R.id.emailAddress -> {
                    if (hasFocus){
                        if (binding.emailAddressTil.isErrorEnabled){
                            binding.emailAddressTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validateEmail()
                    }
                }
                R.id.password -> {
                    if (hasFocus){
                        if (binding.passwordTil.isErrorEnabled){
                            binding.passwordTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validatePassword()
                    }
                }

            }
        }
    }


    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent!!.action == KeyEvent.ACTION_UP){
            submitForm()
        }

        return false
    }





}