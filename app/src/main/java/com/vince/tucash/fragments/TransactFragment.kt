package com.vince.tucash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vince.tucash.R
import com.vince.tucash.data.LoginData
import com.vince.tucash.data.LoginResponse
import com.vince.tucash.view_model.TransactViewModel

class TransactFragment : Fragment() {

    private lateinit var transactViewModel: TransactViewModel
    private lateinit var loginResponse: LoginResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transact, container, false)

        transactViewModel = ViewModelProvider(this)[TransactViewModel::class.java]

        loginResponse = LoginData.LoginResponse

        transactViewModel.walletBalance.observe(viewLifecycleOwner, Observer{
            val walletBalanceDisplay = view.findViewById<TextView>(R.id.walletDisplay)
            walletBalanceDisplay.text = it
        })

        val depositMoneyBtn = view.findViewById<Button>(R.id.depositMoneyButton)
        depositMoneyBtn.setOnClickListener {
            val loadWalletFragment = LoadWalletFragment()
            val transactFragment : FragmentTransaction = requireFragmentManager().beginTransaction()
            transactFragment.replace(R.id.flFragment, loadWalletFragment)
            transactFragment.commit()
        }

        val sendMoneyBtn = view.findViewById<Button>(R.id.sendMoneyButton)
        sendMoneyBtn.setOnClickListener {
            val sendMoneyFragment = SendMoneyFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, sendMoneyFragment)
            transaction.commit()
        }

        return view
    }


}