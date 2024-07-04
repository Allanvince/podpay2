package com.vince.tucash.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vince.tucash.R
import com.vince.tucash.data.WalletBalance

class BalanceAdapter(val context: Context, private val walletBalance: MutableList<WalletBalance>): RecyclerView.Adapter<WalletViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return WalletViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Display only one item
        return 1
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        // Check if walletBalance is not empty
        if (walletBalance.isNotEmpty()) {
            // Display the first item from walletBalance
            holder.walletBalanceDisplay.text = walletBalance[position].toString()
        } else {
            // Handle the case where walletBalance is empty
            holder.walletBalanceDisplay.text = "0"
        }
    }
}


class WalletViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    var walletBalanceDisplay: TextView

    init {
        walletBalanceDisplay = itemView.findViewById(R.id.walletsBalanceDisplay)
    }
}