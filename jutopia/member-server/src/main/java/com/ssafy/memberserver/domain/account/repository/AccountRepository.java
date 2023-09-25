package com.ssafy.memberserver.domain.account.repository;

import com.ssafy.memberserver.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {


    Optional<Account> findByIdAndStudentId(UUID id, String studentId);
}
