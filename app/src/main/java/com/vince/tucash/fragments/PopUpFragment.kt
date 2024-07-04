package com.vince.tucash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.vince.tucash.R

class PopUpFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_pop_up, container, false)

        val payBill = view.findViewById<Button>(R.id.payBillBtn)
        payBill.setOnClickListener {
            val payBillFragment = PayBillFragment()
            val transactFragment : FragmentTransaction = requireFragmentManager().beginTransaction()
            transactFragment.replace(R.id.flFragment, payBillFragment)
            transactFragment.commit()

        }

        val buyGoods = view.findViewById<Button>(R.id.buyGoodsBtn)
        buyGoods.setOnClickListener {
            val buyGoodsFragment = BuyGoodsFragment()
            val transactFragment: FragmentTransaction = requireFragmentManager().beginTransaction()
            transactFragment.replace(R.id.flFragment, buyGoodsFragment)
            transactFragment.commit()

        }
        return view

    }
}