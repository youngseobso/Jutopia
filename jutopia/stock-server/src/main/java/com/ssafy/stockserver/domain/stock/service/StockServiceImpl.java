package com.ssafy.stockserver.domain.stock.service;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.client.NewsServerClient;
import com.ssafy.stockserver.domain.client.ResponseFeignStock;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockService;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockServiceImpl;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.repository.StockRepository;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class StockServiceImpl implements StockService{
    ModelMapper mapper;
    StockRepository stockRepository;
    NewsServerClient newsServerClient;
    MemberStockService memberStockService;
    @Autowired
    public StockServiceImpl(StockRepository stockRepository, NewsServerClient newsServerClient, MemberStockService memberStockService) {
        this.stockRepository = stockRepository;
        this.newsServerClient = newsServerClient;
        this.memberStockService = memberStockService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public List<ResponseStock> getAllStocks(UUID userId) {
        Iterable<Stock> stock = stockRepository.findAll();
        List<ResponseStock> resList = new ArrayList<>();
        var MyStocks = memberStockService.getMemberStocks(userId);
        Set<String> ownedStockCode = new HashSet<>();
        MyStocks.forEach(s -> ownedStockCode.add(s.getStock().getStockCode()));

        // news-server 로부터 실시간 주식 데이터 가져와 가공
        List<ResponseFeignStock> res = newsServerClient.getStocks();

        // response 객체 가공
        stock.forEach(s -> {
            ResponseStock newStock = mapper.map(s, ResponseStock.class);

            Optional<ResponseFeignStock> tmp = res.stream().filter(e -> e.getStockName().equals(s.getStockName())).findFirst();
            BigDecimal nowMoney = new BigDecimal(tmp.get().getPrice());

            newStock.setNowMoney(nowMoney);
            newStock.setChangeMoney(new BigDecimal(tmp.get().getChangeMoney()));
            newStock.setChangeRate(tmp.get().getChangeRate());
            newStock.setType(tmp.get().getType());

            if (ownedStockCode.contains(s.getStockCode())) {
                newStock.setIsOwnedByUser(true);
            } else {
                newStock.setIsOwnedByUser(false);
            }

            resList.add(newStock);
        });
        return resList;
    }

    @Override
    public ResponseStock createStock(RequestStock stock) {
        Stock returnStock = stockRepository.save(mapper.map(stock, Stock.class));
        return mapper.map(returnStock, ResponseStock.class);
    }

    @Override
    public ResponseStock getStock(UUID stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);

        if (!stock.isPresent()) return null;

        // Optional이 값으로 채워져 있다면 매핑을 수행
        ResponseStock responseStock = mapper.map(stock.get(), ResponseStock.class);

        ResponseFeignStock feignStock = newsServerClient.getOneStock(responseStock.getStockCode());


        responseStock.setNowMoney(new BigDecimal(feignStock.getPrice()));
        responseStock.setChangeMoney(new BigDecimal(feignStock.getChangeMoney()));
        responseStock.setChangeRate(feignStock.getChangeRate());
        responseStock.setType(feignStock.getType());

        return responseStock;
    }

    @Override
    public Optional<Stock> getOneStock(UUID stockId) {
        return stockRepository.findById(stockId);
    }
}
