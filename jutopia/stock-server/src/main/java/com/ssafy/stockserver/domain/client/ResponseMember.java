package com.ssafy.stockserver.domain.client;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ResponseMember {
    private String id;
    private String student_id;
    private String student_name;
    private BigDecimal point;
    private BigDecimal money;
}
