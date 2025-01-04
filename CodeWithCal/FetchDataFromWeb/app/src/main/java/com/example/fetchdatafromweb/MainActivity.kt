package com.example.fetchdatafromweb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fetchdatafromweb.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCurrencyDaty().start()
    }

    private fun fetchCurrencyDaty(): Thread {
        return Thread {
            val url = URL("https://open.er-api.com/v6/latest/krw")
            val connection = url.openConnection() as HttpsURLConnection

            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
//                println(inputSystem.toString())
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)

                updateUI(request)

                inputStreamReader.close()
                inputSystem.close()
            } else {
                binding.baseCurrency.text = "Failed Connection"
            }
        }
    }

    private fun updateUI(request: Request) {
        Log.i("MainActivity", request.rates.toString())
        runOnUiThread {
            kotlin.run {
                binding.lastUpdated.text = request.time_last_update_utc
                binding.usdCurrency.text = String.format("USD: %.6f", request.rates.USD)
                binding.euroCurrency.text = String.format("EURO: %.6f", request.rates.EUR)
                binding.jpyCurrency.text = String.format("JPY: %.6f", request.rates.JPY)
            }
        }

    }
}