import React from 'react'
import {
  OrderPriceDiv,
  TradeDateDiv,
  TradeItemDiv,
  TradeListItemContainer,
  TradeVolumeDiv,
  TypeColumnDiv,
} from '../SpotTrade/TradeListItem.styled'
import { FuturesType } from '@/interfaces/TradeType'
import parseDate from '@/utils/parseDate'

interface TradeListItemProps {
  trade: FuturesType
}

function TradeListItem({ trade }: TradeListItemProps) {
  return (
    <TradeListItemContainer $column="5.7rem 5.8rem 1fr 3.5rem 1fr">
      <TradeItemDiv $last={false}>
        <TradeDateDiv>{parseDate(trade.tradeDate)}</TradeDateDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={false}>
        <TypeColumnDiv type={trade.tradeType === 'LONG' ? 'BUY' : ''}>
          {trade.ticker}
          <div>{trade.tradeType}</div>
        </TypeColumnDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={false}>
        <OrderPriceDiv>
          {trade.orderCash.toLocaleString()}
          <div>{trade.price.toLocaleString()}</div>
        </OrderPriceDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={false}>
        <TradeVolumeDiv>{trade.leverage} X</TradeVolumeDiv>
      </TradeItemDiv>
      <TradeItemDiv $last={true}>
        <TradeVolumeDiv>{trade.settlement.toLocaleString()}</TradeVolumeDiv>
      </TradeItemDiv>
    </TradeListItemContainer>
  )
}

export default TradeListItem
