import { useParams } from "react-router-dom";
import { useState } from "react";
import ChartGraph from "./ChartGraph";
import {
  ChartContainer,
  ChartInfoContainer,
  CoinNameDiv,
  PriceContainer,
  CurPriceDiv,
  PriceDiv,
  ChangeDiv,
  ChangeTextDiv,
  ChangeInfoDiv,
  ButtonsContainer,
  TypeButton,
  TradePriceContainer,
  TradePriceItemDiv,
  TradeTitleDiv,
  TradePriceDiv,
} from "./Chart.styled";
import { PriceType } from "@/interfaces/PriceWindowType";

interface ChartProps {
  priceList: PriceType[];
}

function Chart({ priceList }: ChartProps) {
  const { coinCode } = useParams();
  const coin = priceList.find((price) => price.code === coinCode);
  const buttons = ["일봉", "주봉", "월봉", "1분봉", "5분봉", "10분봉"];
  const [button, setButton] = useState<string>("일봉");

  const formatNumber = (number: number) => {
    return new Intl.NumberFormat("ko-KR", {
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(number);
  };

  const clickButton = (name: string) => {
    setButton(name);
  };

  if (!coin) return null;

  return (
    <ChartContainer>
      <ChartInfoContainer>
        <CoinNameDiv>
          <img
            src={`https://static.upbit.com/logos/${coin.code.split("-")[1]}.png`}
            alt=""
            width={25}
          />
          <span>{coin.koreanName}</span>
          {coin.code}
        </CoinNameDiv>
        <PriceContainer>
          <CurPriceDiv change={coin.change}>
            <PriceDiv>
              {coin.tradePrice.toLocaleString("ko-KR")}
              <span>{coin.code.split("-")[0]}</span>
            </PriceDiv>
            <ChangeDiv>
              <ChangeTextDiv>전일대비</ChangeTextDiv>
              <ChangeInfoDiv>
                {coin.change === "RISE" && "+"}
                {(coin.signedChangeRate * 100).toFixed(2)}%
              </ChangeInfoDiv>
              <ChangeInfoDiv>
                {coin.signedChangePrice.toLocaleString("ko-KR")}
                {coin.change === "RISE" && " ▲"}
                {coin.change === "FALL" && " ▼"}
              </ChangeInfoDiv>
            </ChangeDiv>
          </CurPriceDiv>
          <TradePriceContainer>
            <TradePriceItemDiv>
              <TradeTitleDiv $top>
                고가
                <TradePriceDiv color="red">
                  {coin.highPrice.toLocaleString("ko-KR")}
                </TradePriceDiv>
              </TradeTitleDiv>
              <TradeTitleDiv $top={false}>
                저가
                <TradePriceDiv color="blue">
                  {coin.lowPrice.toLocaleString("ko-KR")}
                </TradePriceDiv>
              </TradeTitleDiv>
            </TradePriceItemDiv>
            <TradePriceItemDiv>
              <TradeTitleDiv $top>
                거래량(24H)
                <TradePriceDiv color="black">
                  {coin.accTradeVolme24h.toLocaleString("ko-KR")}
                  <span> {coin.code.split("-")[1]}</span>
                </TradePriceDiv>
              </TradeTitleDiv>
              <TradeTitleDiv $top={false}>
                거래대금(24H)
                <TradePriceDiv color="black">
                  {formatNumber(coin.accTradePrice24h)}
                  <span> {coin.code.split("-")[0]}</span>
                </TradePriceDiv>
              </TradeTitleDiv>
            </TradePriceItemDiv>
          </TradePriceContainer>
        </PriceContainer>
      </ChartInfoContainer>
      <ButtonsContainer>
        {buttons.map((name) => (
          <TypeButton
            $selected={name === button}
            key={name}
            onClick={() => clickButton(name)}
          >
            {name}
          </TypeButton>
        ))}
      </ButtonsContainer>
      <ChartGraph button={button} />
    </ChartContainer>
  );
}

export default Chart;
