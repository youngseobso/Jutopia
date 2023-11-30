package com.ssafy.classserver.controller.bank;

import com.ssafy.classserver.client.MemberMoneyUpdateRequest;
import com.ssafy.classserver.client.MemberServerClient;
import com.ssafy.classserver.client.ResponseMember;
import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.jpa.entity.MemberSavingEntity;
import com.ssafy.classserver.jpa.entity.SavingProductsEntity;
import com.ssafy.classserver.service.BankService;
import com.ssafy.classserver.service.SchoolService;
import com.ssafy.classserver.vo.request.RequestMemberSaving;
import com.ssafy.classserver.vo.request.RequestProduct;
import com.ssafy.classserver.vo.response.ResponseMemberSaving;
import com.ssafy.classserver.vo.response.ResponseProduct;
import com.ssafy.common.api.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/class-server/api/bank")
@Tag(name = "products", description = "적금 상품 관리 api 입니다.")
public class ProductsController {

    Environment env;
    BankService bankService;
    SchoolService schoolService;
    MemberServerClient memberServerClient;
    ModelMapper mapper;

    @Autowired
    public ProductsController(Environment env, SchoolService schoolService, BankService bankService
            ,MemberServerClient memberServerClient) {
        this.env = env;
        this.bankService = bankService;
        this.schoolService = schoolService;
        this.memberServerClient = memberServerClient;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // 반 적금 상품 생성
    @PostMapping("/{classroomId}/product")
    public Api<ResponseProduct> createProduct(@RequestBody RequestProduct product
                                                        , @PathVariable UUID classroomId) {

        ProductDto productDto = mapper.map(product, ProductDto.class);
        productDto.setClassroomId(classroomId);
        bankService.createProduct(productDto);
        ResponseProduct responseProduct = mapper.map(productDto, ResponseProduct.class);

        return Api.CREATED(responseProduct);
    }

    // 적금 상품 가져오기
    @GetMapping("/{classroomId}/product")
    public Api<List<ResponseProduct>> getAllProducts(@PathVariable UUID classroomId) {
        Iterable<SavingProductsEntity> plist = bankService.getAllProducts(classroomId);
        List<ResponseProduct> resultList = new ArrayList<>();

        plist.forEach(p -> resultList.add(new ModelMapper().map(p, ResponseProduct.class)));

        return Api.OK(resultList);
    }


    // 회원 적금 상품 가입
    @PostMapping("/product/{memberId}/{productId}")
    public Api<RequestMemberSaving> createMemberSaving(@RequestBody RequestMemberSaving request,
                                                      @PathVariable("memberId") UUID memberId,
                                                      @PathVariable("productId") UUID productId) {
        Optional<SavingProductsEntity> product = bankService.getProduct(productId);

        if (!product.isPresent()) return Api.NOT_FOUND(null);

        MemberSavingEntity memRequest = mapper.map(request, MemberSavingEntity.class);

        // memberserver에 회원 보유 화폐 요청
        ResponseMember member = mapper.map(memberServerClient.getStudent(memberId).data(), ResponseMember.class);
        // 회원이 보유한 돈이 부족하면
        if (member.getMoney().compareTo(memRequest.getMoney()) < 0) return Api.BAD_REQUEST(null, "화폐가 부족합니다");

        memRequest.setSavingProduct(product.get());
        memRequest.setMemberId(memberId);

        // 회원 적금상품 저장
        bankService.createMemProduct(memRequest);
        // 회원 보유 화폐 차감 요청
        MemberMoneyUpdateRequest moneyUpdate = new MemberMoneyUpdateRequest(memberId, memRequest.getMoney());
        memberServerClient.memberMoneyUpdate(moneyUpdate);

        return Api.OK(request);
    }

    @GetMapping("/product/{memberId}")
    public Api<List<ResponseMemberSaving>> getMemberSaving(@PathVariable UUID memberId) {
        Iterable<MemberSavingEntity> memberSaving = bankService.getMemSaving(memberId);

        List<ResponseMemberSaving> result = new ArrayList<>();
        memberSaving.forEach(m -> {
            ResponseMemberSaving res = mapper.map(m, ResponseMemberSaving.class);

            res.setProductId(m.getSavingProduct().getId());
            res.setProductName(m.getSavingProduct().getProductName());
            res.setProductDetail(m.getSavingProduct().getProductDetail());
            res.setTerm(m.getSavingProduct().getTerm());
            res.setInterestRate(m.getSavingProduct().getInterestRate());

            result.add(res);
        });

        return Api.OK(result);
    }
}
