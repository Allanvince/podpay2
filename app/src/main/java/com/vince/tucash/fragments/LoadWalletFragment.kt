package com.vince.tucash.fragments

import android.os.Bundle
import android.view.InputQueue.Callback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope

import com.vince.tucash.R
import com.vince.tucash.data.Deposit
import com.vince.tucash.data.DepositApi
import com.vince.tucash.data.PaymentResponse
import com.vince.tucash.databinding.FragmentLoadWalletBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoadWalletFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoadWalletBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoadWalletBinding.inflate(inflater, container, false)

        val backDepositBtn = binding.backDepositButton
        backDepositBtn.setOnClickListener {
            val transactFragment = TransactFragment()
            val transition: FragmentTransaction = requireFragmentManager().beginTransaction()
            transition.replace(R.id.flFragment, transactFragment)
            transition.commit()
        }


        val topUpButton = binding.topUpButton
        topUpButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(p0: View?) {
        submitRequest()
    }

    private fun submitRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tucash-api-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val depositService = retrofit.create(DepositApi::class.java)
         val phoneNumber = binding.phoneDepositWallet.text.toString()
        val amountStr = binding.amountDepositWallet.text.toString()
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        val depositRequest = Deposit(phoneNumber, amount)

        val depositCall = depositService.deposit(depositRequest)

        depositCall.enqueue(object : retrofit2.Callback<PaymentResponse>{
            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    Toast.makeText(requireContext(), "Successful :)", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Connection issues!!!Please wait",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })

    }


}