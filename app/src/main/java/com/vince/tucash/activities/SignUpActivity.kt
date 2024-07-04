package com.vince.tucash.activities


import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.vince.tucash.R
import com.vince.tucash.data.RegisterBody
import com.vince.tucash.data.ValidateEmailBody
import com.vince.tucash.databinding.ActivitySignUpBinding
import com.vince.tucash.repository.AuthRepository
import com.vince.tucash.utils.APIConsumer
import com.vince.tucash.utils.APIService
import com.vince.tucash.utils.VibrateView
import com.vince.tucash.view_model.RegisterActivityViewModel
import com.vince.tucash.view_model.RegisterViewModelFactory

class SignUpActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener
, TextWatcher{

    private lateinit var binding: ActivitySignUpBinding


    private lateinit var mViewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
            finish()
        }

        binding.signUpBtn.setOnClickListener(this)

        binding.firstName.onFocusChangeListener = this
        binding.secondName.onFocusChangeListener = this
        binding.thirdName.onFocusChangeListener = this
        binding.emailAddress.onFocusChangeListener = this
        binding.password.onFocusChangeListener = this
        binding.confirmPassword.onFocusChangeListener = this
        binding.confirmPassword.addTextChangedListener(this)
        binding.confirmPassword.setOnKeyListener(this)
        binding.phoneNumber.onFocusChangeListener = this
        binding.idNumber.onFocusChangeListener = this

        mViewModel = ViewModelProvider(this, RegisterViewModelFactory
            (AuthRepository(APIService.getService(APIConsumer::class.java))
            , application))[RegisterActivityViewModel::class.java]
        setupObservers()

    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this){

                binding.progressBar.isVisible = it
        }

        mViewModel.getErrorMessage().observe(this){
            val formErrorKeys = arrayOf("firstName", "secondName",
                "thirdName", "email", "password", "phoneNumber", "idNumber" )
            val messages = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)){
                    when(entry.key){
                        "firstName" -> {
                            binding.firstNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "secondName" -> {
                            binding.secondNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "thirdName" -> {
                            binding.thirdNameTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }

                        }
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
                        "phoneNumber" -> {
                            binding.phoneNumberTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "idNumber" -> {
                            binding.idNumberTil.apply {
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
        mViewModel.getUser().observe(this){
            if (it !=null){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun validateFirstName(shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val firstName = binding.firstName.text.toString().trim()
        if (firstName.isEmpty()) {
           errorMessage = "Enter your first name"
        }
        if (errorMessage != null){
            binding.firstNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null

    }
    private fun validateSecondName(shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val secondName = binding.secondName.text.toString().trim()

        if (secondName.isEmpty()) {
            errorMessage = "Enter your second name"
        }
        if (errorMessage != null){
            binding.secondNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }
    private fun validateThirdName(shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val thirdName = binding.thirdName.text.toString().trim()

        if (thirdName.isEmpty()) {
            errorMessage = "Enter your third name"
        }
        if (errorMessage != null){
            binding.thirdNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
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
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
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
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }
    private fun validateConfirmPassword(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val confirmPassword = binding.confirmPassword.text.toString().trim()

        if (confirmPassword.isEmpty()) {
            errorMessage = "Confirm your password"
        }
        if (errorMessage != null && shouldUpdateView){
            binding.confirmPassTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }

    private fun validatePhoneNumber(shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val phoneNumber = binding.idNumber.text.toString().trim()

//        if (phoneNumber.isEmpty()){
//            errorMessage = "Enter your phone number"}
//
//        if (phoneNumber.length < 9) {
//            errorMessage = "Enter a valid phone number"
//        }

        if (errorMessage != null){
            binding.phoneNumberTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }
        return errorMessage == null
    }
    private fun validateIdNumber(shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val idNumber = binding.phoneNumber.text.toString().trim()
//
        if (idNumber.isEmpty()){
            errorMessage = "Enter your id number"}
//
//        if (idNumber.length < 8) {
//            errorMessage = "Enter a valid ID number"
//        }


        if (errorMessage != null){
            binding.idNumberTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(shouldUpdateView: Boolean = true, shouldVibrateView: Boolean = true): Boolean{
        var errorMessage: String? = null
        val password = binding.password.text.toString().trim()
        val confirmPass = binding.confirmPassword.text.toString().trim()

        if (password != confirmPass){
            errorMessage = "Passwords don't match"
        }
        if (errorMessage != null && shouldUpdateView){
            binding.confirmPassTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView)VibrateView.vibrate(this@SignUpActivity, this)
            }
        }

        return errorMessage == null
    }


    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.signUpBtn){
            onSubmit()
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null){
            when(view.id){
                R.id.firstName -> {
                    if (hasFocus){
                        if (binding.firstNameTil.isErrorEnabled){
                            binding.firstNameTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validateFirstName()
                    }
                }
                R.id.secondName -> {
                    if (hasFocus){
                        if (binding.secondNameTil.isErrorEnabled){
                            binding.secondNameTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validateSecondName()
                    }
                }
                R.id.thirdName -> {
                    if (hasFocus){
                        if (binding.thirdNameTil.isErrorEnabled){
                            binding.thirdNameTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validateThirdName()
                    }
                }
                R.id.emailAddress -> {
                    if (hasFocus){
                        if (binding.emailAddressTil.isErrorEnabled){
                            binding.emailAddressTil.isErrorEnabled = false
                        }
                    }
                }
                R.id.password -> {
                    if (hasFocus){
                        if (binding.passwordTil.isErrorEnabled){
                            binding.passwordTil.isErrorEnabled = false
                        }
                    }
                    else{
                        if (validatePassword() && binding.confirmPassword.text!!.isNotEmpty() &&
                            validateConfirmPassword() && validatePasswordAndConfirmPassword()){
                            if (binding.confirmPassTil.isErrorEnabled){
                                binding.confirmPassTil.isErrorEnabled = false
                            }
                            binding.confirmPassTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
                R.id.confirmPassword -> {
                    if (hasFocus){
                        if (binding.confirmPassTil.isErrorEnabled){
                            binding.confirmPassTil.isErrorEnabled = false
                        }
                    }
                    else{
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()){
                            if (binding.passwordTil.isErrorEnabled){
                                binding.passwordTil.isErrorEnabled = false
                            }
                            binding.confirmPassTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
                R.id.phoneNumber -> {
                    if (hasFocus){
                        if (binding.phoneNumberTil.isErrorEnabled){
                            binding.phoneNumberTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validatePhoneNumber()
                    }
                }
                R.id.idNumber -> {
                    if (hasFocus){
                        if (binding.idNumberTil.isErrorEnabled){
                            binding.idNumberTil.isErrorEnabled = false
                        }
                    }
                    else{
                        validateIdNumber()
                    }
                }

            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP){
            onSubmit()
        }
        return false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (validatePassword(shouldUpdateView = false) && validateConfirmPassword(shouldUpdateView = false)
            && validatePasswordAndConfirmPassword(shouldUpdateView = false)){
            binding.confirmPassTil.apply {
                if (isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        }
        else{
            if (binding.confirmPassTil.startIconDrawable != null)
                binding.confirmPassTil.startIconDrawable != null
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }
    private fun onSubmit(){
        val firstName = binding.firstName.text!!.toString()
        val secondName = binding.secondName.text!!.toString()
        val thirdName = binding.thirdName.text!!.toString()
        val email = binding.emailAddress.text!!.toString()
        val password = binding.password.text!!.toString()
        val phoneNumber = binding.phoneNumber.text!!.toString()
        val idNumber = binding.idNumber.text!!.toString()

        if (validate()){
            // making api call
            mViewModel.registerUser(RegisterBody(email,firstName, secondName, thirdName, phoneNumber,idNumber,password))

        }
    }
    private fun validate(): Boolean{
        var isValid = true

        if (!validateFirstName(shouldVibrateView = false)) isValid = false
        if (!validateSecondName(shouldVibrateView = false)) isValid = false
        if (!validateThirdName(shouldVibrateView = false)) isValid = false
        if (!validateEmail(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false
        if (!validateConfirmPassword(shouldVibrateView = false)) isValid = false
        if (isValid && !validatePasswordAndConfirmPassword(shouldVibrateView = false)) isValid = false
        if (!validatePhoneNumber(shouldVibrateView = false)) isValid = false
        if (!validateIdNumber(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, binding.cardView)

        return isValid
    }

}

