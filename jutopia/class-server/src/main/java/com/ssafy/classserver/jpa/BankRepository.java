package com.ssafy.classserver.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BankRepository extends CrudRepository<BankEntity, UUID> {
    Iterable<BankEntity> findAll();
    BankEntity findByClassId(UUID classId);


}
