package com.vince.tucash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.vince.tucash.R

class BuyAirtimeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_buy_airtime, container, false)

        val backBtn = view.findViewById<Button>(R.id.backBuyBtn)
        backBtn.setOnClickListener {
            val homeFragment = HomeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, homeFragment)
            transaction.commit()
        }


        return view
    }

}