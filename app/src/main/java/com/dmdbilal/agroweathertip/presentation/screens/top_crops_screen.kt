package com.dmdbilal.agroweathertip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.presentation.CropViewModel
import com.dmdbilal.agroweathertip.presentation.views.Crop
import com.dmdbilal.agroweathertip.ui.theme.RoseWhite
import com.dmdbilal.agroweathertip.ui.theme.TealGreen

@Composable
fun TopCrops(navigation: NavController, viewModel: CropViewModel) {
    val crops by viewModel.crops.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RoseWhite)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navigation.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Go back to get crop screen",
                    tint = TealGreen
                )
            }

            Text(text = stringResource(id = R.string.topcropscreen_top_10_crops), fontSize =24.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn{
            crops.forEachIndexed{index,cropItem ->
                item {
                    Crop(data = cropItem, temp = viewModel.temperature, humd = viewModel.humidity)
                }
            }
        }
    }
}
