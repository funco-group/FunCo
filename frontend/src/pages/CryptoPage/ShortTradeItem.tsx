import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { useParams } from "react-router-dom";
import { AxiosResponse } from "axios";
import TradeButton from "@/components/crypto/TradeButton";
import {
  TradeContainer,
  TradeItem,
  TitleDiv,
  ContentDiv,
  PriceInput,
  PriceButtons,
  PriceButton,
  AmountDiv,
  GreenDiv,
  TradeInfo,
} from "@/styles/Crypto.styled";
import userState from "@/recoils/user";
import { getCash, getCoinVolume } from "@/apis/asset";
import { CashType } from "@/interfaces/common/AssetType";
import { buyMarket, sellMarket } from "@/apis/trade";
import TradeConcludedModal from "./TradeConcludedModal";
import { TradeResultType } from "@/interfaces/TradeType";
import { CoinVolumeType } from "@/interfaces/AssetType";
import AlertModal from "@/components/common/Modal/AlertModal";
import inputDecimalFormat from "@/utils/inputDecimalFormat";
import useLoginAlertModalState from "@/hooks/recoilHooks/useLoginAlertModalState";

interface ShortTradeItemProps {
  name: string;
  curPrice: number;
}

function ShortTradeItem({ name, curPrice }: ShortTradeItemProps) {
  const user = useRecoilValue(userState);
  const { onLoginAlertModal } = useLoginAlertModalState();
  const { coinCode } = useParams();
  const volumeButtons = [10, 20, 25, 30, 40, 50, 75, 100];
  const [concludedModal, setConcludedModal] = useState<boolean>(false);
  const [result, setResult] = useState<TradeResultType>({
    ticker: "",
    volume: 0,
    price: 0,
  });
  const [alert, setAlert] = useState<boolean>(false);
  const [alertContent, setAlertContent] = useState<string>("");

  const [cash, setCash] = useState<number>(0); // 주문 가능
  const [volume, setVolume] = useState<number>(0); // 코인 보유 개수
  const [order, setOrder] = useState<number>(0); // 주문 금액, 수량
  const [formatted, setFormatted] = useState<string>("");
  const [expectAmount, setExpectAmount] = useState<number>(0); // 예상 획득량

  const getCashFunc = () => {
    getCash((response: AxiosResponse<CashType>) => {
      const { data } = response;
      setCash(data.cash);
    });
  };

  const getCoinVolumeFunc = () => {
    getCoinVolume(coinCode!, (response: AxiosResponse<CoinVolumeType>) => {
      const { data } = response;
      setVolume(data.volume);
    });
  };

  useEffect(() => {
    setOrder(0);
    setFormatted("");
    if (user.user) {
      if (name === "매수") {
        getCashFunc();
      } else {
        getCoinVolumeFunc();
      }
    } else {
      onLoginAlertModal();
    }
  }, [name, coinCode]);

  useEffect(() => {
    if (name === "매수") {
      setExpectAmount(order / curPrice);
    } else {
      setExpectAmount(order * curPrice);
    }
  }, [order, curPrice]);

  const clickVolumeButton = (rate: number) => {
    if (name === "매수") {
      setOrder(Math.round(cash * rate * 0.01));
      setFormatted(Math.round(cash * rate * 0.01).toLocaleString("ko-KR"));
    } else {
      const price = parseFloat((volume * rate * 0.01).toFixed(10));
      setOrder(price);
      setFormatted(price.toString());
    }
  };

  const clickBuy = () => {
    if (user.user) {
      if (cash < order) {
        setAlertContent("자산이 충분하지 않습니다.");
        setAlert(true);
        return;
      }
      if (order === 0) {
        setAlertContent(`주문 금액을 입력해주세요.`);
        setAlert(true);
        return;
      }
      buyMarket(
        coinCode!,
        order,
        (response: AxiosResponse<TradeResultType>) => {
          const { data } = response;
          setResult(data);
          setConcludedModal(true);
          getCashFunc();
          setOrder(0);
          setFormatted("");
        },
        ({ response }) => {
          if (response.data.errorCode === "INSUFFICIENT_COINS") {
            setAlertContent("자산이 충분하지 않습니다.");
          } else {
            setAlertContent("서버에 오류가 발생했습니다.");
          }
          setAlert(true);
        },
      );
    } else {
      onLoginAlertModal();
    }
  };

  const clickSell = () => {
    if (user.user) {
      if (volume < order) {
        setAlertContent("자산이 충분하지 않습니다.");
        setAlert(true);
        return;
      }
      if (order === 0) {
        setAlertContent(`주문 수량을 입력해주세요.`);
        setAlert(true);
        return;
      }
      sellMarket(
        coinCode!,
        order,
        (response: AxiosResponse<TradeResultType>) => {
          const { data } = response;
          setResult(data);
          setConcludedModal(true);
          getCoinVolumeFunc();
          setOrder(0);
          setFormatted("");
        },
        ({ response }) => {
          if (response.data.errorCode === "INSUFFICIENT_COINS") {
            setAlertContent("자산이 충분하지 않습니다.");
          } else {
            setAlertContent("서버에 오류가 발생했습니다.");
          }
          setAlert(true);
        },
      );
    } else {
      onLoginAlertModal();
    }
  };

  const closeConcludedModal = () => {
    setConcludedModal(false);
  };

  const closeAlert = () => {
    setAlert(false);
  };

  return (
    <TradeContainer>
      {concludedModal && (
        <TradeConcludedModal
          result={result}
          name={name}
          clickClose={closeConcludedModal}
        />
      )}
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <div>
        <TradeItem>
          <TitleDiv>주문 가능</TitleDiv>
          <ContentDiv>
            {name === "매수" ? cash.toLocaleString("ko-KR") : volume}
            <div>{name === "매수" ? "WON" : coinCode!.split("-")[1]}</div>
          </ContentDiv>
        </TradeItem>
        <TradeItem>
          <TitleDiv>주문 {name === "매수" ? "금액" : "수량"}</TitleDiv>
          <AmountDiv>
            <PriceInput
              value={formatted}
              placeholder="0"
              onChange={(e) =>
                inputDecimalFormat(e, setFormatted, setOrder, name)
              }
            />
          </AmountDiv>
        </TradeItem>
        <TradeItem>
          <div />
          <PriceButtons>
            {volumeButtons.map((rate) => {
              return (
                <PriceButton onClick={() => clickVolumeButton(rate)} key={rate}>
                  {rate}%
                </PriceButton>
              );
            })}
          </PriceButtons>
        </TradeItem>
      </div>
      <div>
        <TradeInfo>
          ·수수료(부가세 포함): 0.05%ㅤ·최소주문금액: 1,000 WON
        </TradeInfo>
        <GreenDiv $last>
          <TradeItem>
            <TitleDiv>예상 획득량</TitleDiv>
            <ContentDiv>
              {name === "매도"
                ? Math.round(expectAmount).toLocaleString("ko-KR")
                : parseFloat(expectAmount.toFixed(10))}
              <div>{name === "매도" ? "WON" : coinCode!.split("-")[1]}</div>
            </ContentDiv>
          </TradeItem>
        </GreenDiv>
        <TradeButton
          name={name}
          onclick={name === "매수" ? clickBuy : clickSell}
        />
      </div>
    </TradeContainer>
  );
}

export default ShortTradeItem;
