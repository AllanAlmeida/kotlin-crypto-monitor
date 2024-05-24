package online.ideaincode.cryptomonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import online.ideaincode.cryptomonitor.service.CryptoService
import online.ideaincode.cryptomonitor.state.ScreenState

// ViewModel para gerenciar os dados da UI e a lógica de negócios
class CryptoViewModel(
    private val service: CryptoService
): ViewModel() {
    private val _tickerLiveData = MutableLiveData<ScreenState>()
    val tickerLiveData: LiveData<ScreenState> = _tickerLiveData

    // Inicializa a busca de dados
    init {
        fetch()
    }

    // Função para buscar os dados da API
    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            _tickerLiveData.postValue(ScreenState.Loading)
            try {
                val response = service.fetchCoinTicker()
                if (response.isSuccessful) {
                    val tickers = response.body()
                    if (tickers != null && tickers.isNotEmpty()) {
                        _tickerLiveData.postValue(ScreenState.Success(data = tickers))
                    } else {
                        _tickerLiveData.postValue(ScreenState.Error(Exception("No ticker data available")))
                    }
                } else {
                    _tickerLiveData.postValue(ScreenState.Error(Exception("Erro na resposta: ${response.code()}")))
                }
            } catch (exception: Throwable) {
                _tickerLiveData.postValue(ScreenState.Error(exception))
            }
        }
    }
}