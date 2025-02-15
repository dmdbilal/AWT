package com.dmdbilal.agroweathertip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.ui.theme.RoseWhite
import com.dmdbilal.agroweathertip.ui.theme.TealGreen
import com.dmdbilal.agroweathertip.ui.theme.poppinsFamily

@Composable
fun HelpScreen(navigation: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(RoseWhite),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
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
            Text(text = "pH using pH strip", fontFamily = poppinsFamily,
                fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Column {
                Text(text = "Guidelines", fontFamily = poppinsFamily, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)

                Spacer(modifier = Modifier.padding(16.dp))

                Text(
                    text = "1. Collect a small soil sample.\n\n" +
                            "2. Mix the soil sample with water in a clean container.\n\n" +
                            "3. Dip the pH strip into the soil-water mixture.\n\n" +
                            "4. Wait for a few seconds for the color to develop.\n\n" +
                            "5. Compare the strip's color with the  provided pH color chart.\n\n" +
                            "6. Identify the pH value corresponding to the color match.\n\n" +
                            "7. Repeat the process for multiple soil samples to ensure accuracy.",
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    ),
                    color = TealGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }
}
