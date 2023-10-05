package com.ssafy.teacher.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class NoticeRequest {
    private String title;
    private String content;
    private String school;
    private int grade;
    private int classroom;
}
