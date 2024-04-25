import TabButton from "@/components/common/Button/TabButton.styled";
import { useState } from "react";
import { DateTabContainer, TabDiv, TabTitleDiv } from "./DateTab.styled";

function DateTab() {
  const dateTab = ["1주일", "1개월", "3개월", "6개월"];
  const tradeTypeTab = ["전체", "매수", "매도"];
  const assetTypeTab = ["전체", "가상화폐", "팔로잉", "팔로워"];
  const [dateActive, setDateActive] = useState<string>("1주일");
  const [tradeActive, setTradeActive] = useState<string>("전체");
  const [assetActive, setAssetActive] = useState<string>("전체");

  const dateClick = (tab: string) => {
    setDateActive(tab);
  };

  const tradeClick = (tab: string) => {
    setTradeActive(tab);
  };

  const assetClick = (tab: string) => {
    setAssetActive(tab);
  };

  return (
    <DateTabContainer>
      <TabDiv>
        <TabTitleDiv>
          기간 <span>2024년 01월 01일 ~ 2024년 03월 12일</span>
        </TabTitleDiv>
        {dateTab.map((tab) => {
          return (
            <TabButton
              key={tab}
              width="4rem"
              height="2.5rem"
              $active={tab === dateActive}
              onClick={() => dateClick(tab)}
              radius={tab === "1주일" ? "left" : tab === "6개월" ? "right" : ""}
            >
              {tab}
            </TabButton>
          );
        })}
      </TabDiv>
      <TabDiv>
        <TabTitleDiv>거래종류</TabTitleDiv>
        {tradeTypeTab.map((tab) => {
          return (
            <TabButton
              key={tab}
              width="4rem"
              height="2.5rem"
              $active={tab === tradeActive}
              onClick={() => tradeClick(tab)}
              radius={tab === "전체" ? "left" : tab === "매도" ? "right" : ""}
            >
              {tab}
            </TabButton>
          );
        })}
      </TabDiv>
      <TabDiv>
        <TabTitleDiv>자산종류</TabTitleDiv>
        {assetTypeTab.map((tab) => {
          return (
            <TabButton
              key={tab}
              width="4rem"
              height="2.5rem"
              $active={tab === assetActive}
              onClick={() => assetClick(tab)}
              radius={tab === "전체" ? "left" : tab === "팔로워" ? "right" : ""}
            >
              {tab}
            </TabButton>
          );
        })}
      </TabDiv>
    </DateTabContainer>
  );
}

export default DateTab;
