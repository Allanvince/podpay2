package com.vince.tucash.data

import com.google.gson.annotations.SerializedName

data class User(val email :String,
                @SerializedName("first_name")val firstName :String,
                @SerializedName("second_name")val secondName :String,
                @SerializedName("third_name")val thirdName :String,
                @SerializedName("phone_number")val phoneNumber :String,
                @SerializedName("id_number")val idNumber :String,
                val password :String,)



