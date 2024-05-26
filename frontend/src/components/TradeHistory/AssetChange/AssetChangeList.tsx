'use client'

import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from '@/styles/CommonStyled'
import { useEffect, useState } from 'react'
import {
  CoinAssetType,
  FollowAssetType,
  FuturesAssetType,
  PortfolioAssetType,
} from '@/interfaces/AssetType'
import { AxiosResponse } from 'axios'
import { getHistory } from '@/apis/asset'
import { AssetTabType } from '@/interfaces/tradeHistory/follow/AssetChangeType'
import {
  AssetChangeListContainer,
  HistoryListContainer,
} from './AssetChangeList.styled'
import { AssetChangeListItemContainer } from './AssetChangeListItem.styled'
import CoinAssetListItem from './CoinAssetListItem'
import FuturesAssetListItem from './FuturesAssetListItem'
import FollowAssetListItem from './FollowAssetListItem'
import PortfolioAssetListItem from './PortfolioAssetListItem'

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
  const [historyList, setHistoryList] = useState<
    | CoinAssetType[]
    | FuturesAssetType[]
    | FollowAssetType[]
    | PortfolioAssetType[]
  >([])

  const coinColumns = [
    '체결시간',
    '보유자산',
    '종류',
    '거래수량',
    '거래단가',
    '변동금액',
    '자산',
  ]

  const futuresColumns = [
    '체결시간',
    '보유자산',
    '종류',
    '거래단가',
    '변동금액',
    '자산',
  ]

  const followColumns = [
    '체결시간',
    '종류',
    '거래금액',
    '투자손익',
    '수수료',
    '정산금액',
    '정산시간',
  ]

  const portfolioColumns = ['체결시간', '유저', '종류', '거래금액', '정산금액']

  const columns = () => {
    switch (assetActive) {
      case 0:
        return coinColumns
      case 1:
        return futuresColumns
      case 2:
        return followColumns
      default:
        return portfolioColumns
    }
  }

  const setColumn = () => {
    switch (assetActive) {
      case 0:
        return 'repeat(7, 1fr)'
      case 1:
        return 'repeat(6, 1fr)'
      case 2:
        return 'repeat(7, 1fr)'
      default:
        return 'repeat(5, 1fr)'
    }
  }

  useEffect(() => {
    getHistory(
      periodTab[periodActive].type,
      assetTypeTab[assetActive].type,
      tradeTypeTab[assetActive][tradeActive].type,
      (
        response: AxiosResponse<
          | CoinAssetType[]
          | FuturesAssetType[]
          | FollowAssetType[]
          | PortfolioAssetType[]
        >,
      ) => {
        const { data } = response
        setHistoryList(data)
      },
    )
  }, [periodActive, assetActive, tradeActive])

  const returnAssetListItem = (
    history:
      | CoinAssetType
      | FuturesAssetType
      | FollowAssetType
      | PortfolioAssetType,
    index: number,
  ) => {
    switch (assetActive) {
      case 0:
        return (
          <CoinAssetListItem key={index} history={history as CoinAssetType} />
        )
      case 1:
        return (
          <FuturesAssetListItem
            key={index}
            history={history as FuturesAssetType}
          />
        )
      case 2:
        return (
          <FollowAssetListItem
            key={index}
            history={history as FollowAssetType}
          />
        )
      case 3:
        return (
          <PortfolioAssetListItem
            key={index}
            history={history as PortfolioAssetType}
          />
        )
      default:
        return <div />
    }
  }

  return (
    <AssetChangeListContainer>
      <ColumnContainer>
        <AssetChangeListItemContainer>
          <ColumnGrid $column={setColumn()}>
            {columns().map((column) => (
              <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
            ))}
          </ColumnGrid>
        </AssetChangeListItemContainer>
      </ColumnContainer>
      <HistoryListContainer>
        {historyList.map((history, index) =>
          returnAssetListItem(history, index),
        )}
      </HistoryListContainer>
    </AssetChangeListContainer>
  )
}

export default AssetChangeList
