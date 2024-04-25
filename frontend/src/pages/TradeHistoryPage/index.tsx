import { Outlet } from "react-router-dom";
import { useState } from "react";
import { useRecoilValue } from "recoil";
import PriceWindow from "@/components/common/PriceWindow/PriceWindow";
import { CryptoPageContainer } from "../CryptoPage/styled";
import HistoryTab from "./HistoryTab";
import { TradeHistoryPageContainer } from "./styled";
import { PriceType } from "@/interfaces/PriceWindowType";
import { priceListState } from "@/recoils/crypto";

function Index() {
  const [priceList, setPriceList] = useState<PriceType[]>(
    useRecoilValue(priceListState),
  );
  return (
    <CryptoPageContainer>
      <TradeHistoryPageContainer>
        <HistoryTab />
        <Outlet />
      </TradeHistoryPageContainer>
      <PriceWindow priceList={priceList} setPriceList={setPriceList} />
    </CryptoPageContainer>
  );
}

export default Index;
