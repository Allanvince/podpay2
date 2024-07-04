package com.vince.tucash.data

import com.google.gson.annotations.SerializedName

data class WalletBalance(@SerializedName("user_id")val userId:Int, val amount: Double)
