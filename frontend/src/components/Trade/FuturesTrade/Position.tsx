'use client'

import React from 'react'
import {
  ContentContainer,
  OrderBookContainer,
  TitleContainer,
  TitleDiv,
} from '../SpotTrade/OrderBook.styled'
import { TradeItem } from '@/styles/Trade.styled'

function Position() {
  const topTitle = ['포지션 현황', '', '']
  // const positions = [
  //   {
  //     "title": "주문금액",
  //     "data": 243,343
  //   },
  // {
  //   title: "변동폭",
  //   data: 243,343
  // },
  // {
  //   title: "수익률",
  //   data: 243,343
  // },
  // {
  //   title: "손익",
  //   data: 243,343
  // },
  // {
  //   title: "레버리지",
  //   data: 243,343
  // },
  // {
  //   title: "정산금",
  //   data: 243,343
  // },
  // ]

  return (
    <OrderBookContainer>
      <TitleContainer $top>
        {topTitle.map((title) => (
          <TitleDiv type={title} key={title}>
            {title}
          </TitleDiv>
        ))}
      </TitleContainer>
      <ContentContainer>
        <TradeItem>
          <TitleDiv>주문금액</TitleDiv>
        </TradeItem>
      </ContentContainer>
    </OrderBookContainer>
  )
}

export default Position
