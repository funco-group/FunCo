import { useState, useEffect, useMemo } from "react";
import { AxiosResponse } from "axios";
import { useRecoilValue } from "recoil";
import {
  PriceWindowContainer,
  ColumnTitleDiv,
  PriceItemContainer,
} from "./PriceWindow.styled";
import tickerWebSocket from "@/sockets/tickerWebSocket";
import {
  PriceType,
  FavoriteCoinResponseType,
  WebSocketHandlers,
  HoldingCoinResponseType,
} from "@/interfaces/PriceWindowType";
import {
  addFavoriteCoin,
  getFavoriteCoinList,
  removeFavoriteCoin,
} from "@/apis/crypto";
import CoinSearch from "./CoinSearch";
import PriceItem from "./PriceItem";
import Tab from "@/components/crypto/Tab";
import codeListState from "@/recoils/crypto/withCodeList";
import { getHoldingCoin } from "@/apis/trade";
import { ColumnContainer, ColumnGrid } from "@/styles/CommonStyled";
import userState from "@/recoils/user";
import useLoginAlertModalState from "@/hooks/recoilHooks/useLoginAlertModalState";

interface PriceWindowProps {
  priceList: PriceType[];
  setPriceList: React.Dispatch<React.SetStateAction<PriceType[]>>;
}

function PriceWindow({ priceList, setPriceList }: PriceWindowProps) {
  const codes = useRecoilValue(codeListState);
  const tabs = ["원화", "보유", "관심"];
  const columnList = ["한글명", "현재가", "전일대비", "거래대금"];
  const [activeTab, setActiveTab] = useState<string>("원화");
  const [socket, setSocket] = useState<WebSocketHandlers | undefined>(
    undefined,
  );
  const [favCoins, setFavCoins] = useState<string[]>([]);
  const [holdingCoins, setHoldingCoins] = useState<string[]>([]);
  const [status, setStatus] = useState<number | undefined>(-1);
  const [search, setSearch] = useState<string>("");
  const { onLoginAlertModal } = useLoginAlertModalState();
  const user = useRecoilValue(userState);

  useMemo(() => {
    // 1. 소켓 연결
    const tickerSocket = tickerWebSocket(setPriceList, setStatus);
    setSocket(tickerSocket);

    // 2. 보유 코인 갖고 오기
    if (user.user) {
      getHoldingCoin((response: AxiosResponse<HoldingCoinResponseType>) => {
        const { data } = response;
        setHoldingCoins(data.holdingCoins);
      });

      // 3. 관심 코인 갖고 오기
      getFavoriteCoinList(
        (response: AxiosResponse<FavoriteCoinResponseType>) => {
          const { data } = response;
          setFavCoins(data.tickers);
        },
      );
    }
  }, []);

  useEffect(() => {
    return () => {
      if (socket?.status === WebSocket.OPEN) {
        socket.close();
      }
    };
  }, []);

  useEffect(() => {
    if (socket?.status === WebSocket.OPEN) {
      if (activeTab === "원화") {
        socket.send(JSON.stringify(codes));
      } else if (activeTab === "보유") {
        socket.send(JSON.stringify(holdingCoins));
      } else if (activeTab === "관심") {
        socket.send(JSON.stringify(favCoins));
      }
    }
  }, [
    activeTab,
    favCoins,
    socket?.status,
    socket,
    codes,
    status,
    holdingCoins,
  ]);

  const changeTab = (tab: string) => {
    if (user.user) {
      setActiveTab(tab);
    } else {
      if (tab === "보유" || tab === "관심") {
        onLoginAlertModal();
      }
    }
  };

  const clickFavorite = (code: string, e: React.MouseEvent<HTMLElement>) => {
    e.stopPropagation();
    if (user.user) {
      if (favCoins.includes(code)) {
        removeFavoriteCoin(code);
        setFavCoins(favCoins.filter((favCoin) => favCoin !== code));
      } else {
        addFavoriteCoin(code);
        setFavCoins([...favCoins, code]);
      }
    } else {
      onLoginAlertModal();
    }
  };

  const filterPricesByTab = (price: PriceType) => {
    switch (activeTab) {
      case "원화":
        return price.koreanName.includes(search);
      case "보유":
        return (
          holdingCoins.includes(price.code) && price.koreanName.includes(search)
        );
      case "관심":
        return (
          favCoins.includes(price.code) && price.koreanName.includes(search)
        );
      default:
        return false;
    }
  };

  if (!priceList) return null;

  return (
    <PriceWindowContainer>
      <CoinSearch setSearch={setSearch} />
      <Tab
        columns={3}
        tabs={tabs}
        activeTab={activeTab}
        changeTab={changeTab}
      />
      <ColumnContainer>
        <ColumnGrid column="0.2fr 0.8fr 0.7fr 0.55fr 0.8fr">
          <div />
          {columnList.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <PriceItemContainer>
        {priceList.filter(filterPricesByTab).map((price: PriceType) => (
          <PriceItem
            key={price.code}
            price={price}
            isFav={favCoins.includes(price.code)}
            onClickFavorite={clickFavorite}
          />
        ))}
      </PriceItemContainer>
    </PriceWindowContainer>
  );
}

export default PriceWindow;
