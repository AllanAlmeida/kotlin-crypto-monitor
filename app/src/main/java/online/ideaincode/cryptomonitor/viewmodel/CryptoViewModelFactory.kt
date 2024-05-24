package online.ideaincode.cryptomonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import online.ideaincode.cryptomonitor.service.CryptoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Factory para criar instâncias de CryptoViewModel
class CryptoViewModelFactory : ViewModelProvider.Factory {
    private fun createService(): CryptoService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadobitcoin.net/api/v4/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CryptoService::class.java)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CryptoViewModel(service = createService()) as T
    }
}