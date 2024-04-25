import { useRecoilValue } from "recoil";
import { useEffect, useState } from "react";
import { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
import TradeButton from "@/components/crypto/TradeButton";
import userState from "@/recoils/user";
import {
  TradeContainer,
  TradeItem,
  TitleDiv,
  ContentDiv,
  PriceInput,
  UpdownButton,
  PriceButtons,
  PriceButton,
  AmountDiv,
  GreenDiv,
  TradeInfo,
} from "@/styles/Crypto.styled";
import { getCash, getCoinVolume } from "@/apis/asset";
import { CashType } from "@/interfaces/common/AssetType";
import inputDecimalFormat from "@/utils/inputDecimalFormat";
import { buyLimit, sellLimit } from "@/apis/trade";
import AlertModal from "@/components/common/Modal/AlertModal";
import { CoinVolumeType } from "@/interfaces/AssetType";
import useLoginAlertModalState from "@/hooks/recoilHooks/useLoginAlertModalState";

interface NormalTradeItemProps {
  name: string;
  curPrice: number;
}

function NormalTradeItem({ name, curPrice }: NormalTradeItemProps) {
  const user = useRecoilValue(userState);
  const { onLoginAlertModal } = useLoginAlertModalState();
  const { coinCode } = useParams();
  const volumeButtons = [10, 20, 25, 30, 40, 50, 75, 100];

  const [cash, setCash] = useState<number>(0); // 주문 가능
  const [volume, setVolume] = useState<number>(0); // 코인 보유 개수
  const [orderPrice, setOrderPrice] = useState<number>(curPrice); // 주문 가격
  const [formattedOrderPrice, setFormattedOrderPrice] = useState<string>(""); // 주문 가격 포맷
  const [orderVolume, setOrderVolume] = useState<number>(0); // 주문 수량
  const [formattedVolume, setFormattedVolume] = useState<string>(""); // 주문 수량 포맷
  const [resPrice, setResPrice] = useState<number>(0); // 주문 금액
  const [commission, setCommission] = useState<number>(0); // 수수료
  const [totalAmount, setTotalAmount] = useState<number>(0); // 총 획득량

  const [alert, setAlert] = useState<boolean>(false);
  const [alertContent, setAlertContent] = useState<string>("");

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
    if (user.user) {
      getCashFunc();
    }
  }, []);

  const resetInput = () => {
    setOrderVolume(0);
    setFormattedVolume("");
    setOrderPrice(curPrice);
    setFormattedOrderPrice(curPrice.toLocaleString("ko-KR"));
    setCommission(0);
    setResPrice(0);
  };

  useEffect(() => {
    resetInput();
    if (name === "매수") {
      getCashFunc();
    } else if (user.user) {
      getCoinVolumeFunc();
    }
  }, [curPrice, coinCode, name, user.user]);

  const priceUp = () => {
    setOrderPrice(Math.round(orderPrice + curPrice * 0.01));
    setFormattedOrderPrice(
      Math.round(orderPrice + curPrice * 0.01).toLocaleString("ko-KR"),
    );
  };

  const priceDown = () => {
    const newPrice = orderPrice - curPrice * 0.01;
    if (newPrice > 0) {
      setOrderPrice(Math.round(newPrice));
      setFormattedOrderPrice(Math.round(newPrice).toLocaleString("ko-KR"));
    }
  };

  const clickVolumeButton = (rate: number) => {
    if (name === "매수") {
      setOrderVolume(
        Math.round(((cash * rate * 0.01) / orderPrice) * 1000) / 1000,
      );
      setFormattedVolume(
        (
          Math.round(((cash * rate * 0.01) / orderPrice) * 1000) / 1000
        ).toString(),
      );
    } else {
      let newVolume = volume * rate * 0.01;
      newVolume = parseFloat(newVolume.toFixed(10));
      setOrderVolume(newVolume);
      setFormattedVolume(newVolume.toString());
    }
  };

  const clickBuy = () => {
    if (user.user) {
      if (cash < resPrice) {
        setAlertContent("자산이 충분하지 않습니다.");
        setAlert(true);
        return;
      }
      if (orderVolume === 0) {
        setAlertContent(`주문 수량을 입력해주세요.`);
        setAlert(true);
        return;
      }
      buyLimit(
        coinCode!,
        orderVolume,
        orderPrice,
        () => {
          setAlertContent("주문이 등록되었습니다.");
          setAlert(true);
          getCashFunc();
          resetInput();
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
      if (volume < orderVolume) {
        setAlertContent("자산이 충분하지 않습니다.");
        setAlert(true);
        return;
      }
      if (orderVolume === 0) {
        setAlertContent(`주문 수량을 입력해주세요.`);
        setAlert(true);
        return;
      }
      sellLimit(
        coinCode!,
        orderVolume,
        orderPrice,
        () => {
          setAlertContent("주문이 등록되었습니다");
          setAlert(true);
          getCoinVolumeFunc();
          resetInput();
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

  const closeAlert = () => {
    setAlert(false);
  };

  useEffect(() => {
    setResPrice(Math.round(orderPrice * orderVolume));
  }, [orderPrice, orderVolume]);

  useEffect(() => {
    if (name === "매수") {
      setCommission(orderVolume * 0.0005);
    } else {
      setCommission(Math.round(resPrice * 0.0005));
    }
  }, [orderVolume, resPrice]);

  useEffect(() => {
    if (name === "매수") {
      setTotalAmount(orderVolume - commission);
    } else {
      setTotalAmount(resPrice - commission);
    }
  }, [orderVolume, commission]);

  return (
    <TradeContainer>
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
          <TitleDiv>{name} 가격</TitleDiv>
          <AmountDiv>
            <PriceInput
              value={formattedOrderPrice}
              onChange={(e) => {
                inputDecimalFormat(
                  e,
                  setFormattedOrderPrice,

                  setOrderPrice,
                  "매수",
                );
              }}
            />
            <UpdownButton onClick={priceUp}>▲</UpdownButton>
            <UpdownButton onClick={priceDown}>▼</UpdownButton>
          </AmountDiv>
        </TradeItem>
        <TradeItem>
          <TitleDiv>주문 수량</TitleDiv>
          <AmountDiv>
            <PriceInput
              value={formattedVolume}
              placeholder="0"
              onChange={(e) => {
                inputDecimalFormat(
                  e,
                  setFormattedVolume,
                  setOrderVolume,
                  "매도",
                );
              }}
            />
          </AmountDiv>
        </TradeItem>
        <TradeItem>
          <div />
          <PriceButtons>
            {volumeButtons.map((rate) => {
              return (
                <PriceButton key={rate} onClick={() => clickVolumeButton(rate)}>
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
        <GreenDiv $last={false}>
          <TradeItem>
            <TitleDiv>주문 금액</TitleDiv>
            <ContentDiv>
              {resPrice.toLocaleString("ko-KR")} <div>WON</div>
            </ContentDiv>
          </TradeItem>
        </GreenDiv>
        <GreenDiv $last={false}>
          <TradeItem>
            <TitleDiv>수수료</TitleDiv>
            <ContentDiv>
              {name === "매수"
                ? parseFloat(commission.toFixed(10))
                : commission.toLocaleString("ko-KR")}
              <div>{name === "매수" ? coinCode!.split("-")[1] : "WON"}</div>
            </ContentDiv>
          </TradeItem>
        </GreenDiv>
        <GreenDiv $last>
          <TradeItem>
            <TitleDiv>총 획득량</TitleDiv>
            <ContentDiv>
              {name === "매수"
                ? parseFloat(totalAmount.toFixed(10))
                : totalAmount.toLocaleString("ko-KR")}
              <div>{name === "매수" ? coinCode!.split("-")[1] : "WON"}</div>
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

export default NormalTradeItem;
