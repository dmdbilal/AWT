package com.dmdbilal.agroweathertip.presentation.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmdbilal.agroweathertip.ui.theme.RoseWhite
import com.dmdbilal.agroweathertip.ui.theme.TealGreen
import com.dmdbilal.agroweathertip.ui.theme.shapes.SpeechBubbleShape
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.dmdbilal.agroweathertip.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessage(message: String) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val current = LocalDateTime.now().format(formatter)

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(SpeechBubbleShape(tipSize = 5.dp))
            .background(TealGreen),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .offset(x = 6.dp, y = 0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Mohamed Bilal D",
                fontSize = 10.sp,
                color = RoseWhite,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = message,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessages(messages: List<String>) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(8.dp),

        ) {
        for (message in messages) {
            ChatMessage(message)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
