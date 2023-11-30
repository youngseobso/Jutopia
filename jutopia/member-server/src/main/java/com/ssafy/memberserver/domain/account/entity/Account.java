package com.ssafy.memberserver.domain.account.entity;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.students.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    public static Account from(Student student, String accountNumber){
        return Account.builder()
                .accountName("싸피은행")
                .accountStatus(AccountStatus.ACTIVE)
                .accountBalance(BigDecimal.ZERO)
                .id(UUID.randomUUID())
                .accountNumber(accountNumber)
                .student(student)
                .build();
    }
    public void deposit(BigDecimal amount){
        this.accountBalance = this.accountBalance.add(amount);
    }
    public void withdraw(BigDecimal amount){
        if(this.accountBalance.compareTo(amount) < 0){
            throw new IllegalArgumentException("불가능한 금액입니다.");
        }
        this.accountBalance = this.accountBalance.subtract(amount);
    }
    public void helpMoneyUpdate(BigDecimal number){
        this.accountBalance = this.accountBalance.add(number);
    }
    public void delete(AccountDeleteRequest accountDeleteRequest){
        if(accountDeleteRequest.getAccountStatus() == AccountStatus.ACTIVE){
            this.accountStatus = AccountStatus.INACTIVE;
        }
    }
    public void updateBalance(BigDecimal balance){
        this.accountBalance = balance;
    }
    public void updateBalance2(BigDecimal accountBalance){
        this.accountBalance = accountBalance;
    }
}
