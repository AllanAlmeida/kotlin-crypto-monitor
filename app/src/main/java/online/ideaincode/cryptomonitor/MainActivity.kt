package online.ideaincode.cryptomonitor

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import online.ideaincode.cryptomonitor.state.ScreenState
import online.ideaincode.cryptomonitor.viewmodel.CryptoViewModel
import online.ideaincode.cryptomonitor.viewmodel.CryptoViewModelFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val viewModel: CryptoViewModel by viewModels { CryptoViewModelFactory() }
    private val textViewBitcoin: TextView by lazy { findViewById(R.id.textViewBitcoin) }
    private val textViewDate: TextView by lazy { findViewById(R.id.textViewDate) }
    private val buttonRefresh: Button by lazy { findViewById(R.id.buttonRefresh) }
    private val progressBar: ProgressBar by lazy { findViewById(R.id.progressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Observa mudanças no LiveData do ViewModel
        viewModel.tickerLiveData.observe(this) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    buttonRefresh.visibility = View.GONE
                }
                is ScreenState.Success -> {
                    progressBar.visibility = View.GONE
                    buttonRefresh.visibility = View.VISIBLE
                    val lastPrice = state.data?.lastOrNull()?.last?.toBigDecimalOrNull()
                    textViewBitcoin.text = lastPrice?.let { price ->
                        NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(price)
                    } ?: "0"
                    textViewDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR")).format(Date())
                }
                is ScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    buttonRefresh.visibility = View.VISIBLE
                    Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Configura o botão de atualização para buscar novos dados
        buttonRefresh.setOnClickListener {
            viewModel.fetch()
        }
    }
}
