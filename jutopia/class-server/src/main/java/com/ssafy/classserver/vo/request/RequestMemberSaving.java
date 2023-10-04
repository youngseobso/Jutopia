package com.ssafy.classserver.vo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestMemberSaving {
    @NotNull(message = "납입할 금액을 설정하세요")
    @Min(value = 0, message = "최소 금액은 0 이상이어야 합니다.")
    private BigDecimal money;
}
