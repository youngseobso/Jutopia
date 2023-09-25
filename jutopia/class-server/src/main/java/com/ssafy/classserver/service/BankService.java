package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.jpa.entity.SavingProductsEntity;

import java.util.UUID;

public interface BankService {
    ProductDto createProduct(ProductDto product);

    Iterable<SavingProductsEntity> getAllProducts(UUID classroomId);
}
