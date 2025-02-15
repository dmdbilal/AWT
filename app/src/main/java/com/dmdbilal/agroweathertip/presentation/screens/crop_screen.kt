package com.dmdbilal.agroweathertip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.domain.util.LoadAssetImage
import com.dmdbilal.agroweathertip.presentation.CropViewModel
import com.dmdbilal.agroweathertip.ui.theme.DustyGrey
import com.dmdbilal.agroweathertip.ui.theme.RoseWhite
import com.dmdbilal.agroweathertip.ui.theme.TealGreen

@Composable
fun CropScreen(navigation: NavController, viewModel: CropViewModel) {
    val crops by viewModel.crops.collectAsState()
    val state by viewModel.cropState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(RoseWhite),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = TealGreen
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Adds space between the spinner and the text
                    Text(
                        text = stringResource(id = R.string.cropscreen_loading_message),
                        color = TealGreen
                    )
                }
            }
        } else {

            Row(Modifier.fillMaxWidth(), Arrangement.Start) {
                IconButton(onClick = { navigation.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.cropscreen_go_back_to_get_crop_screen),
                        tint = TealGreen
                    )
                }
            }

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.cropscreen_your_top_crop),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                LoadAssetImage(fileName = crops[0].crop, size = 150.dp)
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = if (crops.isEmpty()) " " else crops[0].crop.capitalize(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_temperature),
                        contentDescription = stringResource(id = R.string.cropscreen_temperature),
                        tint = TealGreen
                    )
                    Text(
                        text = "${viewModel.temperature}°C", fontSize = 18.sp, color = TealGreen
                    )
                }

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_waterdrop),
                        contentDescription = stringResource(id = R.string.cropscreen_humidity),
                        tint = TealGreen
                    )
                    Text(
                        text = "${viewModel.humidity}%rh", fontSize = 18.sp, color = TealGreen
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.cropscreen_click_to_see_more),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.LightGray
                )
                IconButton(onClick = { navigation.navigate("TopCrops") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_swipeup),
                        contentDescription = stringResource(id = R.string.cropscreen_swipe_up),
                        tint = DustyGrey
                    )
                }

            }

        }
    }
}
