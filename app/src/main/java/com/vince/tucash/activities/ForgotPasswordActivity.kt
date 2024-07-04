package com.vince.tucash.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.vince.tucash.R
import com.vince.tucash.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)

        binding.tucashLogo.startAnimation(rotateAnimation)

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.submitBtn.setOnClickListener {
            val email = binding.emailAddress.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if (email.isEmpty()){
                    Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT)
                        .show()
                }
                Toast.makeText(this, "Invalid email syntax", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                //
            }
        }
    }
}