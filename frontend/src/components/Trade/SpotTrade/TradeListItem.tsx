import { TradeListType } from "@/interfaces/TradeType";
import {
  TradeListItemContainer,
  TradeItemDiv,
  TypeColumnDiv,
  CancleButton,
  OrderPriceDiv,
  TradeVolumeDiv,
  TradeDateDiv,
} from "./TradeListItem.styled";

interface TradeListItemProps {
  trade: TradeListType;
  selected: string;
  clickCancle: (id: number) => void;
}

function TradeListItem({ trade, selected, clickCancle }: TradeListItemProps) {
  const concluded = selected === "체결";

  return (
    <TradeListItemContainer $concluded={concluded}>
      <TradeItemDiv $last={false}>
        <TradeDateDiv>{trade.tradeDate}</TradeDateDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={false}>
        <TypeColumnDiv type={trade.tradeType}>
          {trade.ticker}
          <div>{trade.tradeType === "BUY" ? "매수" : "매도"}</div>
        </TypeColumnDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={false}>
        <OrderPriceDiv>
          {trade.orderCash.toLocaleString()}
          <div>{trade.price.toLocaleString()}</div>
        </OrderPriceDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={concluded}>
        <TradeVolumeDiv>{trade.volume}</TradeVolumeDiv>
      </TradeItemDiv>
      {!concluded && (
        <TradeItemDiv $last>
          <CancleButton
            onClick={() => {
              clickCancle(trade.id);
            }}
          >
            취소
          </CancleButton>
        </TradeItemDiv>
      )}
    </TradeListItemContainer>
  );
}

export default TradeListItem;
