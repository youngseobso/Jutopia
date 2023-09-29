package com.ssafy.memberserver.domain.notice.dto.response;

public record NoticeUpdateResponse(
        boolean noticeUpdateResult
) {
    public static NoticeUpdateResponse of(boolean noticeUpdateResult){
        return new NoticeUpdateResponse(noticeUpdateResult);
    }
}
