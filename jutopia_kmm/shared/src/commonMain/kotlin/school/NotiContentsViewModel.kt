package school

import moe.tlaster.precompose.viewmodel.ViewModel

class NotiContentsViewModel(private val idx: Int): ViewModel() {
    private val _notice: noticeDetail = noticeDetail("칠판당번 공지", "금주의 칠판당번은 xxx입니다", "2023.09.07", if (idx == 0) "10:00" else "10:59")

    val notice: noticeDetail = _notice
}