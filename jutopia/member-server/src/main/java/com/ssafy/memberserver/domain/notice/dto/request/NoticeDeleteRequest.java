package com.ssafy.memberserver.domain.notice.dto.request;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import lombok.Getter;

@Getter
public class NoticeDeleteRequest{
    private NoticeStatus noticeStatus;
}
