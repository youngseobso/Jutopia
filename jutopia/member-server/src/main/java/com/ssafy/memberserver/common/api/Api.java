package com.ssafy.memberserver.common.api;

import com.ssafy.memberserver.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    private T body;

    public static <T> Api<T> OK(T data) {
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    public static <T> Api<T> CREATED(T data) {
        var api = new Api<T>();
        api.result = Result.CREATED();
        api.body = data;
        return api;
    }

    public static <T> Api<T> NOT_FOUND(T data) {
        var api = new Api<T>();
        api.result = Result.NOT_FOUND();
        api.body = data;
        return api;
    }

    public static <T> Api<T> BAD_REQUEST(T data, String msg) {
        var api = new Api<T>();
        api.result = Result.BAD_REQUEST(msg);
        api.body = data;
        return api;
    }

    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs, description);
        return api;
    }
}

