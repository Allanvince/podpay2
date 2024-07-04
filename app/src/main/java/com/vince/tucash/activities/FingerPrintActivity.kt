package com.vince.tucash.activities

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.vince.tucash.R
import com.vince.tucash.databinding.ActivityFingerPrintBinding
import java.util.concurrent.Executor


class FingerPrintActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFingerPrintBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityFingerPrintBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.arrowLogin.setOnClickListener {
            startActivity(Intent(this, RecaptchaActivity::class.java))
            finish()

        }

        binding.fingerPrint.setOnClickListener{
            checkDeviceBiometric()
        }

        val myButton = binding.arrowLogin
        myButton.setOnHoverListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_HOVER_ENTER -> {
                    // Code to handle hover enter (change button appearance)
                    myButton.setBackgroundResource(R.drawable.hovered_button_background)
                }
                MotionEvent.ACTION_HOVER_EXIT -> {
                    // Code to handle hover exit (reset button appearance)
                    myButton.setBackgroundResource(R.drawable.normal_button_background)
                }
            }
            false
        }
    }

    private fun createBiometricListener(){
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@FingerPrintActivity, errString, Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@FingerPrintActivity, "Authentication Failed", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@FingerPrintActivity, "Authentication succeeded", Toast.LENGTH_LONG)
                    .show()
                startActivity(Intent(this@FingerPrintActivity, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun createPromptInfo(){
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in uding your biometric credential")
            .setNegativeButtonText("CANCEL BIOMETRIC")
            .build()
    }
    private fun checkDeviceBiometric(){
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                createBiometricListener()
                createPromptInfo()
                biometricPrompt.authenticate(promptInfo)

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(this, "No biometric feature available on this device", Toast.LENGTH_LONG)
                    .show()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "Biometric features are currently unavailable", Toast.LENGTH_LONG)
                    .show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(this, "Fingerprint is not enabled in the device",Toast.LENGTH_LONG)
                    .show()
            }
            else ->{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}