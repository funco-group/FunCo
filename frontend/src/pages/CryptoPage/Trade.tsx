import React, { useEffect, useState } from "react";
import { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
import Tab from "@/components/crypto/Tab";
import TradeContainer from "./Trade.styled";
import NormalTrade from "./NormalTrade";
import ShortTrade from "./ShortTrade";
import TradeList from "./TradeList";
import { getTickerPrice } from "@/apis/upbit";
import { ResTickerType } from "@/interfaces/tradeHistory/follow/ResTickerType";
import { useRecoilValue } from "recoil";
import userState from "@/recoils/user";
import useLoginAlertModalState from "@/hooks/recoilHooks/useLoginAlertModalState";

const Trade = React.memo(function Trade() {
  const tabs = ["일반거래", "간편거래", "거래내역"];
  const [activeTab, setActiveTab] = useState<string>("일반거래");
  const user = useRecoilValue(userState);
  const { onLoginAlertModal } = useLoginAlertModalState();

  const changeTab = (tab: string) => {
    if (!user.user && tab === "일반거래") {
      onLoginAlertModal();
    }
    setActiveTab(tab);
  };
  const { coinCode } = useParams();
  const [curPrice, setCurPrice] = useState<number>(0);

  // 현재가 가져오기
  const getCurPrice = () => {
    getTickerPrice(coinCode!, (response: AxiosResponse<ResTickerType[]>) => {
      const { data } = response;
      setCurPrice(Math.round(data[0].trade_price));
    });
  };

  useEffect(() => {
    getCurPrice();
  }, [coinCode, activeTab]);

  useEffect(() => {
    setActiveTab("일반거래");
  }, [coinCode]);

  return (
    <TradeContainer>
      <Tab
        columns={3}
        tabs={tabs}
        activeTab={activeTab}
        changeTab={changeTab}
      />
      {activeTab === "일반거래" && (
        <NormalTrade curPrice={curPrice} getCurPrice={getCurPrice} />
      )}
      {activeTab === "간편거래" && (
        <ShortTrade curPrice={curPrice} getCurPrice={getCurPrice} />
      )}
      {activeTab === "거래내역" && <TradeList />}
    </TradeContainer>
  );
});

export default Trade;
