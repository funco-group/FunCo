package com.found_404.funco.asset.dto.request;

import com.found_404.funco.asset.domain.type.AssetTradeType;
import com.found_404.funco.asset.domain.type.AssetType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TotalAssetHistoryRequest {
    private Long memberId;
    private AssetType assetType;
    private AssetTradeType assetTradeType;
    private Double volume;
    private Double price;
    private Long commission;
    private Long settlement;
    private Long beginningCash;
    private Long endingCash;
    private Long orderCash;
    private String ticker;
    private String portfolioName;
    private Long investment;
    private String followName;
    private LocalDateTime followDate;
    private Double followReturnRate;
}
