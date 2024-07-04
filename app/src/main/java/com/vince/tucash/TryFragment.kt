package com.vince.tucash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vince.tucash.data.WalletBalance
import com.vince.tucash.utils.APIConsumer
import com.vince.tucash.utils.BalanceAdapter
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_try, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViews)

       val retrofit = Retrofit.Builder()
           .baseUrl("https://tucash-api-production.up.railway.app/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()

        val apiService = retrofit.create(APIConsumer::class.java)

//        lifecycleScope.launch {
////            val call = apiService.getBalance()
////            call.enqueue(object: Callback<WalletBalance>{
////                override fun onResponse(
////                    call: Call<WalletBalance>,
////                    response: Response<WalletBalance>
////                ) {
//////                    if (response.isSuccessful){
//////                       recyclerView.apply {
//////                           layoutManager = LinearLayoutManager(requireContext())
//////                           adapter = BalanceAdapter(response.body()!!)
//////                       }
//////                    }
////                }
////
////                override fun onFailure(call: Call<WalletBalance>, t: Throwable) {
////                    Log.e("TAG", "failed ")
////                }
////
////            })
//
//        }


        return view



    }


}