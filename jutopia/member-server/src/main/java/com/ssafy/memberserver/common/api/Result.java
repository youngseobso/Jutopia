package com.ssafy.memberserver.common.api;

import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result CREATED() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("생성 완료")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("에러")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)
                .build();
    }


    public static Result NOT_FOUND() {
        return Result.builder()
                .resultCode(ErrorCode.NOT_FOUND.getErrorCode())
                .resultMessage(ErrorCode.NOT_FOUND.getDescription())
                .resultDescription("해당 값은 없습니다")
                .build();
    }

    public static Result BAD_REQUEST(String msg) {
        return Result.builder()
                .resultCode(ErrorCode.BAD_REQUEST.getErrorCode())
                .resultMessage(ErrorCode.BAD_REQUEST.getDescription())
                .resultDescription(msg)
                .build();
    }
}

