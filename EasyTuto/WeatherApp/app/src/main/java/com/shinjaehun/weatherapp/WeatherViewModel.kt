package com.shinjaehun.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.weatherapp.api.Constant
import com.shinjaehun.weatherapp.api.NetworkResponse
import com.shinjaehun.weatherapp.api.RetrofitInstance
import com.shinjaehun.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch

private const val TAG = "WeatherViewModel"

class WeatherViewModel: ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
//        Log.i(TAG, "city: $city")
        _weatherResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey,city)
                if (response.isSuccessful) {
                Log.i(TAG, "Response: ${response.body().toString()}")
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
//                Log.i(TAG, "Error: ${response.message()}")
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }
}