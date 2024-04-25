import { RecentInvestmentContainer } from "./RecentInvestment.styled";
import { ComponentTitleH3 } from "./styled";
import { useEffect, useState } from "react";
import { getTickerPrice } from "@/apis/upbit";
import { AxiosResponse } from "axios";
import { ResTickerType } from "@/interfaces/tradeHistory/follow/ResTickerType";
import { ChartDiv } from "./AssetGraph.styled";
import MemberType from "@/interfaces/userPage/MemberType";
import MonochromePieChart from "@/components/common/Chart/MonochromePieChart";
import { NoDataDiv } from "./ReturnRateGraph.styled";

interface AssetGraphProps {
  member: MemberType;
}

function AssetGraph({ member }: AssetGraphProps) {
  const [investmentList, setInvestmentList] = useState<(string | number)[][]>(
    [],
  );

  const getCurPrice = () => {
    const curPrice = new Map<string, number>();
    getTickerPrice(
      member.memberAssetInfo.coins.map((coin) => coin.ticker).join(","),
      (response: AxiosResponse<ResTickerType[]>) => {
        const { data } = response;
        data.map((coin) => {
          curPrice.set(coin.market, coin.trade_price);
        });
        setInvestmentListFunc(curPrice);
      },
    );
  };

  const setInvestmentListFunc = (curPrice: Map<string, number>) => {
    setInvestmentList([
      ["현금", member.memberAssetInfo.cash],
      ["팔로우", member.followingCash],
      [
        "가상화폐",
        member.memberAssetInfo.coins.reduce((acc, coin) => {
          return acc + Math.floor(coin.volume * curPrice.get(coin.ticker)!);
        }, 0),
      ],
    ]);
  };

  useEffect(() => {
    getCurPrice();
  }, []);

  return (
    <RecentInvestmentContainer>
      <ComponentTitleH3>자산 요약</ComponentTitleH3>
      <ChartDiv $flex={investmentList.length === 0}>
        {investmentList.length !== 0 ? (
          <MonochromePieChart
            key={investmentList.length}
            investmentList={investmentList}
            isLegend={true}
          />
        ) : (
          <NoDataDiv>자산 내역이 없습니다.</NoDataDiv>
        )}
      </ChartDiv>
    </RecentInvestmentContainer>
  );
}

export default AssetGraph;
