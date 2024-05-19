package com.found_404.funco.feignClient.client;

import com.found_404.funco.feignClient.dto.request.TotalAssetHistoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "asset-service")
public interface AssetServiceClient {
    @PostMapping({"/api/v1/asset/histories/coin"})
    void createCoinHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest);

    @PostMapping({"/api/v1/asset/histories/futures"})
    void createFuturesHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest);
}
