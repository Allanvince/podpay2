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
import com.vince.tucash.databinding.FragmentBuyGoodsBinding
import com.vince.tucash.utils.BuyAPIService
import com.vince.tucash.utils.BuyGoods
import com.vince.tucash.utils.BuyGoodsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyGoodsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBuyGoodsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBuyGoodsBinding.inflate(inflater, container, false)


        val backButton = binding.backBuyGoodsBtn
        backButton.setOnClickListener {
            val homeFragment = HomeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.flFragment, homeFragment)
            transaction.commit()
        }

        val buyGoodsBtn = binding.buyGoodsSafButton
        buyGoodsBtn.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(p0: View?) {
        submitBuyGoodsRequest()
    }

    private fun submitBuyGoodsRequest() {
      val buyGoodsService = BuyGoodsService.create(BuyAPIService::class.java)

        val tillNumbers = binding.tillNumber.text.toString()
        val amountBuyGoodsStr = binding.amountBuyGoods.text.toString()
        val amountBuyGoods = amountBuyGoodsStr.toDoubleOrNull()?: 0.0
        val buyGoodsSenderId = LoginData.LoginResponse.id

        val buyGoodsRequest = BuyGoods(tillNumbers, amountBuyGoods, buyGoodsSenderId)
        val buyGoodsCall = buyGoodsService.buyGoods(buyGoodsRequest)

        buyGoodsCall.enqueue(object: Callback<PaymentResponse>{
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
                Toast.makeText(requireContext(), "Check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }
        })


    }

}