package com.ssafy.teacher.dto.rent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SeatStatus {
    AVAILABLE("사용가능"),
    UNAVAILABLE("사용불가"),
    INUSE("사용중"),
    DELETED("삭제");

    private final String description;
}
