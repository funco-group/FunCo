import {
  GreenContainer,
  GreenDataDiv,
  GreenTitleDiv,
} from "@/styles/TradeHistoryStyled";
import {
  AssetItemContainer,
  AssetItemDiv,
  TotalAssetContainer,
} from "./TotalAsset.styled";
import { TotalAssetType } from "@/interfaces/AssetType";

interface TotalAssetProps {
  totalAsset: TotalAssetType | undefined;
}

function TotalAsset({ totalAsset }: TotalAssetProps) {
  return (
    <TotalAssetContainer>
      <GreenContainer>
        <AssetItemContainer $top>
          <AssetItemDiv>
            <GreenTitleDiv>보유</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.cash.toLocaleString("ko-KR")} <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
          <AssetItemDiv>
            <GreenTitleDiv>총 보유자산</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.asset.toLocaleString("ko-KR")} <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
        </AssetItemContainer>
        <AssetItemContainer $top={false}>
          <AssetItemDiv>
            <GreenTitleDiv>총 매수금액</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.price.toLocaleString("ko-KR")} <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
          <AssetItemDiv>
            <GreenTitleDiv>총 평가손익</GreenTitleDiv>
            <GreenDataDiv
              color={
                totalAsset?.returnResult.toString().startsWith("-")
                  ? "blue"
                  : "red"
              }
            >
              {totalAsset?.returnResult.toLocaleString("ko-KR")}{" "}
              <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
        </AssetItemContainer>
        <AssetItemContainer $top={false}>
          <AssetItemDiv>
            <GreenTitleDiv>총 평가금액</GreenTitleDiv>
            <GreenDataDiv color="black">
              {totalAsset?.evaluationAmount.toLocaleString("ko-KR")}{" "}
              <span>WON</span>
            </GreenDataDiv>
          </AssetItemDiv>
          <AssetItemDiv>
            <GreenTitleDiv>총 평가수익률</GreenTitleDiv>
            <GreenDataDiv
              color={
                totalAsset?.evaluationProfit.toString().startsWith("-")
                  ? "blue"
                  : "red"
              }
            >
              {totalAsset?.evaluationProfit} <span>%</span>
            </GreenDataDiv>
          </AssetItemDiv>
        </AssetItemContainer>
      </GreenContainer>
    </TotalAssetContainer>
  );
}

export default TotalAsset;
