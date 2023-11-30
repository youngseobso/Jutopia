package com.ssafy.memberserver.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

    OK(HttpStatus.OK.value(), 200, "성공"),
    CREATED(HttpStatus.CREATED.value(), 201, "생성 완료"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청입니다"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버에러 입니다."),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 513, "Null Point"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), 400, "유효하지 않은 input 입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "해당 값은 없습니다."),

    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404,"존재하지 않는 학생입니다."),
    STUDENT_INVALID_INPUT(HttpStatus.UNAUTHORIZED.value(), 401,"아이디 또는 비밀번호가 틀렸습니다."),
    STUDENT_POINT_ERROR(HttpStatus.BAD_REQUEST.value(), 1002,"포인트가 부족합니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
