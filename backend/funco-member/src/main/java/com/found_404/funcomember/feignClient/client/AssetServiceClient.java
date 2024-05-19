package com.found_404.funcomember.feignClient.client;

import com.found_404.funcomember.feignClient.dto.request.TotalAssetHistoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "asset-service")
public interface AssetServiceClient {
    @PostMapping({"/histories/follow"})
    void createCoinHistory(@RequestBody TotalAssetHistoryRequest totalAssetHistoryRequest);

}
