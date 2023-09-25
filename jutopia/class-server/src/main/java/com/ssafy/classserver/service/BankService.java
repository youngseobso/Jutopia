package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.jpa.BankEntity;

import java.util.UUID;

public interface BankService {
    Iterable<BankEntity> getAllBanks();
    BankEntity getClassBanks(UUID classId);
    ProductDto createProduct(ProductDto product);
}
