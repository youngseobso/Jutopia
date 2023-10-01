package com.ssafy.stockserver.domain.stock.service;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockService {

    List<ResponseStock> getAllStocks();

    ResponseStock createStock(RequestStock stock);

    Optional<Stock> getStock(UUID stockId);
}
