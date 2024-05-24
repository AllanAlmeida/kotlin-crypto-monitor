package online.ideaincode.cryptomonitor.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoService {
    @GET("tickers")
    suspend fun fetchCoinTicker(@Query("symbols") symbols: String = "BTC-BRL"): Response<List<Ticker>>
}