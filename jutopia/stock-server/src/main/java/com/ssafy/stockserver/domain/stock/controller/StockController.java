package com.ssafy.stockserver.domain.stock.controller;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockService;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.service.StockService;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stock-server/api/stock")
@Tag(name = "Stock", description = "주식 종목 조회")
public class StockController {

    StockService stockService;
    MemberStockService memberStockService;
    ModelMapper mapper;

    @Autowired
    public StockController(StockService stockService, MemberStockService memberStockService) {
        this.stockService = stockService;
        this.memberStockService = memberStockService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @GetMapping("/stocklist/{userId}")
    public Api<List<ResponseStock>> getAllStocks(@PathVariable UUID userId) {
        List<ResponseStock> stockList = stockService.getAllStocks(userId);
        return Api.OK(stockList);
    }

    @PostMapping("/")
    public Api<ResponseStock> createStock(@RequestBody RequestStock stock) {
        return Api.OK(stockService.createStock(stock));
    }

    @GetMapping("/{stockId}")
    public Api<ResponseStock> getStock(@PathVariable UUID stockId) {
        ResponseStock stock = stockService.getStock(stockId);
        if (stock == null) return Api.NOT_FOUND(null);

        return Api.OK(stock);
    }
}
