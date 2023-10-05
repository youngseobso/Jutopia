package com.ssafy.memberserver.domain.notice.dto.request;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeRequest {
    private String title;
    private String content;
    private String school;
    private int grade;
    private int classroom;
}
