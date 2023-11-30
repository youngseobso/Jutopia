package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.jpa.entity.MemberSavingEntity;
import com.ssafy.classserver.jpa.entity.SavingProductsEntity;
import com.ssafy.classserver.vo.request.RequestMemberSaving;

import java.util.Optional;
import java.util.UUID;

public interface BankService {
    ProductDto createProduct(ProductDto product);

    Iterable<SavingProductsEntity> getAllProducts(UUID classroomId);

    Optional<SavingProductsEntity> getProduct(UUID productId);

    MemberSavingEntity createMemProduct(MemberSavingEntity request);

    Iterable<MemberSavingEntity> getMemSaving(UUID memberId);
}
