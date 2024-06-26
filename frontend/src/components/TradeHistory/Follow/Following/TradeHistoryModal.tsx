import { useEffect, useState } from 'react'
import { ColumnContainer, ColumnTitleDiv } from '@/styles/CommonStyled'
import { TradeListType } from '@/interfaces/TradeType'
import tradeTypeMap from '@/lib/tradeTypeMap'
import { getFollowingTradeList } from '@/apis/follow'
import { OrderTypeSpan } from '@/components/TradeHistory/OpenOrder/OpenOrderContent.styled'
import parseDate from '@/utils/parseDate'
import {
  FollowTradeHistoryContainer,
  FollowTradeHistoryColumnGridDiv,
  FollowTradeHistoryContentDiv,
  FollowTradeHistoryContentInnerDiv,
  FollowTradeHistoryDateDiv,
  FollowTradeHistoryTextAlignDiv,
} from './TradeHistoryModal.styled'
import FollowingModal from './FollowingModal'

interface TradeHistoryModalProps {
  handleTradeHistoryClick: () => void
  followId: number
}

function TradeHistoryModal({
  handleTradeHistoryClick,
  followId,
}: TradeHistoryModalProps) {
  const tradeHistortColumnList = ['거래시간', '구분', '거래가격', '거래수량']
  const [tradeHistoryList, setTradeHistoryList] = useState<TradeListType[]>()
  const [isLoading, setIsLoading] = useState(false)

  useEffect(() => {
    setIsLoading(true)
    getFollowingTradeList(followId, 0, 100, (res) => {
      const { data } = res
      setTradeHistoryList(data)
    })
  }, [])

  useEffect(() => {
    if (tradeHistoryList !== undefined) {
      setIsLoading(false)
    }
  }, [tradeHistoryList])

  if (isLoading) {
    // eslint-disable-next-line react/jsx-no-useless-fragment
    return <></>
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
                    {tradeHistory.price.toLocaleString('en-US')}
                  </FollowTradeHistoryTextAlignDiv>
                  <FollowTradeHistoryTextAlignDiv>
                    {tradeHistory.orderCash.toLocaleString('en-US')}
                  </FollowTradeHistoryTextAlignDiv>
                  {(tradeHistory.tradeType === 'LONG' ||
                    tradeHistory.tradeType === 'SHORT') && (
                    <FollowTradeHistoryTextAlignDiv>
                      {tradeHistory.volume.toLocaleString('en-US')}
                    </FollowTradeHistoryTextAlignDiv>
                  )}
                </FollowTradeHistoryContentInnerDiv>
                <FollowTradeHistoryContentInnerDiv>
                  {tradeHistory.tradeType === 'LONG' ||
                  tradeHistory.tradeType === 'SHORT'
                    ? '-'
                    : tradeHistory.volume}
                </FollowTradeHistoryContentInnerDiv>
              </FollowTradeHistoryColumnGridDiv>
            ))}
        </FollowTradeHistoryContentDiv>
      </FollowTradeHistoryContainer>
    </FollowingModal>
  )
}

export default TradeHistoryModal
