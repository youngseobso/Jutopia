package com.ssafy.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum RentErrorCode implements ErrorCodeIfs {

    OK(HttpStatus.OK.value(), 200, "성공"),
    CREATED(HttpStatus.CREATED.value(), 201, "생성 완료"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청입니다"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버에러 입니다."),
    FAIL(HttpStatus.BAD_REQUEST.value(), 1001, "다른 사람이 먼저 신청한 좌석입니다."),
    POINT_LACK(HttpStatus.BAD_REQUEST.value(), 1002, "포인트가 부족합니다.");



    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
