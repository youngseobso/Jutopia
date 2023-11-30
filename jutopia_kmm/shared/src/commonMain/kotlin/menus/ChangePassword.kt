package menus

import BottomTabBar
import UserInfo
import Variables.ColorsOnPrimary
import Variables.ColorsOnPrimaryVariant
import Variables.ColorsPrimary
import Variables.ColorsPrimaryVariant
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.TopPageBar
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import pathTo

@Composable
fun ChangePassword(navigator: Navigator) {

    val coroutineScope = rememberCoroutineScope()

    val store: KStore<UserInfo> = storeOf(filePath = pathTo("user"))

    var userInfo by remember { mutableStateOf<UserInfo?>(null) }

    coroutineScope.launch {
        userInfo = store.get()
    }

    if (userInfo != null)
    {
        Column {
            TopPageBar("비밀번호 변경", navigator)

            Contents(userInfo!!.id)
        }
    }
    BottomTabBar(navigator, 5)
}

@Composable
fun Contents(id: String) {

    val openDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }

    when {
        openDialog.value -> {
            AlertDialog(title = {Text(dialogTitle.value)}, buttons = {
                                                                     Row (
                                                                         horizontalArrangement = Arrangement.Center,
                                                                         modifier = Modifier
                                                                             .fillMaxWidth()

                                                                     ) {
                                                                         Button(
                                                                             onClick = { openDialog.value = false },
                                                                             colors = ButtonDefaults.buttonColors(backgroundColor = ColorsPrimaryVariant)
                                                                         ) { Text("확인", color = ColorsOnPrimaryVariant) }
                                                                     }
            }, onDismissRequest = {openDialog.value = false}, backgroundColor = Color.LightGray, modifier = Modifier.shadow(4.dp))
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val password = mutableStateOf("")
    val newPassword = mutableStateOf("")
    val checkPassword = mutableStateOf("")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            modifier = Modifier
                .width(240.dp)
        ) {
            CustomTextField("비밀번호", password, "비밀번호를 입력해주세요")
            CustomTextField("새 비밀번호", newPassword, "비밀번호를 입력해주세요")
            CustomTextField("비밀번호 확인", checkPassword, "비밀번호를 입력해주세요")
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = ColorsPrimary),
                onClick = {
                    coroutineScope.launch {
                        val response = ChangePasswordAPI().changePwd(id, password.value, newPassword.value)

                        if (response) {
                            dialogTitle.value = "비밀번호를 변경하였습니다"
                            openDialog.value = true
                        } else {
                            dialogTitle.value = "비밀번호 변경에 실패하였습니다"
                            openDialog.value = true
                        }
                    }
                }
            ) {
                Text("변경하기", color = ColorsOnPrimary)
            }
        }
    }
}

@Composable
fun CustomTextField(header: String, variable: MutableState<String>, placeholder: String) {
    Text(header)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .border(2.dp, Color.LightGray )
    ) {
        TextField(
            modifier = Modifier
                .width(200.dp),
            value = variable.value,
            onValueChange = { variable.value = it },
            placeholder = { if (variable.value.isEmpty()) Text(placeholder) },
            textStyle = TextStyle(fontSize = 20.sp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}
