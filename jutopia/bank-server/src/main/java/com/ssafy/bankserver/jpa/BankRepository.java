package com.ssafy.bankserver.jpa;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BankRepository extends CrudRepository<BankEntity, Long> {
    Iterable<BankEntity> findAll();
    BankEntity findByClassId(UUID classId);
}
