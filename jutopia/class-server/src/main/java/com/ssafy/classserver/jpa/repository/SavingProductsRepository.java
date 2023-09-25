package com.ssafy.classserver.jpa.repository;

import com.ssafy.classserver.jpa.entity.SavingProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SavingProductsRepository extends JpaRepository<SavingProductsEntity, UUID> {

}
