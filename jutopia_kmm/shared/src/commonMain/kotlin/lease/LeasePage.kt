package lease

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import moe.tlaster.precompose.viewmodel.viewModel
import co.touchlab.kermit.Logger
object Variables {
    val Brown50: Color = Color(0xFFF6F3F0)
    val Brown200: Color = Color(0xFFD3C4B5)
}
private val log = Logger.withTag("LeasePage")
@Composable
fun LeasePage(
    viewModel: LeaseViewModel = viewModel(modelClass = LeaseViewModel::class) {
        LeaseViewModel()
    }, modifier: Modifier = Modifier
) {
    val seats by viewModel.seats.collectAsState()
    val selectedSeat by viewModel.selectedSeat.collectAsState()
    val apiResponse by viewModel.apiResponse.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 20.dp, bottom = 20.dp),
    ) {
        // 박스로 칠판과 좌석을 감싼다
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(420.dp)
                .align(Alignment.TopCenter)
                .shadow(
                    elevation = 15.dp,
                    spotColor = Color(0x000000),
                    ambientColor = Color(0x000000)
                )
                .background(color = Variables.Brown50, shape = RoundedCornerShape(size = 20.dp))
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
                    .background(
                        color = Variables.Brown200,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .align(Alignment.TopCenter)
            ) {
                Text("칠판", color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
            Spacer(modifier = Modifier.height(100.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 15.dp)
            ) {
                items(seats.chunked(4)) { rowSeats ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowSeats.forEach { seat ->
                            SeatView(seat, viewModel)
                            Spacer(modifier = Modifier.width(15.dp))
                        }
                    }
                }
            }
        }

        if (selectedSeat != null) {
            val status = selectedSeat!!.seatStatus
            Dialog(onDismissRequest = { }) {
                // 박스로 전체 Dialog 레이아웃을 감쌈
                Box(
                    contentAlignment = Alignment.Center, // 가운데 정렬
                    modifier = Modifier
                        .background(
                            color = Color(0xFFEBF5F7),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // 가운데 정렬
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("자리 정보", textAlign = TextAlign.Center)
                        Text("자리: ${selectedSeat?.position ?: ""}")
                        Text("가격: ${selectedSeat?.price ?: ""}")
                        Text("구매하시겠습니까?")
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // 취소 버튼
                            Button(
                                onClick = { viewModel.clearSelectedSeat() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFC3E0E8)
                                ),
                                shape = RoundedCornerShape(size = 10.dp),

                                ) {
                                Text("취소")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            // 신청 버튼
                            Button(
                                onClick = {
                                    log.i { status }
                                    if (status=="AVAILABLE") {
                                        viewModel.reserveSeat(selectedSeat!!.id)
                                        viewModel.clearSelectedSeat()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFC3E0E8)
                                ),
                                shape = RoundedCornerShape(size = 10.dp),
                                enabled = status.equals("AVAILABLE")
                            ) {
                                Text("신청")
                            }
                        }
                    }
                }
            }
        }

        ShowDialogBasedOnApiResponse(apiResponse, viewModel)

    }

}

@Composable
fun SeatView(seat: Seat, viewModel: LeaseViewModel) {
    val backgroundColor = if (seat.seatStatus == "AVAILABLE") Variables.Brown200 else Color.Gray
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(size = 10.dp))
            .clickable {
                viewModel.selectSeat(seat) }

    ) {
        Text(
            text = "${seat.position}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ShowSuccessMessage() {
    Text("신청이 성공적으로 완료되었습니다.")
}

@Composable
fun ShowInsufficientPointsMessage() {
    Text("포인트가 부족합니다.")
}

@Composable
fun ShowServerErrorMessage() {
    Text("서버 에러가 발생했습니다.")
}

@Composable
fun ShowDialogBasedOnApiResponse(apiResponse: ApiResponse?, viewModel: LeaseViewModel) {
    val showDialog by viewModel.showDialog.collectAsState()
    val dialogMessage = remember { mutableStateOf("") }

    when (apiResponse) {
        is ApiResponse.Success -> {
            dialogMessage.value = "신청 완료"
            viewModel.toggleDialog(true)
        }
        is ApiResponse.InsufficientPoints -> {
            dialogMessage.value = "포인트가 부족합니다"
            viewModel.toggleDialog(true)
        }
        is ApiResponse.ServerError -> {
            dialogMessage.value = "서버 에러 발생"
            viewModel.toggleDialog(true)
        }
        null -> Unit
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.toggleDialog(false)  // Dialog를 닫기 위해 상태 변경
            },
            title = {
                Text("알림")
            },
            text = {
                Text(dialogMessage.value)
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.toggleDialog(false)
                    viewModel.clearApiResponse()
                }) {
                    Text("확인")
                }
            }
        )
    }
}
