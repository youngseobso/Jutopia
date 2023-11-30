package com.ssafy.memberserver.domain.sign.controller;

import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.domain.sign.dto.SignInRequest;
import com.ssafy.memberserver.domain.sign.dto.SignInResponse;
import com.ssafy.memberserver.domain.sign.service.SignInService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member-server/api")
public class signInController {
    private final SignInService signInService;

    @Operation(summary = "통합 로그인")
    @PostMapping("/sign-in")
    public Api<SignInResponse> StudentSignIn(@RequestBody SignInRequest signInRequest){
        return Api.OK(signInService.signIn(signInRequest));
    }
}
