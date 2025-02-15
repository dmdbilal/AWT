package com.dmdbilal.agroweathertip.presentation.screens

import android.provider.Settings.Global.getString
import android.provider.Settings.System.getString
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.domain.sign_in.UserData
import com.dmdbilal.agroweathertip.ui.theme.*
import java.util.*

@Composable
fun HomeScreen(
    navigation: NavController,
    userData: UserData?
) {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(RoseWhite)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DynamicGreeting()

                AsyncImage(
                    model = userData?.profilePicUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { navigation.navigate("ProfileScreen") },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            var painter = painterResource(id = R.drawable.forecast_cc_28)
            Image(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height)
                    .fillMaxWidth(),
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                context.getString(R.string.homescreen_getstarted),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                color = TealGreen2,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.get_crop_btn),
                        contentDescription = "Get Crop",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigation.navigate("GetCropScreen") }
                    )
                    Text(
                        text = context.getString(R.string.homescreen_getcrop_btn),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.agrona_btn),
                        contentDescription = "Agrona Button",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigation.navigate("BotScreen") }
                    )
                    Text(
                        text = context.getString(R.string.homescreen_agrona_btn),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            painter = painterResource(id = R.drawable.community_btn)
            Box(modifier = Modifier.weight(1f, fill = false)) {
                Image(
                    modifier = Modifier
                        .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height)
                        .fillMaxWidth()
                        .clickable { navigation.navigate("ChatScreen") },
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = context.getString(R.string.homescreen_community_btn),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun DynamicGreeting() {
    val context = LocalContext.current
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        currentHour in 5..11 -> context.getString(R.string.homescreen_goodmorning)
        currentHour in 12..16 -> context.getString(R.string.homescreen_goodafternoon)
        currentHour in 17..20 -> context.getString(R.string.homescreen_goodevening)
        else -> context.getString(R.string.homescreen_goodnight)
    }

    Text(
        text = greeting,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        color = TealGreen2,
        fontSize = 22.sp
    )
}