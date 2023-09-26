package com.ssafy.memberserver.domain.notice.dto.request;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;

import java.time.LocalDateTime;

public record NoticeRequest(
        Long id,
        String title,
        String content,
        Long viewCount,
        NoticeStatus noticeStatus,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
