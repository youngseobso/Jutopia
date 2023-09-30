package com.ssafy.stockserver.domain.memberStock.service;


import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;

import java.util.UUID;

public interface MemberStockService {

    Iterable<MemberStock> getMemberStock(UUID memberId);
}
