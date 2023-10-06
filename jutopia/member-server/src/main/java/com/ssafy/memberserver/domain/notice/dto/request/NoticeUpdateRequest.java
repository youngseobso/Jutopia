package com.ssafy.memberserver.domain.notice.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeUpdateRequest {
    private String title;
    private String newTitle;
    private String content;
    private String newContent;
    private LocalDateTime updatedAt;
}