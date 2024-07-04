package com.vince.tucash.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vince.tucash.databinding.ActivityRecaptchaBinding

class RecaptchaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecaptchaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecaptchaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val tuCashLogo = binding.recapTuCashLogo
        val dollarLogo = binding.recapDollarLogo
        val dollarSignLogo = binding.recapDollarSignLogo
        val payLogo = binding.recapPayLogo
        
        var count: Int = 2
        
        while (true){

            dollarLogo.setOnClickListener {
                //                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
            }
            dollarSignLogo.setOnClickListener {
                //                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
            }
            payLogo.setOnClickListener {
//                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
            }

            tuCashLogo.setOnClickListener {
                Toast.makeText(this, "ReCaptcha successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            break
        }
    }

}