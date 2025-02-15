package com.dmdbilal.agroweathertip.domain.util

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadAssetImage(fileName: String, size: Dp) {
    val context = LocalContext.current
    val imageBitmap = remember(fileName) {
        try {
            val inputStream = context.assets.open("crops/$fileName.jpg")
            BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
        } catch (e: Exception) {
            null // Handle exception if the image is not found
        }
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "Image of $fileName",
            modifier = Modifier.size(size).clip(CircleShape), // Adjust size as needed
            contentScale = ContentScale.Crop
        )
    } else {
        Text("Image not found")
    }
}
