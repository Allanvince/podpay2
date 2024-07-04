package com.vince.tucash.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.vince.tucash.R
import com.vince.tucash.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        val sideAnimation2 = AnimationUtils.loadAnimation(this, R.anim.slide2)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)

        binding.tucashText.startAnimation(sideAnimation)
        binding.imageDollar.startAnimation(sideAnimation2)

        Handler().postDelayed({
            startActivity(Intent(this, SignActivity::class.java))
            finish()
        },3000)

    }
}