'use client'

import { useEffect, useState } from 'react'
import { usePathname } from 'next/navigation'
import {
  OrderBookContainer,
  TitleContainer,
  TitleDiv,
  ContentContainer,
  ContentDiv,
  SizeDiv,
  PriceDiv,
} from './OrderBook.styled'
import orderbookWebSockets from '@/sockets/orderbookWebSocket'
import { WebSocketHandlers } from '@/interfaces/PriceWindowType'
import { OrderType } from '@/interfaces/CryptoType'

function OrderBook() {
  const pathname = usePathname()
  const [status, setStatus] = useState<number | undefined>(-1)

  const topTitle = ['매도', '호가', '']
  const BottomTitle = ['', '호가', '매수']

  const coinCode = pathname?.split('/')[3]
  const [orderList, setOrderList] = useState<OrderType[]>(
    Array.from({ length: 9 }, () => ({
      ask_price: 0,
      bid_price: 0,
      ask_size: 0,
      bid_size: 0,
    })),
  )

  const [socket, setSocket] = useState<WebSocketHandlers | undefined>(undefined)

  useEffect(() => {
    const orderbookSocket = orderbookWebSockets(setOrderList, setStatus)
    setSocket(orderbookSocket)

    return () => {
      if (orderbookSocket.status === WebSocket.OPEN) {
        orderbookSocket.close()
      }
    }
  }, [])

  useEffect(() => {
    if (socket?.status === WebSocket.OPEN) {
      socket.send(JSON.stringify([coinCode]))
    }
    console.log(socket?.status)
  }, [coinCode, socket?.status, socket, status])

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
        {[...orderList].reverse().map((order, index) => (
          <ContentDiv key={index}>
            <SizeDiv $buy={false}>{order.ask_size.toFixed(3)}</SizeDiv>
            <PriceDiv $buy={false}>
              {order.ask_price.toLocaleString('ko-KR')}
            </PriceDiv>
            <div />
          </ContentDiv>
        ))}
        {orderList.map((order, index) => (
          <ContentDiv key={index}>
            <div />
            <PriceDiv $buy>{order.bid_price.toLocaleString('ko-KR')}</PriceDiv>
            <SizeDiv $buy>{order.bid_size.toFixed(3)}</SizeDiv>
          </ContentDiv>
        ))}
      </ContentContainer>
      <TitleContainer $top={false}>
        {BottomTitle.map((title) => (
          <TitleDiv type={title} key={title}>
            {title}
          </TitleDiv>
        ))}
      </TitleContainer>
    </OrderBookContainer>
  )
}

export default OrderBook
