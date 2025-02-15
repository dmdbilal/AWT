package com.dmdbilal.agroweathertip.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.presentation.views.ChatMessages
import com.dmdbilal.agroweathertip.ui.theme.RoseWhite
import com.dmdbilal.agroweathertip.ui.theme.TealGreen
import com.dmdbilal.agroweathertip.ui.theme.TealGreen2
import com.dmdbilal.agroweathertip.ui.theme.poppinsFamily

@SuppressLint("NewApi")
@Composable
fun ChatScreen(navigation: NavController) {
    var messages by remember {
        mutableStateOf(listOf<String>())
    }
    var message by remember {
        mutableStateOf("")
    }

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
                        text = stringResource(id = R.string.communityscreen_title),
                        fontFamily = poppinsFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(RoseWhite)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                ChatMessages(messages = messages)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                    ,
                    value = message,
                    onValueChange = { message = it },
                    placeholder = {Text(stringResource(id = R.string.communityscreen_message), color = TealGreen)},
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = TealGreen2,
                        focusedIndicatorColor = TealGreen
                    ),
                    singleLine = false,
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                if (message.isNotBlank()) {
                                    messages = messages + listOf(message)
                                    message = ""
                                }
                            },
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            tint = TealGreen
                        )
                    }
                )
            }
        }
    }
}
