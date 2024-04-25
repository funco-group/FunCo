import { useRecoilValue } from "recoil";
import { useState } from "react";
import PriceWindow from "@/components/common/PriceWindow/PriceWindow";
import Chart from "./Chart.tsx";
import OrderBook from "./OrderBook";
import Trade from "./Trade";
import { CryptoPageContainer, BottomContainer } from "./styled";
import priceListState from "@/recoils/crypto/atoms.ts";
import { PriceType } from "@/interfaces/PriceWindowType.ts";

function Index() {
  const [priceList, setPriceList] = useState<PriceType[]>(
    useRecoilValue(priceListState),
  );

  return (
    <CryptoPageContainer>
      <div>
        <Chart priceList={priceList} />
        <BottomContainer>
          <OrderBook />
          <Trade />
        </BottomContainer>
      </div>
      <PriceWindow priceList={priceList} setPriceList={setPriceList} />
    </CryptoPageContainer>
  );
}

export default Index;
