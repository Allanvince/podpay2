package com.vince.tucash.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.vince.tucash.R
import com.vince.tucash.data.LoginData
import com.vince.tucash.data.PaymentResponse
import com.vince.tucash.databinding.FragmentPayBillBinding
import com.vince.tucash.utils.PayAPIService
import com.vince.tucash.utils.PayBill
import com.vince.tucash.utils.PayBillService
import retrofit2.Call
import retrofit2.Response

class PayBillFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPayBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPayBillBinding.inflate(inflater, container, false)


        val backPayBtn = binding.backPayBtn
        backPayBtn.setOnClickListener {
            val homeFragment = HomeFragment()
            val transactFragment: FragmentTransaction = requireFragmentManager().beginTransaction()
            transactFragment.replace(R.id.flFragment, homeFragment)
            transactFragment.commit()
        }

        val payButton = binding.payBillSafButton
        payButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(p0: View?) {
        submitPayment()
    }

    private fun submitPayment() {

        val payBillService = PayBillService.create(PayAPIService::class.java)

        val payBillNumber = binding.payBillNumber.text.toString()
        val accountNumber = binding.accountNumber.text.toString()
        val amountStr = binding.amountPayBill.text.toString()
        val amount = amountStr.toDoubleOrNull()?: 0.0
        val payBillUserId = LoginData.LoginResponse.id

        val paymentRequest = PayBill(payBillNumber, accountNumber, amount,payBillUserId )

        val payBillCall = payBillService.payBill(paymentRequest)

        payBillCall.enqueue(object: retrofit2.Callback<PaymentResponse>{
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