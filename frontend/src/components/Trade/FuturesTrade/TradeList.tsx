import React, { useEffect, useState } from 'react'
import { futuresHistory } from '@/apis/trade'
import { usePathname } from 'next/navigation'
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from '@/styles/CommonStyled'
import { NoTradeData, TradeListContainer } from '../SpotTrade/TradeList.styled'
import { FuturesType } from '@/interfaces/TradeType'
import { useRecoilValue } from 'recoil'
import { userState } from '@/recoils/user'
import TradeListItem from './TradeListItem'

function TradeList() {
  const user = useRecoilValue(userState)
  const columns = ['주문시간', '구분', '주문가격', '레버리지', '정산']
  const pathname = usePathname()
  const coinCode = pathname?.split('/')[3]
  const [tradeList, setTradeList] = useState<FuturesType[]>([])

  useEffect(() => {
    futuresHistory(coinCode, (response) => {
      const { data } = response
      setTradeList(data)
    })
  }, [])

  return (
    <div>
      <ColumnContainer>
        <ColumnGrid $column="5.7rem 5.8rem 1fr 3.5rem 1fr">
          {columns.map((column) => (
            <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
          ))}
        </ColumnGrid>
      </ColumnContainer>
      <TradeListContainer>
        {!user.user && <NoTradeData>로그인 후 확인할 수 있습니다.</NoTradeData>}
        {user.user && !tradeList && (
          <NoTradeData>거래 내역이 없습니다.</NoTradeData>
        )}
        {user.user &&
          tradeList &&
          tradeList.map((trade) => (
            <TradeListItem key={trade.id} trade={trade} />
          ))}
      </TradeListContainer>
    </div>
  )
}

export default TradeList
