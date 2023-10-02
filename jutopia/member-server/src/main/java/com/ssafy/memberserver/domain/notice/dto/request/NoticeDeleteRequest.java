package com.ssafy.memberserver.domain.notice.dto.request;

import com.ssafy.memberserver.common.enums.NoticeStatus;

public record NoticeDeleteRequest(
        NoticeStatus noticeStatus
) {
}
