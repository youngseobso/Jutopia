package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import lombok.Getter;

@Getter
public class AccountDeleteRequest{
    private AccountStatus accountStatus;
}
