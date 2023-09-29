package com.ssafy.memberserver.domain.notice.dto.request;

import java.time.LocalDateTime;

public record NoticeUpdateRequest(
        String title,
        String newTitle,
        String content,
        String newContent,
        LocalDateTime updatedAt
) {
}
