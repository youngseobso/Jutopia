package com.ssafy.rentserver.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SeatStatus {
    AVAILABLE("사용가능"),
    UNAVAILABLE("사용불가"),
    INUSE("사용중");

    private final String description;
}
