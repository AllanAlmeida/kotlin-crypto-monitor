package online.ideaincode.cryptomonitor.service

// Data class para representar um ticker individual
data class Ticker(
    val pair: String,
    val high: String,
    val low: String,
    val vol: String,
    val last: String,
    val buy: String,
    val sell: String,
    val open: String,
    val date: Long
)
