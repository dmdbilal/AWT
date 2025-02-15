package com.dmdbilal.agroweathertip.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmdbilal.agroweathertip.R
import com.dmdbilal.agroweathertip.domain.MessageModel
import com.dmdbilal.agroweathertip.presentation.ChatViewModel
import com.dmdbilal.agroweathertip.ui.theme.*

@Composable
fun ChatBotScreen(navigation: NavController, viewModel: ChatViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RoseWhite)
    ) {
        AppHeader(navigation)
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList
        )
        Spacer(modifier = Modifier.height(10.dp))
        MessageInput(
            onMessageSend = {
                viewModel.sendMessage(it)
            }
        )
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier,messageList : List<MessageModel>) {
    if(messageList.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(300.dp),
                painter = painterResource(id = R.drawable.awt_black_2),
                contentDescription = "Icon",
            )

            Text(text = stringResource(R.string.chatbotscreen_ama), fontSize = 20.sp, color = Color.DarkGray)
        }
    }else{
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()){
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role=="model"

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f) // Flexibly adjust space
            ) {
                Box(
                    modifier = Modifier
                        .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                        .padding(
                            start = if (isModel) 8.dp else 50.dp,
                            end = if (isModel) 50.dp else 8.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        )
                        .clip(RoundedCornerShape(12.dp)) // Smaller corner radius
                        .background(color = if (isModel) ForestGreen else DeepJadeGreen)
                        .padding(horizontal = 12.dp, vertical = 8.dp) // Compact internal padding
                ) {
                    SelectionContainer {
                        Text(
                            text = messageModel.message,
                            fontWeight = FontWeight.Medium,
                            color =  Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend : (String)-> Unit) {
    var message by remember {
        mutableStateOf("")
    }

    val SoftPeach = Color(0xFFFFE1C6) // Light peach color that complements RoseWhite

    Row(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .background(SoftPeach),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // OutlinedTextField with soft background
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)  // Ensures text field takes available space
                .padding(5.dp),  // Padding inside the text field
            value = message,
            onValueChange = {
                message = it
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = SoftPeach,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.LightGray
            ),
            shape = MaterialTheme.shapes.large,  // Rounded corners for the text field
            singleLine = true,  // Keeps text input in one line
            placeholder = { Text(text = stringResource(id = R.string.chatbotscreen_message), color = Color.Gray) },  // Optional placeholder text
        )

        // Send button with enhanced styling
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = TealGreen,  // Matching green color for the send icon
                modifier = Modifier.size(28.dp)  // Adjust icon size
            )
        }
    }
}

@Composable
fun AppHeader(navigation: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
            Text(
                modifier = Modifier.padding(0.dp),
                text = stringResource(id = R.string.homescreen_agrona_btn),
                color = TealGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
