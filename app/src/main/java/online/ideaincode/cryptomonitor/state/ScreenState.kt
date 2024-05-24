package online.ideaincode.cryptomonitor.state

import online.ideaincode.cryptomonitor.service.Ticker

sealed class ScreenState {
    data object Loading : ScreenState()
    data class Error(val exception: Throwable) : ScreenState()
    data class Success(val data: List<Ticker>?) : ScreenState()
}
