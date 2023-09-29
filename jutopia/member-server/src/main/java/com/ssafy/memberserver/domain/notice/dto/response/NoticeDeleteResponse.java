package com.ssafy.memberserver.domain.notice.dto.response;

public record NoticeDeleteResponse(
        boolean noticeDeleteResult
) {
    public static NoticeDeleteResponse of(boolean noticeDeleteResult){
        return new NoticeDeleteResponse(noticeDeleteResult);
    }
}
