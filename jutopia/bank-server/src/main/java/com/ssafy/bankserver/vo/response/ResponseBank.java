package com.ssafy.bankserver.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBank  {
    private String bankId;
    private String bankName;
    private BigDecimal money;
    private Double exchangeTaxRate;
    private Double capitalGainsTaxRate;
    private Double tradeTaxRate;
    private Double consumptionTaxRate;

    private UUID classId;
}
