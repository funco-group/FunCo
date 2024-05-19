
package com.found_404.funco.feignClient.service;

import com.found_404.funco.feignClient.client.AssetServiceClient;
import com.found_404.funco.feignClient.dto.AssetTradeType;
import com.found_404.funco.feignClient.dto.request.TotalAssetHistoryRequest;
import com.found_404.funco.follow.domain.Follow;
import com.found_404.funco.follow.exception.FollowException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.found_404.funco.follow.exception.FollowErrorCode.OTHER_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssetService {
    private final AssetServiceClient assetServiceClient;
    private final String SERVER_NAME = "[asset-service]";

    @Async
    public void createAssetHistory(Follow follow, AssetTradeType assetTradeType, Long cash) {
        try {
            this.assetServiceClient.createCoinHistory(TotalAssetHistoryRequest.builder()
                    .assetTradeType(assetTradeType)
                    .memberId(assetTradeType.equals(AssetTradeType.FOLLOWING) ? follow.getFollowerMemberId() : follow.getFollowingMemberId())
                    .investment(follow.getInvestment())
                    .settlement(follow.getSettlement())
                    .followReturnRate(follow.getReturnRate())
                    .commission(follow.getCommission())
                    .endingCash(cash)
                    .followDate(follow.getCreatedAt())
                    .build());
        } catch (FeignException e) {
            log.error("{} createAssetHistory error : {}", SERVER_NAME, e.getMessage());
            throw new FollowException(OTHER_SERVER_ERROR);
        }
    }

}
