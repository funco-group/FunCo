'use client'

import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from '@/styles/CommonStyled'
import { useEffect, useState } from 'react'
import { AssetHistoryType } from '@/interfaces/AssetType'
import { AxiosResponse } from 'axios'
import { getHistory } from '@/apis/asset'
import {
  AssetChangeListContainer,
  HistoryListContainer,
} from './AssetChangeList.styled'
import AssetChangeListItem from './AssetChangeListItem'
import { AssetChangeListItemContainer } from './AssetChangeListItem.styled'
import { AssetTabType } from '@/interfaces/tradeHistory/follow/AssetChangeType'

interface AssetChangeListProps {
  periodTab: AssetTabType[]
  tradeTypeTab: AssetTabType[][]
  assetTypeTab: AssetTabType[]
  periodActive: number
  tradeActive: number
  assetActive: number
}

function AssetChangeList({
  periodTab,
  tradeTypeTab,
  assetTypeTab,
  periodActive,
  tradeActive,
  assetActive,
}: AssetChangeListProps) {
  const [historyList, setHistoryList] = useState<AssetHistoryType[]>([])
  const columns = [
    '체결시간',
    '보유자산',
    '종류',
    '거래수량',
    '거래단가',
    '거래금액',
    '수수료',
    '정산금액',
  ]

  useEffect(() => {
    getHistory(
      periodTab[periodActive].type,
      assetTypeTab[assetActive].type,
      tradeTypeTab[assetActive][tradeActive].type,
      (response: AxiosResponse<AssetHistoryType[]>) => {
        const { data } = response
        console.log(data)
        // setHistoryList(data)
      },
    )
  }, [periodActive, assetActive, tradeActive])

  return (
    <AssetChangeListContainer>
      <ColumnContainer>
        <AssetChangeListItemContainer>
          <ColumnGrid $column="7rem 6rem 5rem 1.3fr 1fr 1fr 1fr 1fr">
            {columns.map((column) => (
              <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
            ))}
          </ColumnGrid>
        </AssetChangeListItemContainer>
      </ColumnContainer>
      {/* <HistoryListContainer>
        {historyList.map((history) => (
          <AssetChangeListItem key={history.date} history={history} />
        ))}
      </HistoryListContainer> */}
    </AssetChangeListContainer>
  )
}

export default AssetChangeList
