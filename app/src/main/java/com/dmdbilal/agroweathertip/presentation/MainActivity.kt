package com.dmdbilal.agroweathertip.presentation

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dmdbilal.agroweathertip.data.location.LocationTracker
import com.dmdbilal.agroweathertip.data.remote.RetrofitClient
import com.dmdbilal.agroweathertip.domain.sign_in.GoogleAuthUiClient
import com.dmdbilal.agroweathertip.presentation.screens.*
import com.dmdbilal.agroweathertip.ui.theme.*
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.location.*
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.*
import okhttp3.*
import retrofit2.*
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: CropViewModel
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var locationTracker: LocationTracker
    private val permissionId = 2
    private val firestore = Firebase.firestore
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                locationTracker.getLocation()
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgroWeatherTipTheme {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                val navController = rememberNavController()
                viewModel = ViewModelProvider(this)[CropViewModel::class.java]
                chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
                locationTracker = LocationTracker(
                    fusedLocationClient,
                    viewModel,
                    applicationContext,
                    this
                )

                locationTracker.getLocation()

                lifecycleScope.launch {
                    getWeatherInfo(viewModel)
                }

                NavHost(navController = navController, startDestination = "HomeScreen") {
                    composable("LoginScreen") {
                        val state by viewModel.state.collectAsState()

                        LaunchedEffect(key1 = Unit) {
                            if (googleAuthUiClient.getSignedInUser() !=  null) {
                                navController.navigate("HomeScreen")
                            }
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = {result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )
                        
                        LaunchedEffect(key1 = state.isSignInSuccessfull) {
                            if (state.isSignInSuccessfull) {
                                Toast.makeText(
                                    applicationContext,
                                    "Successfully signed in",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate("HomeScreen")
                                viewModel.resetState()
                            }
                        }

                        LoginScreen(
                            state = state,
                            onClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        )
                    }

                    composable("ProfileScreen") {
                        ProfileScreen(
                            userData = googleAuthUiClient.getSignedInUser(),
                            onSignOut = {
                                lifecycleScope.launch {
                                    googleAuthUiClient.signOut()
                                    Toast.makeText(
                                        applicationContext,
                                        "Signed out",
                                        Toast.LENGTH_SHORT
                                    )
                                    navController.navigate("LoginScreen")
                                    navController.graph.clear()
                                }
                            }
                        )
                    }

                    composable("HomeScreen") {
                        LaunchedEffect(key1 = Unit) {
                            if (googleAuthUiClient.getSignedInUser() == null) {
                                navController.navigate("LoginScreen")
                            }
                        }

                        HomeScreen(navigation = navController, userData = googleAuthUiClient.getSignedInUser())
                    }
                    composable("GetCropScreen") {
                        GetCropScreen(navigation = navController, viewModel = viewModel, context = applicationContext)
                    }
                    composable("HelpScreen") {
                        HelpScreen(navigation = navController)
                    }
                    composable("CropScreen") {
                        CropScreen(navigation = navController, viewModel)
                    }
                    composable("TopCrops") {
                        TopCrops(navigation = navController, viewModel)
                    }
                    composable("BotScreen") {
                        ChatBotScreen(navigation = navController, viewModel = chatViewModel)
                    }
                    composable("ChatScreen") {
                        ChatScreen(navController)
                    }
                }
            }
        }
    }

    suspend fun getWeatherInfo(viewModel: CropViewModel) {
        val apiService = RetrofitClient.create()
        val response = apiService.getForecast(locationTracker.lat, locationTracker.lon, "temperature_2m,relative_humidity_2m")
        val temp = response.current.temperature_2m
        val humd = response.current.relative_humidity_2m
        viewModel.temperature = temp.toFloat()
        viewModel.humidity = humd.toFloat()
    }
}

