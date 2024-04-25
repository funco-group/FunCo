import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from "@/styles/CommonStyled";
import { AxiosResponse } from "axios";
import { useState, useEffect } from "react";
import {
  AssetChangeListContainer,
  HistoryListContainer,
} from "../TradeHistoryPage/AssetChangePage/AssetChangeList.styled";
import AssetChangeListItem from "../TradeHistoryPage/AssetChangePage/AssetChangeListItem";
import { AssetChangeListItemContainer } from "../TradeHistoryPage/AssetChangePage/AssetChangeListItem.styled";
import { ModalTitle } from "./PortfolioModal.styled";
import { getUserTradeList } from "@/apis/trade";
import { AssetHistoryType } from "@/interfaces/AssetType";

interface PortfolioAssetChangeProps {
  memberId: number;
}

function PortfolioAssetChange({ memberId }: PortfolioAssetChangeProps) {
  const [historyList, setHistoryList] = useState<AssetHistoryType[]>([]);
  const columns = [
    "체결시간",
    "보유자산",
    "종류",
    "거래수량",
    "거래단가",
    "거래금액",
    "수수료",
    "정산금액",
  ];

  useEffect(() => {
    getUserTradeList(
      memberId,
      0,
      10,
      (response: AxiosResponse<AssetHistoryType[]>) => {
        const { data } = response;
        setHistoryList(data);
      },
    );
  }, []);
  return (
    <div>
      <AssetChangeListContainer>
        <ModalTitle>자산변동 내역</ModalTitle>
        <ColumnContainer>
          <AssetChangeListItemContainer>
            <ColumnGrid column="7rem 6rem 5rem 1.3fr 1fr 1fr 1fr 1fr">
              {columns.map((column) => (
                <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
              ))}
            </ColumnGrid>
          </AssetChangeListItemContainer>
        </ColumnContainer>
        <HistoryListContainer>
          {historyList.map((history) => {
            return <AssetChangeListItem key={history.date} history={history} />;
          })}
        </HistoryListContainer>
      </AssetChangeListContainer>
    </div>
  );
}

export default PortfolioAssetChange;
