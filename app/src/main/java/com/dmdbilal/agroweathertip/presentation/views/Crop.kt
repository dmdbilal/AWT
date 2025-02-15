package com.dmdbilal.agroweathertip.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.domain.CropData
import com.dmdbilal.agroweathertip.domain.util.LoadAssetImage
import com.dmdbilal.agroweathertip.ui.theme.TealGreen
import com.dmdbilal.agroweathertip.ui.theme.poppinsFamily

@Composable
fun Crop(data: CropData, temp: Float, humd: Float) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadAssetImage(fileName = data.crop, size = 40.dp)
//            Image(
//                painter = painterResource(id = R.drawable.crop_beans),
//                contentDescription = "crop",
//                Modifier
//                    .size(40.dp)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Fit
//            )
            Spacer(modifier = Modifier.padding(8.dp))
            Column{
                Text(text=data.crop.capitalize(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

        Row {
            Column {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_temperature),
                        contentDescription = "Location Icon",
                        tint = TealGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = "$temp°C", fontFamily = poppinsFamily, fontSize = 15.sp, color = TealGreen)
                }

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_waterdrop),
                        contentDescription = "Location Icon",
                        tint = TealGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = "$humd%rh", fontFamily = poppinsFamily, fontSize = 15.sp, color = TealGreen)
                }
            }
        }

    }
    Divider(thickness = 1.5.dp)
}
