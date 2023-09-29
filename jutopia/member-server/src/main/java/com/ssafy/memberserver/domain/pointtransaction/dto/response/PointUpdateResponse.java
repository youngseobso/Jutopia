package com.ssafy.memberserver.domain.pointtransaction.dto.response;

public record PointUpdateResponse(
        boolean PointUpdateResponseResult
) {
    public static PointUpdateResponse of(boolean pointUpdateResponseResult){
        return new PointUpdateResponse(pointUpdateResponseResult);
    }
}
