package com.ssafy.bankserver.service;

import com.ssafy.bankserver.jpa.BankEntity;

import java.util.UUID;

public interface BankService {
    Iterable<BankEntity> getAllBanks();
    BankEntity getClassBanks(UUID classId);
}
