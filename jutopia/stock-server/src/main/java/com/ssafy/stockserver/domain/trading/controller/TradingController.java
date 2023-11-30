package com.ssafy.stockserver.domain.trading.controller;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.client.MemberPointUpdateRequest;
import com.ssafy.stockserver.domain.client.MemberServerClient;
import com.ssafy.stockserver.domain.client.ResponseMember;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockService;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.service.StockService;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import com.ssafy.stockserver.domain.trading.entity.TradeType;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import com.ssafy.stockserver.domain.trading.service.TradingService;
import com.ssafy.stockserver.domain.trading.vo.request.RequestTrade;
import com.ssafy.stockserver.domain.trading.vo.response.ResponseTrade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/stock-server/api/trade")
@Tag(name = "Trading", description = "실시간 주식 거래")
public class TradingController {

    TradingService tradingService;
    StockService stockService;
    MemberServerClient memberServerClient;
    MemberStockService memberStockService;
    ModelMapper mapper;

    @Autowired
    public TradingController(TradingService tradingService, StockService stockService,
                             MemberServerClient memberServerClient, MemberStockService memberStockService) {
        this.tradingService = tradingService;
        this.stockService = stockService;
        this.memberServerClient = memberServerClient;
        this.memberStockService = memberStockService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PostMapping("/")
    public Api<ResponseTrade> createTrade(@RequestBody RequestTrade requestTrade) {
        Trading trade = mapper.map(requestTrade, Trading.class);
        trade.setTotalPrice(requestTrade.getPrice().multiply(BigDecimal.valueOf(requestTrade.getVolume())));

        Optional<Stock> stock = stockService.getOneStock(requestTrade.getStockId());

        if (!stock.isPresent()) return Api.NOT_FOUND(null); // 예: 404 Not Found

        trade.setStock(stock.get());
        trade.setMemberId(requestTrade.getMemberId());

        // 멤버 보유 포인트 정보를 요청한다 (Member-server)
        ResponseMember member = mapper.map(memberServerClient.getStudent(requestTrade.getMemberId()).data(), ResponseMember.class);

        if(requestTrade.getType() == TradeType.BUY && trade.getTotalPrice().compareTo(member.getPoint()) > 0) {
            // 매수인데 포인트가 부족한 경우
            return Api.BAD_REQUEST(null, "포인트가 부족합니다.");
        }

        if (requestTrade.getType() == TradeType.SELL) {
            // 매도인 경우
            trade.setTotalPrice(trade.getTotalPrice().negate());
        }

        // 주식 주문
        trade = tradingService.save(trade);
        // 학생 포인트 변동 요청
        MemberPointUpdateRequest requestMember = new MemberPointUpdateRequest(requestTrade.getMemberId(), trade.getTotalPrice());
        memberServerClient.memberPointUpdate(requestMember);
        // 학생 보유 주식 종목 업데이트
        // 프론트에서 보유 종목 있는걸 걸렀다 전재하에 작성
        memberStockService.updateStock(trade);



        // 결과 반환
        ResponseTrade result = mapper.map(trade, ResponseTrade.class);
        result.setStockName(stock.get().getStockName());
        result.setStockCode(stock.get().getStockCode());
        return Api.CREATED(result);
    }
}
