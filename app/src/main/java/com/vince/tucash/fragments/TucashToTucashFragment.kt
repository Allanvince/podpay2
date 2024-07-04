package com.vince.tucash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.vince.tucash.R
import com.vince.tucash.data.LoginData
import com.vince.tucash.data.PaymentApi
import com.vince.tucash.data.PaymentResponse
import com.vince.tucash.data.TuSend
import com.vince.tucash.databinding.FragmentTucashToTucashBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TucashToTucashFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentTucashToTucashBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTucashToTucashBinding.inflate(inflater, container, false)

        val backTuCashBtn = binding.root.findViewById<Button>(R.id.backTuCashBtn)
        backTuCashBtn.setOnClickListener {
            val homeFragment = HomeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, homeFragment)
            transaction.commit()
        }

        val tuCashButton = binding.root.findViewById<Button>(R.id.sendTuCashButton)
        tuCashButton.setOnClickListener(this)

        return binding.root
    }


    override fun onClick(view: View?) {
        onSubmit()
    }

    private fun onSubmit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tucash-api-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val transactService = retrofit.create(PaymentApi::class.java)
        val senderId = LoginData.LoginResponse.id
        val phoneNumber = binding.phoneTuCash.text.toString()
        val amountStr = binding.amountTuCash.text.toString()
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        val paymentRequest = TuSend(senderId, phoneNumber, amount)

        val call = transactService.makePayment(paymentRequest)

        call.enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
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