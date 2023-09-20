package com.ssafy.bankserver.domain.account.jpa;

import com.ssafy.bankserver.domain.account.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}
