package com.dmdbilal.agroweathertip.presentation.screens

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.presentation.CropViewModel
import com.dmdbilal.agroweathertip.ui.theme.RoseWhite
import com.dmdbilal.agroweathertip.ui.theme.TealGreen
import com.dmdbilal.agroweathertip.ui.theme.Wattle
import com.dmdbilal.agroweathertip.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GetCropScreen(
    context: Context,
    navigation: NavController,
    viewModel: CropViewModel
) {
    val getCropText = stringResource(id = R.string.getcropscreen_get_crop)
    val goBackText = stringResource(id = R.string.getcropscreen_go_back_to_homescreen)
    val phLabel = stringResource(id = R.string.getcropscreen_ph_label)
    val phFind = stringResource(id = R.string.getcropscreen_how_to_find_ph)
    val nitrogenLabel = stringResource(id = R.string.getcropscreen_nitrogen_label)
    val phosphorusLabel = stringResource(id = R.string.getcropscreen_phosphorus_label)
    val potassiumLabel = stringResource(id = R.string.getcropscreen_potassium_label)
    val rainfallLabel = stringResource(id = R.string.getcropscreen_rainfall_label)
    val light = stringResource(id = R.string.getcropscreen_light)
    val moderate = stringResource(id = R.string.getcropscreen_moderate)
    val high = stringResource(id = R.string.getcropscreen_high)
    val getButton = stringResource(id = R.string.getcropscreen_get)
    val noInternetMessage = stringResource(id = R.string.getcropscreen_no_internet_connection)
    val enterValidPh = stringResource(id = R.string.getcropscreen_enter_valid_ph)
    val enterValidN = stringResource(id = R.string.getcropscreen_enter_valid_n)
    val enterValidP = stringResource(id = R.string.getcropscreen_enter_valid_p)
    val enterValidK = stringResource(id = R.string.getcropscreen_enter_valid_k)

    val levels = arrayOf("Light", "Moderate", "Heavy")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(levels[1]) }
    var N by remember { mutableStateOf("${viewModel.N}") }
    var P by remember { mutableStateOf("${viewModel.P}") }
    var K by remember { mutableStateOf("${viewModel.K}") }
    var pH by remember { mutableStateOf("${viewModel.pH}") }
    var rainfall by remember { mutableStateOf("${viewModel.rainfall}") }
    val state by viewModel.cropState.collectAsState()

    Scaffold(
        contentColor = Color.Black,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navigation.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Go back to homescreen",
                            tint = TealGreen
                        )
                    }
                },
                backgroundColor = RoseWhite,
                title = {
                    Text(
                        text = getCropText,
                        fontFamily = poppinsFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(RoseWhite),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = pH,
                onValueChange = { pH = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        text = phLabel,
                        fontFamily = poppinsFamily,
                        color = TealGreen
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = TealGreen,
                    focusedBorderColor = TealGreen,
                    unfocusedBorderColor = TealGreen
                ),
                shape = RoundedCornerShape(8.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigation.navigate("HelpScreen") }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_help),
                        contentDescription = phFind,
                        tint = TealGreen
                    )
                }
                Text(
                    text = phFind,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Light,
                    color = TealGreen,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = N,
                onValueChange = { N = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        text = nitrogenLabel,
                        fontFamily = poppinsFamily,
                        color = TealGreen
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = TealGreen,
                    focusedBorderColor = TealGreen,
                    unfocusedBorderColor = TealGreen
                ),
                shape = RoundedCornerShape(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = P,
                onValueChange = { P = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        text = phosphorusLabel,
                        fontFamily = poppinsFamily,
                        color = TealGreen
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = TealGreen,
                    focusedBorderColor = TealGreen,
                    unfocusedBorderColor = TealGreen
                ),
                shape = RoundedCornerShape(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = K,
                onValueChange = { K = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(
                        text = potassiumLabel,
                        fontFamily = poppinsFamily,
                        color = TealGreen
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = TealGreen,
                    focusedBorderColor = TealGreen,
                    unfocusedBorderColor = TealGreen
                ),
                shape = RoundedCornerShape(8.dp),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedText,
                        onValueChange = {
                            rainfall = when (selectedText) {
                                light -> "25.0"
                                moderate -> "50.0"
                                else -> {
                                    "90.0"
                                }
                            }
                        },
                        label = {
                            Text(
                                text = rainfallLabel,
                                fontFamily = poppinsFamily,
                                color = TealGreen
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = TealGreen,
                            focusedBorderColor = TealGreen,
                            unfocusedBorderColor = TealGreen
                        ),
                        shape = RoundedCornerShape(8.dp),
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                selectedText = light
                                expanded = false
                            }
                        ) {
                            Text(text = light, fontFamily = poppinsFamily)
                        }
                        DropdownMenuItem(
                            onClick = {
                                selectedText = moderate
                                expanded = false
                            }
                        ) {
                            Text(text = moderate, fontFamily = poppinsFamily)
                        }
                        DropdownMenuItem(
                            onClick = {
                                selectedText = high
                                expanded = false
                            }
                        ) {
                            Text(text = high, fontFamily = poppinsFamily)
                        }
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    // Check for internet connectivity
                    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetwork = connectivityManager.activeNetworkInfo
                    val isConnected = activeNetwork?.isConnected == true

                    if (!isConnected) {
                        Toast.makeText(context, noInternetMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.reset()
                        viewModel.N = N.toFloat()
                        viewModel.P = P.toFloat()
                        viewModel.K = K.toFloat()
                        viewModel.pH = pH.toFloat()
                        viewModel.rainfall = rainfall.toFloat()

                        if (viewModel.pH <= 0.0 || viewModel.pH > 14.0)
                            Toast.makeText(context, enterValidPh, Toast.LENGTH_SHORT).show()
                        else if (viewModel.N <= 0.0)
                            Toast.makeText(context, enterValidN, Toast.LENGTH_SHORT).show()
                        else if (viewModel.P <= 0.0)
                            Toast.makeText(context, enterValidP, Toast.LENGTH_SHORT).show()
                        else if (viewModel.K <= 0.0)
                            Toast.makeText(context, enterValidK, Toast.LENGTH_SHORT).show()
                        else {
                            viewModel.getCropRecommendations()
                            navigation.navigate("CropScreen")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Wattle),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Text(text = getButton, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }

//    Row(
//        Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start
//    ) {
//        IconButton(onClick = { navigation.popBackStack() }) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_back),
//                contentDescription = "Go back to homescreen",
//                tint = TealGreen
//            )
//        }
//        Text(
//            text = "Get Crop",
//            fontFamily = poppinsFamily,
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Normal
//        )
//    }


}

