import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vince.tucash.data.LoginResponse
import com.vince.tucash.data.WalletBalance
import com.vince.tucash.utils.BalanceAPIService
import com.vince.tucash.utils.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val apiService = RetrofitService.create(BalanceAPIService::class.java)

    // LiveData to hold the response data
    private val _walletBalanceData = MutableLiveData<String>()

    // Expose a LiveData that can be observed from the UI
    val walletBalanceData: LiveData<String>
        get() = _walletBalanceData

    private var userId: Int = -1

    private fun fetchBalance(userId: Int) {
        val call = apiService.getBalance(userId)
        call.enqueue(object : Callback<WalletBalance> {
            override fun onResponse(
                call: Call<WalletBalance>,
                response: Response<WalletBalance>
            ) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        // Update the LiveData with the response data
                        _walletBalanceData.value = responseData.amount.toString()
                    }
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<WalletBalance>, t: Throwable) {
                // Handle failure
            }
        })
    }

    // Update the userId property when fetching user data
    fun fetchUserId(loginResponse: LoginResponse) {
        userId = loginResponse.id

        // Call fetchBalance with the userId
        fetchBalance(userId)
    }
}
