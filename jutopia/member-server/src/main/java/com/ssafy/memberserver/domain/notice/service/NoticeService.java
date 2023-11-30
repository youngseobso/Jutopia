package com.ssafy.memberserver.domain.notice.service;

import com.ssafy.memberserver.domain.notice.dto.request.NoticeDeleteRequest;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeRequest;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeUpdateRequest;
import com.ssafy.memberserver.domain.notice.dto.response.NoticeDeleteResponse;
import com.ssafy.memberserver.domain.notice.dto.response.NoticeResponse;
import com.ssafy.memberserver.domain.notice.dto.response.NoticeUpdateResponse;
import com.ssafy.memberserver.domain.notice.entity.Notice;
import com.ssafy.memberserver.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponse getNotice(Long id){
        return noticeRepository.findById(id)
                .map(NoticeResponse::from)
                .map(response -> {
                    noticeRepository.updateViewCount(id);
                    return response;
                })
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공지사항입니다."));
    }
    @Transactional(readOnly = true)
    public List<NoticeResponse> getNotices(String school, int grade, int classroom){
        List<Notice> notices = noticeRepository.findBySchoolAndGradeAndClassroom(school, grade, classroom);
        return notices.stream().map(NoticeResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public NoticeResponse noticeWrite(NoticeRequest noticeRequest){
        Notice notice = noticeRepository.save(Notice.from(noticeRequest));
        noticeRepository.flush();
        return NoticeResponse.from(notice);
    }
    @Transactional
    public NoticeUpdateResponse noticeUpdate(NoticeUpdateRequest noticeUpdateRequest, Long id){
        return noticeRepository.findById(id)
                .map(notice -> {
                    notice.update(noticeUpdateRequest);
                    return NoticeUpdateResponse.of(true);
                })
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공지사항 입니다."));
    }
    @Transactional
    public NoticeDeleteResponse noticeDelete(NoticeDeleteRequest noticeDeleteRequest, Long id){
        return noticeRepository.findById(id)
                .map(notice -> {
                    notice.delete(noticeDeleteRequest);
                    return NoticeDeleteResponse.of(true);
                })
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공지사항입니다."));
    }
}
