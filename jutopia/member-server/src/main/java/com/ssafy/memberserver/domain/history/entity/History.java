package com.ssafy.memberserver.domain.history.entity;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private HistoryType historyType;
    private LocalDateTime createdAt;
    @ManyToOne
    private Account account;

    public void senderHistory(Account account,HistoryType type, String sender, String receiver, BigDecimal amount, BigDecimal balance){
        this.account = account;
        this.historyType = type;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }
    public void receiverHistory(Account account,HistoryType type, String receiver, String sender,BigDecimal amount, BigDecimal balance){
        this.account = account;
        this.historyType = type;
        this.receiver = sender;
        this.sender = receiver;
        this.amount = amount;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }
}
