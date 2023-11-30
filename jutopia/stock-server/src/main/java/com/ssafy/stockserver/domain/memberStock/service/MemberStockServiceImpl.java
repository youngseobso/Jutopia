package com.ssafy.stockserver.domain.memberStock.service;

import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import com.ssafy.stockserver.domain.memberStock.repository.MemberStockRepository;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.trading.entity.TradeType;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@Slf4j
public class MemberStockServiceImpl implements MemberStockService{

    MemberStockRepository memberStockRepository;

    @Autowired
    public MemberStockServiceImpl(MemberStockRepository memberStockRepository) {
        this.memberStockRepository = memberStockRepository;
    }

    @Override
    public Iterable<MemberStock> getMemberStocks(UUID memberId) {
        return memberStockRepository.findAllByMemberId(memberId);
    }

    @Override
    public void updateStock(Trading trade) {
        System.out.println("udpateSTock: " + trade);
        Optional<MemberStock> memberStock =
                memberStockRepository.findByMemberIdAndStockId(trade.getMemberId(), trade.getStock().getId());

        // 매수일때
        if(trade.getType() == TradeType.BUY) {
            // 만약 해당 주식을 처음 구매하는거라면
            if (!memberStock.isPresent()) {
                MemberStock newMemStock = new MemberStock(
                        trade.getMemberId(),
                        trade.getVolume(),
                        trade.getTotalPrice(),
                        trade.getPrice(),
                        trade.getStock()
                );
                memberStockRepository.save(newMemStock);
                return;
            }

            // 해당 주식이 있으면 매수로 인한 평단가 조정
            MemberStock newMemStock = memberStock.get();

            Long upQty = newMemStock.getQty() + trade.getVolume();
            BigDecimal upTotalPrice = newMemStock.getTotalPrice().add(trade.getTotalPrice());
            BigDecimal upAvgPrice = upTotalPrice.divide(BigDecimal.valueOf(upQty), 2, RoundingMode.HALF_UP);


            newMemStock.update(upQty, upTotalPrice, upAvgPrice);

            memberStockRepository.save(newMemStock);
            return;
        }

        // 매도일때
        if(trade.getType() == TradeType.SELL) {
            // 해당 주식이 있으면 매수로 인한 평단가 조정
            MemberStock newMemStock = memberStock.get();

            Long upQty = newMemStock.getQty() - trade.getVolume();
            // 수량이 0이면 삭제
            if (upQty == 0) {
                memberStockRepository.delete(newMemStock);
                return;
            }
            BigDecimal upAvgPrice = newMemStock.getTotalPrice().divide(BigDecimal.valueOf(upQty));

            newMemStock.update(upQty, newMemStock.getTotalPrice(), upAvgPrice);

            memberStockRepository.save(newMemStock);
        }
    }

    @Override
    public Optional<MemberStock> getMemberOneStock(UUID memberId, UUID stockId) {
        return memberStockRepository.findByMemberIdAndStockId(memberId, stockId);
    }

}
