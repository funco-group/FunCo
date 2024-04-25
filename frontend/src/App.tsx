import "@/App.css";
import { useSetRecoilState } from "recoil";
import { useEffect } from "react";
import { AxiosResponse } from "axios";
import RoutesComponent from "@/routes/Routes";
import priceListState from "@/recoils/crypto/atoms";
import { getCoinList } from "@/apis/upbit";
import { PriceType, ResMarketCodeType } from "@/interfaces/PriceWindowType";
import "react-toastify/dist/ReactToastify.css";
import { StyledContainer } from "./styles/CommonStyled";
import useLoginAlertModalState from "./hooks/recoilHooks/useLoginAlertModalState";
import LoginAlertModal from "./components/common/Modal/LoginAlertModal";

const toPriceType = (coinData: ResMarketCodeType[]): PriceType[] => {
  return coinData
    .filter((coin) => coin.market.startsWith("KRW"))
    .map((coin: ResMarketCodeType) => ({
      code: coin.market,
      koreanName: coin.korean_name,
      tradePrice: 0,
      change: "EVEN",
      signedChangeRate: 0,
      signedChangePrice: 0,
      accTradeVolme24h: 0,
      accTradePrice24h: 0,
      highPrice: 0,
      lowPrice: 0,
      updated: false,
    }));
};

export default function App() {
  const setPriceList = useSetRecoilState(priceListState);
  const { loginAlertModal } = useLoginAlertModalState();

  useEffect(() => {
    getCoinList((response: AxiosResponse<ResMarketCodeType[]>) => {
      const { data } = response;
      setPriceList(toPriceType(data));
    });
  }, []);

  return (
    <>
      <StyledContainer
        position="top-right"
        closeOnClick
        closeButton={false}
        pauseOnHover={false}
        pauseOnFocusLoss={false}
        theme="light"
        draggable
        draggablePercent={80}
      />
      <RoutesComponent />
      {loginAlertModal && <LoginAlertModal />}
    </>
  );
}
