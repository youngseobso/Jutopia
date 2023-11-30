package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.jpa.entity.MemberSavingEntity;
import com.ssafy.classserver.jpa.entity.SavingProductsEntity;
import com.ssafy.classserver.jpa.repository.ClassRoomRepository;
import com.ssafy.classserver.jpa.repository.MemberSavingRepository;
import com.ssafy.classserver.jpa.repository.SavingProductsRepository;
import com.ssafy.classserver.vo.request.RequestMemberSaving;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Data
@Slf4j
public class BankServiceImpl implements BankService{

    ClassRoomRepository classRoomRepository;
    SavingProductsRepository savingProductsRepository;
    MemberSavingRepository memberSavingRepository;
    ModelMapper mapper;

    @Autowired
    public BankServiceImpl(ClassRoomRepository classRoomRepository, SavingProductsRepository savingProductsRepository,
                           MemberSavingRepository memberSavingRepository) {
        this.classRoomRepository = classRoomRepository;
        this.savingProductsRepository = savingProductsRepository;
        this.memberSavingRepository = memberSavingRepository;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public ProductDto createProduct(ProductDto product) {

        SavingProductsEntity productEntity = mapper.map(product, SavingProductsEntity.class);
        productEntity.setClassroom(classRoomRepository.findById(product.getClassroomId())
                .orElseThrow(() -> new EntityNotFoundException("GradeEntity not found with id: " + product.getClassroomId()))
        );

        productEntity = savingProductsRepository.save(productEntity);

        ProductDto resultDto = mapper.map(productEntity, ProductDto.class);
        resultDto.setBankName(classRoomRepository.findById(product.getClassroomId())
                .orElseThrow(() -> new EntityNotFoundException("GradeEntity not found with id: " + product.getClassroomId()))
                .getBankName());

        return new ModelMapper().map(productEntity, ProductDto.class);
    }

    @Override
    public Iterable<SavingProductsEntity> getAllProducts(UUID classroomId) {
        System.out.println("service" + savingProductsRepository.findAllByClassroomId(classroomId));
        return savingProductsRepository.findAllByClassroomId(classroomId);
    }

    @Override
    public Optional<SavingProductsEntity> getProduct(UUID productId) {
        return savingProductsRepository.findById(productId);
    }

    @Override
    public MemberSavingEntity createMemProduct(MemberSavingEntity request) {
        return memberSavingRepository.save(request);
    }

    @Override
    public Iterable<MemberSavingEntity> getMemSaving(UUID memberId) {
        return memberSavingRepository.findAllByMemberId(memberId);
    }

}
