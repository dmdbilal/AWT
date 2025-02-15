package com.dmdbilal.agroweathertip.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmdbilal.agroweathertip.data.CropJsonTask
import com.dmdbilal.agroweathertip.domain.CropData
import com.dmdbilal.agroweathertip.domain.sign_in.SignInResult
import com.dmdbilal.agroweathertip.domain.sign_in.SignInState
import com.dmdbilal.agroweathertip.domain.util.CropState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CropViewModel : ViewModel() {
    var temperature = 0.0f
    var humidity = 0.0f
    var pH = 0.0f
    var rainfall = 85.0f
    var N = 0.0f
    var P = 0.0f
    var K = 0.0f

    private val _crops = MutableStateFlow<List<CropData>>(emptyList())
    val crops = _crops.asStateFlow()

    private val _cropState = MutableStateFlow(CropState())
    val cropState = _cropState.asStateFlow()

    private val _location = MutableStateFlow<List<Double>>(emptyList())
    val location: StateFlow<List<Double>> get() = _location

    fun setLocation(value: List<Double>) {
        _location.value = value
    }

    fun getCropRecommendations() {
        viewModelScope.launch {
            _cropState.update { it.copy(
                isLoading = true
            ) }
            CropJsonTask(N, P, K, temperature, humidity, pH, rainfall) { result ->
                updateCropsLiveData(result)
            }.execute()
        }
    }

    private fun updateCropsLiveData(jsonResponse: String?) {
        val gson = Gson()

        viewModelScope.launch {
            try {
                if (jsonResponse.isNullOrEmpty()) {
                    _cropState.update { it.copy(
                        isLoading = false,
                        error = "JSON response is null or empty."
                    ) }
                    println("JSON response is null or empty.")
                } else {

                    // Parse JSON into a list of CropData objects
                    val cropDataList =
                        gson.fromJson(jsonResponse, Array<CropData>::class.java).toList()

                    // Extract crops and probabilities into separate lists
                    val cropsLs = cropDataList.map { it.crop }
                    val probabilities = cropDataList.map { it.probability }

                    // Create a new list of CropData objects
                    val cropsList = mutableListOf<CropData>()

                    // Print the result
                    for (i in cropsLs.indices) {
                        val crop = CropData(cropsLs[i], probabilities[i])
                        cropsList.add(crop)
                    }

                    // Update LiveData with the new list
                    _crops.value = cropsList
                    _cropState.update { it.copy(
                        isLoading = false,
                    ) }
                    Log.d("MainActivity", "Crops data saved in viewmodel.")
                    println("temp: ${temperature} humd: $humidity ph: $pH N: $N P: $P K: $K rain: $rainfall")
                    for (i in cropsList) {
                        println(i)
                    }
                }
            } catch (e: JsonSyntaxException) {
                println("Error parsing JSON: ${e.message}")
                _cropState.update { it.copy(
                    isLoading = false,
                    error = e.message
                ) }
            }
        }
    }

    // Sign In Screen
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessfull = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun reset() {
        _cropState.update { CropState() }
        _crops.update { emptyList() }
    }

    // User Data
    private val _username = MutableStateFlow(null)
    val username = _username.asStateFlow()


}
