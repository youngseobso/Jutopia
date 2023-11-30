package com.ssafy.stockserver.domain.stock.entity;

import com.ssafy.stockserver.common.IndexType;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STOCK_ID")
    private UUID id;

    private String stockCode;
    private String stockName;

//    private String stockFullnumber;
//
//    private String country;
//    private String market;
//    private String sector;
//    private String sectorCode;
//    private IndexType indexType;


}
