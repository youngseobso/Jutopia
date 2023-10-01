package com.ssafy.stockserver.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient(name="news-server")
@FeignClient(name = "news-server", url = "http://j9c108.p.ssafy.io:8000/news-server")
public interface NewsServerClient {
    @GetMapping("/stocks")
    String getStocks();
}
