package com.ssafy.classserver.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<SavingProductsEntity, UUID> {

}
