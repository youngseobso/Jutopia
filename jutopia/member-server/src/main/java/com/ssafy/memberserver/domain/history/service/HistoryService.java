package com.ssafy.memberserver.domain.history.service;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.history.dto.response.AccountHistoryResponse;
import com.ssafy.memberserver.domain.history.dto.response.HistoryExpenseResponse;
import com.ssafy.memberserver.domain.history.dto.response.HistoryIncomeResponse;
import com.ssafy.memberserver.domain.history.entity.History;
import com.ssafy.memberserver.domain.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;
    @Transactional(readOnly = true)
    public List<AccountHistoryResponse> getAccountHistory(UUID accountId){
        List<History> accountHistories = historyRepository.findByAccountId(accountId);
        return accountHistories.stream()
                .map(AccountHistoryResponse::from)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<HistoryIncomeResponse> getIncome(UUID accountId) {
        List<History> incomeHistories = historyRepository.findByAccountIdAndHistoryType(accountId, HistoryType.INCOME);
        return incomeHistories.stream()
                .map(HistoryIncomeResponse::from)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<HistoryExpenseResponse> getExpense(UUID accountId){
        List<History> expenseHistories = historyRepository.findByAccountIdAndHistoryType(accountId,HistoryType.EXPENSE);
        return expenseHistories.stream()
                .map((HistoryExpenseResponse::from))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public BigDecimal getIncomeMonthly(UUID id, int year, int month){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("없는 계좌번화 입니다."));
        LocalDateTime startDate = LocalDateTime.of(year, month,1,0,0);
        LocalDateTime endDate = startDate.plusMonths(1).withDayOfMonth(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
        List<History> incomeHistories = historyRepository.findByAccount_IdAndHistoryTypeAndCreatedAtBetween(id,HistoryType.INCOME,startDate,endDate);
        BigDecimal totalIncome = incomeHistories.stream()
                .map(History::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        return totalIncome;
    }
    @Transactional(readOnly = true)
    public BigDecimal getExpenseMonthly(UUID id, int year, int month){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("없는 계좌임"));
        LocalDateTime startDate = LocalDateTime.of(year, month,1,0,0);
        LocalDateTime endDate = startDate.plusMonths(1).withDayOfMonth(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
        List<History> incomeHistories = historyRepository.findByAccount_IdAndHistoryTypeAndCreatedAtBetween(id,HistoryType.EXPENSE,startDate,endDate);
        BigDecimal totalOutcome = incomeHistories.stream()
                .map(History::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::subtract);
        return totalOutcome;
    }
}
