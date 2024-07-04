package com.vince.tucash.fragments

import HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vince.tucash.R
import com.vince.tucash.data.LoginData
import com.vince.tucash.data.LoginResponse
import com.vince.tucash.data.UserObject


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var loginResponse: LoginResponse



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        loginResponse = LoginData.LoginResponse


        viewModel.walletBalanceData.observe(viewLifecycleOwner, Observer { balance ->
            // Update your TextView with the balance data
            val balanceTextView = view.findViewById<TextView>(R.id.balanceDisplay)
            balanceTextView.text = balance
        })
        viewModel.fetchUserId(loginResponse)


        val depositMoney = view.findViewById<TextView>(R.id.depositMoney)
        depositMoney.setOnClickListener {
            val loadWalletFrag = LoadWalletFragment()
            val transition: FragmentTransaction = requireFragmentManager().beginTransaction()
            transition.replace(R.id.flFragment, loadWalletFrag)
            transition.commit()
        }

        val payBillBtn = view.findViewById<Button>(R.id.payBillBtn)
        payBillBtn.setOnClickListener {
            val showPopUp = PopUpFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }
        val buyAirtime = view.findViewById<Button>(R.id.buyAirtimeBtn)
        buyAirtime.setOnClickListener {
            val buyAirtimeFragment = BuyAirtimeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, buyAirtimeFragment)
            transaction.commit()
        }

        val withdrawMoney = view.findViewById<Button>(R.id.withDrawBtn)
        withdrawMoney.setOnClickListener {
            val withdrawMoneyFragment = WithdrawMoneyFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, withdrawMoneyFragment)
            transaction.commit()
        }

        val tuCashBtn = view.findViewById<Button>(R.id.tucashBtn)
        tuCashBtn.setOnClickListener {
            val tuCashFragment = TucashToTucashFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, tuCashFragment)
            transaction.commit()
        }

        return view
    }



}


