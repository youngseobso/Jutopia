package com.ssafy.memberserver.common.api;

public record ApiResponse(
        ApiStatus apiStatus,
        String message,
        Object data
) {
    public static ApiResponse success(Object data){
        return new ApiResponse(ApiStatus.SUCCESS,null,data);
    }
    public static ApiResponse error(String message){
        return new ApiResponse(ApiStatus.ERROR,message,null);
    }
}
