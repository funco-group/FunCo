import { useEffect, useState } from "react";
import AssetList from "./AssetList";
import TotalAsset from "./TotalAsset";
import { getAsset } from "@/apis/asset";
import {
  AssetResponseType,
  AssetType,
  TotalAssetType,
} from "@/interfaces/AssetType";
import { AxiosResponse } from "axios";
import { getTickerPrice } from "@/apis/upbit";
import { ResTickerType } from "@/interfaces/tradeHistory/follow/ResTickerType";
import cashIcon from "@/assets/icon/cash-icon.png";
import followIcon from "@/assets/icon/follow-icon.png";
import { ChartContainer, TotalAssetInfoContainer } from "./styled";
import MonochromePieChart from "@/components/common/Chart/MonochromePieChart";
import { TitleDiv } from "@/styles/TradeHistoryStyled";

function index() {
  const [assets, setAssets] = useState<AssetType[]>([]);
  const [totalAsset, setTotalAsset] = useState<TotalAssetType>();

  const [investmentList, setInvestmentList] = useState<(string | number)[][]>();

  const getCurPrice = async (assets: AssetResponseType) => {
    const curPrice = new Map<string, number>();
    if (assets.holdingCoinInfos.length !== 0) {
      const codes = assets.holdingCoinInfos
        .map((coin) => coin.ticker)
        .join(",");
      await getTickerPrice(
        codes,
        (response: AxiosResponse<ResTickerType[]>) => {
          const { data } = response;
          data.map((coin) => {
            curPrice.set(coin.market, coin.trade_price);
          });
        },
      );
    }
    return curPrice;
  };

  const setAssetsInfo = (
    assetsRes: AssetResponseType,
    curPrice: Map<string, number>,
  ) => {
    setAssets([
      {
        imgSrc: cashIcon,
        name: "현금",
        volume: null,
        averagePrice: null,
        price: null,
        evaluationAmount: assetsRes.cash,
        evaluationProfit: null,
      },
      {
        imgSrc: followIcon,
        name: "팔로우",
        volume: null,
        averagePrice: null,
        price: assetsRes.followingInvestment,
        evaluationAmount: assetsRes.followingInvestment,
        evaluationProfit: null,
      },
    ]);
    assetsRes.holdingCoinInfos.map((coin) => {
      const price = Math.floor(coin.volume * coin.averagePrice);
      const evaluationAmount = Math.floor(
        coin.volume * curPrice.get(coin.ticker)!,
      );
      setAssets((asset) => [
        ...asset,
        {
          imgSrc: `https://static.upbit.com/logos/${coin.ticker.split("-")[1]}.png`,
          name: coin.ticker,
          volume: coin.volume,
          averagePrice: coin.averagePrice,
          price: price,
          evaluationAmount: evaluationAmount,
          evaluationProfit:
            Math.floor(((evaluationAmount - price) / price) * 100 * 100) / 100,
        },
      ]);
    });
    setInvestmentList([
      ["현금", assetsRes.cash],
      ["팔로우", assetsRes.followingInvestment],
      [
        "가상화폐",
        assetsRes.holdingCoinInfos.reduce((acc, coin) => {
          return acc + Math.floor(coin.volume * curPrice.get(coin.ticker)!);
        }, 0),
      ],
    ]);
  };

  useEffect(() => {
    getAsset((response: AxiosResponse<AssetResponseType>) => {
      const { data } = response;
      const curPrice = getCurPrice(data);
      curPrice.then((res) => {
        setAssetsInfo(data, res);
      });
    });
  }, []);

  useEffect(() => {
    if (assets.length !== 0) {
      // 보유
      const cash = assets.filter((asset) => asset.name === "현금")[0]
        .evaluationAmount;
      // 총 매수금액
      const price = assets
        .filter((asset) => asset.name !== "현금")
        .reduce((acc, item) => {
          return acc + item.price!;
        }, 0);
      // 총 평가금액
      const evaluationAmount = assets
        .filter((asset) => asset.name !== "현금")
        .reduce((acc, item) => {
          return acc + item.evaluationAmount;
        }, 0);
      // 총 보유자산
      const asset = cash + price;
      // 총 평가손익
      const returnResult = evaluationAmount - price;
      // 총 평가수익률
      const evaluationProfit =
        Math.floor(((evaluationAmount - price) / price) * 100 * 100) / 100;

      setTotalAsset({
        cash,
        price,
        evaluationAmount,
        asset,
        returnResult,
        evaluationProfit,
      });
    }
  }, [assets]);

  if (!investmentList) return null;

  return (
    <div>
      <TotalAssetInfoContainer>
        <TotalAsset totalAsset={totalAsset} />
        <ChartContainer>
          <MonochromePieChart investmentList={investmentList} isLegend={true} />
        </ChartContainer>
      </TotalAssetInfoContainer>
      <TitleDiv>보유자산 목록</TitleDiv>
      <AssetList assets={assets} />
    </div>
  );
}

export default index;
