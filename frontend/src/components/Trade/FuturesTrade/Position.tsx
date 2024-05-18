'use client'

import React, { useEffect, useState } from 'react'
import { ContentDiv, TradeItem } from '@/styles/Trade.styled'
import { PriceType } from '@/interfaces/PriceWindowType'
import { FuturesType } from '@/interfaces/TradeType'
import AlertModal from '@/components/Common/Modal/AlertModal'
import { PositionTitleDiv, PositionTitleContainer } from './Position.styled'
import {
  ContentContainer,
  OrderBookContainer,
  TitleDiv,
} from '../SpotTrade/OrderBook.styled'

function parseDate(date: string) {
  const parsedDate = new Date(date)

  const year = parsedDate.getFullYear()
  const month = String(parsedDate.getMonth() + 1).padStart(2, '0')
  const day = String(parsedDate.getDate()).padStart(2, '0')
  const hours = String(parsedDate.getHours()).padStart(2, '0')
  const minutes = String(parsedDate.getMinutes()).padStart(2, '0')
  const seconds = String(parsedDate.getSeconds()).padStart(2, '0')

  return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분 ${seconds}초`
}

interface PositionProps {
  isTrade: boolean
  setIsTrade: React.Dispatch<React.SetStateAction<boolean>>
  coin: PriceType | null
  trade: FuturesType | null
}

interface PositionType {
  title: string
  data: string | number
  unit: string
  color: string
}

const Position = React.memo(
  ({ isTrade, setIsTrade, coin, trade }: PositionProps) => {
    const [positions, setPositions] = useState<PositionType[]>([])
    const [alert, setAlert] = useState<boolean>(false)

    const closeAlert = () => {
      setAlert(false)
    }

    useEffect(() => {
      if (isTrade && trade && coin !== null) {
        let priceChange
        let profit
        if (trade.tradeType === 'LONG') {
          priceChange = ((coin.tradePrice - trade.price) / trade.price) * 100
          profit =
            ((trade.orderCash * (coin.tradePrice - trade.price)) /
              trade.price) *
            trade.leverage
        } else {
          priceChange = ((trade.price - coin.tradePrice) / trade.price) * 100
          profit =
            ((trade.orderCash * (trade.price - coin.tradePrice)) /
              trade.price) *
            trade.leverage
        }

        if (trade.orderCash - profit <= 0) {
          setIsTrade(false)
          setAlert(true)
        }

        setPositions([
          {
            title: '진입 날짜',
            data: parseDate(trade.tradeDate),
            unit: '',
            color: '',
          },
          {
            title: '거래 종류',
            data: trade.tradeType,
            unit: '',
            color: trade.tradeType === 'LONG' ? 'red' : 'blue',
          },
          {
            title: '레버리지',
            data: trade.leverage,
            unit: 'X',
            color: '',
          },
          {
            title: '주문금액',
            data: trade.orderCash.toLocaleString('ko-KR'),
            unit: 'WON',
            color: '',
          },
          {
            title: '변동폭',
            data: Math.round(priceChange * 100) / 100,
            unit: '%',
            color: priceChange > 0 ? 'red' : 'blue',
          },
          {
            title: '수익률',
            data: Math.round(priceChange * trade.leverage * 100) / 100,
            unit: '%',
            color: priceChange > 0 ? 'red' : 'blue',
          },
          {
            title: '손익',
            data: Math.ceil(profit).toLocaleString('ko-KR'),
            unit: 'WON',
            color: priceChange > 0 ? 'red' : 'blue',
          },
          {
            title: '정산금',
            data: Math.ceil(trade.orderCash - profit).toLocaleString('ko-KR'),
            unit: 'WON',
            color: priceChange > 0 ? 'red' : 'blue',
          },
        ])
      }
    }, [trade, coin])

    useEffect(() => {
      if (!isTrade) {
        setPositions([
          { title: '진입 날짜', data: '-', unit: '', color: '' },
          { title: '거래 종류', data: '-', unit: '', color: '' },
          { title: '레버리지', data: '-', unit: '', color: '' },
          { title: '주문금액', data: '-', unit: '', color: '' },
          { title: '변동폭', data: '-', unit: '', color: '' },
          { title: '수익률', data: '-', unit: '', color: '' },
          { title: '손익', data: '-', unit: '', color: '' },
          { title: '정산금', data: '-', unit: '', color: '' },
        ])
      }
    }, [isTrade])

    return (
      <OrderBookContainer>
        {alert && (
          <AlertModal
            title="알림"
            content="주문이 강제 청산되었습니다."
            closeAlert={closeAlert}
          />
        )}
        <PositionTitleContainer>
          <PositionTitleDiv>포지션 현황</PositionTitleDiv>
        </PositionTitleContainer>
        <ContentContainer>
          {positions.map((position) => (
            <TradeItem key={position.title}>
              <TitleDiv>{position.title}</TitleDiv>
              <ContentDiv color={position.color} $bold>
                {position.data} <span>{position.unit}</span>
              </ContentDiv>
            </TradeItem>
          ))}
        </ContentContainer>
      </OrderBookContainer>
    )
  },
)

export default Position
