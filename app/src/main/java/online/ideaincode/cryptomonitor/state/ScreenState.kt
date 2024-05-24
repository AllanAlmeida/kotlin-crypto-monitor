package online.ideaincode.cryptomonitor.state

import online.ideaincode.cryptomonitor.service.Ticker

// Estado da tela representado por uma sealed class
sealed class ScreenState {
    // Estado de carregamento
    object Loading : ScreenState()
    // Estado de erro com uma exceção
    data class Error(val exception: Throwable) : ScreenState()
    // Estado de sucesso com os dados da API
    data class Success(val data: List<Ticker>?) : ScreenState()
}