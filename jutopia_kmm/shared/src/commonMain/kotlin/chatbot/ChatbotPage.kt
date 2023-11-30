package chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

private val log = Logger.withTag("ChatbotPage")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatbotPage(
    viewModel: ChatbotViewModel = viewModel(modelClass = ChatbotViewModel::class) {
        ChatbotViewModel()
    }, modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val messages by viewModel.messages.collectAsState()
    val isSending by viewModel.isSending.collectAsState()
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFEBF9FF),
            Color(0xFFDAF3FF)
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, 1000f)
    )
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size - 1)
    }


    Box(modifier = Modifier.fillMaxSize().background(brush = gradient)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
            ) {
                items(messages) { message ->
                    ChatMessageRow(message)
                }
            }

            var newMessage by remember { mutableStateOf("") }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color(0xFFDAF3FF))
                    .height(56.dp)
            ) {
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    modifier = Modifier.weight(1f),
                    label = { Text(text = "채팅을 입력해주세요") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (newMessage.isNotEmpty() && !isSending) {
                                viewModel.viewModelScope.launch {
                                    viewModel.addAndRespond(newMessage)
                                }
                                newMessage = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors()
                )
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.addAndRespond(newMessage)
                        }
                        newMessage = ""
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    enabled = !isSending && newMessage.isNotEmpty(),
                    modifier = Modifier.width(80.dp).fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC3E0E8)
                    )

                ) {
                    if (isSending) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text("전송")
                    }
                }
            }
        }
    }
}


@Composable
fun ChatMessageRow(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = if (message.fromServer) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = if (message.fromServer) Color.White else Color(0xFFC3E0E8),
                    shape = RoundedCornerShape(8.dp)
                )

        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                Text(message.message, color = Color.Black)
                Text(
                    message.parsedTime,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }
        }
    }
}

