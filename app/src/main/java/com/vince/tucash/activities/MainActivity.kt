package com.vince.tucash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vince.tucash.fragments.HomeFragment
import com.vince.tucash.fragments.MenuBtnFragment
import com.vince.tucash.fragments.ProfileFragment
import com.vince.tucash.R
import com.vince.tucash.TryFragment
import com.vince.tucash.data.RegisterBody
import com.vince.tucash.data.User
import com.vince.tucash.data.UserObject
import com.vince.tucash.fragments.ShopFragment
import com.vince.tucash.fragments.TransactFragment
import com.vince.tucash.databinding.ActivityMainBinding

import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var greetings : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        greetings = findViewById(R.id.greetings)


        val calender = Calendar.getInstance()

        val jam:Int = calender.get(Calendar.HOUR_OF_DAY)

        val morning = "Good Morning"
        val afterNoon = "Good Afternoon"
        val evening = "Good Evening"
        val night = "Good Night"
        val hi = "Hi"


        if (jam >=0 && jam < 12){
            greetings.text = morning
        }
        else if (jam >=12 && jam < 16){
            greetings.text = afterNoon
        }
        else if (jam >=16 && jam < 21){
            greetings.text = evening
        }
        else if (jam >=21 ){
            greetings.text = night
        }
        else{
            greetings.text = hi
        }

        val homeFragment = HomeFragment()
        val tryFragment = TryFragment()
        val transactFragment = TransactFragment()
        val shopFragment = ShopFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)


        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.shop -> setCurrentFragment(shopFragment)
                R.id.transact -> setCurrentFragment(transactFragment)
                R.id.profile -> setCurrentFragment(profileFragment)

            }
            true
        }
        binding.menuButton.setOnClickListener {
            val menuBtnFragment = MenuBtnFragment()

            setCurrentFragment(menuBtnFragment)
        }

    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
//            addToBackStack(null)
            commit()
        }
    }
}