package com.ssafy.memberserver.domain.account.repository;

import com.ssafy.memberserver.domain.account.entity.Account;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountName(String accountName);
    @Query("SELECT a FROM Account a WHERE a.student.studentId = :studentId")
    Optional<Account> findAccountByStudentId(@Param("studentId") String studentId);
}
