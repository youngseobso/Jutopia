package com.ssafy.teacher.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
public class Response<T> {

    private Result result;
    private T body;

    @Getter
    @Setter
    public static class Result {
        private int result_code;
        private String result_message;
        private String result_description;
    }
}
