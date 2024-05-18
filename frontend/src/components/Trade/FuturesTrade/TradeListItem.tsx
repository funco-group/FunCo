import React from 'react'
// import {
//   TradeDateDiv,
//   TradeItemDiv,
//   TradeListItemContainer,
//   TypeColumnDiv,
// } from '../SpotTrade/TradeListItem.styled'

function TradeListItem() {
  return (
    <div />
    // <TradeListItemContainer $concluded={concluded}>
    //   <TradeItemDiv $last={false}>
    //     <TradeDateDiv>{trade.tradeDate}</TradeDateDiv>
    //   </TradeItemDiv>
    //   <TradeItemDiv $last={false}>
    //     <TypeColumnDiv type={trade.tradeType}>
    //       {trade.ticker}
    //       <div>{trade.tradeType === 'BUY' ? '매수' : '매도'}</div>
    //     </TypeColumnDiv>
    //   </TradeItemDiv>
    //   <TradeItemDiv $last={false}>
    //     <OrderPriceDiv>
    //       {trade.orderCash.toLocaleString()}
    //       <div>{trade.price.toLocaleString()}</div>
    //     </OrderPriceDiv>
    //   </TradeItemDiv>
    //   <TradeItemDiv $last={concluded}>
    //     <TradeVolumeDiv>{trade.volume}</TradeVolumeDiv>
    //   </TradeItemDiv>
    // </TradeListItemContainer>
  )
}

export default TradeListItem
