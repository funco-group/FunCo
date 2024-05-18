import React, { useEffect } from 'react'
import { futuresHistory } from '@/apis/trade'
import { usePathname } from 'next/navigation'
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from '@/styles/CommonStyled'
import { TradeListContainer } from '../SpotTrade/TradeList.styled'

function TradeList() {
  const columns = ['주문시간', '구분', '주문가격', '레버리지', '정산']
  const pathname = usePathname()
  const coinCode = pathname?.split('/')[3]

  useEffect(() => {
    futuresHistory(coinCode, () => {})
  }, [])

  return (
    <div>
      <ColumnContainer>
        <ColumnGrid $column="5.5rem 6rem 7.2rem 7.1rem 3.5rem">
          {columns.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <TradeListContainer>
        {/* {!user.user && <NoTradeData>로그인 후 확인할 수 있습니다.</NoTradeData>}
        {user.user && !tradeList && (
          <NoTradeData>거래 내역이 없습니다.</NoTradeData>
        )}
        {user.user &&
          tradeList &&
          tradeList.map((trade) => <TradeListItem key={trade.id} />)} */}
      </TradeListContainer>
    </div>
  )
}

export default TradeList
