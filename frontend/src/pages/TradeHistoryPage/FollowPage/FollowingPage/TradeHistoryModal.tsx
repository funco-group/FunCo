import { useEffect, useState } from "react";
import { ColumnContainer, ColumnTitleDiv } from "@/styles/CommonStyled";
import FollowingModal from "./FollowingModal";
import {
  FollowTradeHistoryContainer,
  FollowTradeHistoryColumnGridDiv,
  FollowTradeHistoryContentDiv,
  FollowTradeHistoryContentInnerDiv,
  FollowTradeHistoryDateDiv,
  FollowTradeHistoryTextAlignDiv,
} from "./TradeHistoryModal.styled";
import { TradeListType } from "@/interfaces/TradeType";
import { OrderTypeSpan } from "../../OpenOrdersPage/OpenOrderContent.styled";
import tradeTypeMap from "@/lib/tradeTypeMap";
import useParseDate from "@/hooks/useParseDate";
import { getFollowingTradeList } from "@/apis/follow";

interface TradeHistoryModalProps {
  handleTradeHistoryClick: () => void;
  followId: number;
}

function TradeHistoryModal({
  handleTradeHistoryClick,
  followId,
}: TradeHistoryModalProps) {
  const tradeHistortColumnList = ["거래시간", "구분", "거래가격", "거래수량"];
  const [tradeHistoryList, setTradeHistoryList] = useState<TradeListType[]>();
  const [isLoading, setIsLoading] = useState(false);
  const parseDate = useParseDate;

  useEffect(() => {
    setIsLoading(true);
    getFollowingTradeList(followId, 0, 100, (res) => {
      const { data } = res;
      setTradeHistoryList(data);
    });
  }, []);

  useEffect(() => {
    if (tradeHistoryList !== undefined) {
      setIsLoading(false);
    }
  }, [tradeHistoryList]);

  if (isLoading) {
    return <></>;
  }

  return (
    <FollowingModal title="거래 내역" handleClick={handleTradeHistoryClick}>
      <FollowTradeHistoryContainer>
        <ColumnContainer>
          <FollowTradeHistoryColumnGridDiv>
            {tradeHistortColumnList.map((column) => (
              <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
            ))}
          </FollowTradeHistoryColumnGridDiv>
        </ColumnContainer>
        <FollowTradeHistoryContentDiv>
          {tradeHistoryList !== undefined &&
            tradeHistoryList.map((tradeHistory) => (
              <FollowTradeHistoryColumnGridDiv key={tradeHistory.id}>
                <FollowTradeHistoryDateDiv>
                  {parseDate(tradeHistory.tradeDate)}
                </FollowTradeHistoryDateDiv>
                <FollowTradeHistoryContentInnerDiv>
                  <div>{tradeHistory.ticker}</div>
                  <div>
                    <OrderTypeSpan type={tradeHistory.tradeType}>
                      {tradeTypeMap.get(tradeHistory.tradeType)}
                    </OrderTypeSpan>
                  </div>
                </FollowTradeHistoryContentInnerDiv>
                <FollowTradeHistoryContentInnerDiv>
                  <FollowTradeHistoryTextAlignDiv>
                    {tradeHistory.price.toLocaleString("en-US")}
                  </FollowTradeHistoryTextAlignDiv>
                  <FollowTradeHistoryTextAlignDiv>
                    {tradeHistory.orderCash.toLocaleString("en-US")}
                  </FollowTradeHistoryTextAlignDiv>
                </FollowTradeHistoryContentInnerDiv>
                <FollowTradeHistoryContentInnerDiv>
                  {tradeHistory.volume}
                </FollowTradeHistoryContentInnerDiv>
              </FollowTradeHistoryColumnGridDiv>
            ))}
        </FollowTradeHistoryContentDiv>
      </FollowTradeHistoryContainer>
    </FollowingModal>
  );
}

export default TradeHistoryModal;
