package com.ssafy.memberserver.domain.notice.entity;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeDeleteRequest;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeRequest;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeUpdateRequest;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long viewCount;
    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;
    private String school;
    private int grade;
    private int classroom;

    public static Notice from(NoticeRequest noticeRequest){
        return Notice.builder()
                .id(0L)
                .title(noticeRequest.getTitle())
                .content(noticeRequest.getContent())
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .viewCount(0L)
                .noticeStatus(NoticeStatus.ACTIVE)
                .school(noticeRequest.getSchool())
                .grade(noticeRequest.getGrade())
                .classroom(noticeRequest.getClassroom())
                .build();
    }

    public void update(NoticeUpdateRequest noticeUpdateRequest) {
        if (noticeUpdateRequest.getNewTitle() != null) {
            this.title = noticeUpdateRequest.getNewTitle();
        }
        if (noticeUpdateRequest.getNewContent() != null) {
            this.content = noticeUpdateRequest.getContent();
        }
        this.updateAt = LocalDateTime.now();
    }
    public void delete(NoticeDeleteRequest noticeDeleteRequest){
        if(noticeDeleteRequest.getNoticeStatus() == NoticeStatus.ACTIVE){
            this.noticeStatus = NoticeStatus.INACTIVE;
        }
    }
}
