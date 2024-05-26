'use client'

import AssetChangeList from '@/components/TradeHistory/AssetChange/AssetChangeList'
import DateTab from '@/components/TradeHistory/AssetChange/DateTab'
import { AssetTabType } from '@/interfaces/tradeHistory/follow/AssetChangeType'
import { useState } from 'react'

function AssetChange() {
  const periodTab: AssetTabType[] = [
    {
      id: 0,
      type: 'WEEK',
      typeKor: '1주일',
    },
    {
      id: 1,
      type: 'ONEMONTH',
      typeKor: '1개월',
    },
    {
      id: 2,
      type: 'THREEMONTH',
      typeKor: '3개월',
    },
    {
      id: 3,
      type: 'SIXMONTH',
      typeKor: '6개월',
    },
  ]
  const tradeTypeTab: AssetTabType[][] = [
    [
      {
        id: 0,
        type: 'ALL',
        typeKor: '전체',
      },
      {
        id: 1,
        type: 'BUY',
        typeKor: '매수',
      },
      {
        id: 2,
        type: 'SELL',
        typeKor: '매도',
      },
    ],
    [
      {
        id: 0,
        type: 'ALL',
        typeKor: '전체',
      },
      {
        id: 1,
        type: 'LONG',
        typeKor: 'LONG',
      },
      {
        id: 2,
        type: 'SHORT',
        typeKor: 'SHORT',
      },
    ],
    [
      {
        id: 0,
        type: 'ALL',
        typeKor: '전체',
      },
      {
        id: 1,
        type: 'FOLLOWING',
        typeKor: '팔로잉',
      },
      {
        id: 2,
        type: 'FOLLOWER',
        typeKor: '팔로워',
      },
    ],
    [
      {
        id: 0,
        type: 'ALL',
        typeKor: '전체',
      },
      {
        id: 1,
        type: 'PURCHASE_PORTFOLIO',
        typeKor: '구매',
      },
      {
        id: 2,
        type: 'SELL_PORTFOLIO',
        typeKor: '판매',
      },
    ],
  ]

  const assetTypeTab: AssetTabType[] = [
    {
      id: 0,
      type: 'COIN',
      typeKor: '가상화폐',
    },
    {
      id: 1,
      type: 'FUTURES',
      typeKor: '선물',
    },
    {
      id: 2,
      type: 'FOLLOW',
      typeKor: '팔로우',
    },
    {
      id: 3,
      type: 'PORTFOLIO',
      typeKor: '포트폴리오',
    },
  ]
  const [periodActive, setPeriodActive] = useState<number>(0)
  const [tradeActive, setTradeActive] = useState<number>(0)
  const [assetActive, setAssetActive] = useState<number>(0)

  return (
    <div>
      <DateTab
        periodTab={periodTab}
        tradeTypeTab={tradeTypeTab}
        assetTypeTab={assetTypeTab}
        periodActive={periodActive}
        tradeActive={tradeActive}
        assetActive={assetActive}
        setPeriodActive={setPeriodActive}
        setTradeActive={setTradeActive}
        setAssetActive={setAssetActive}
      />
      <AssetChangeList
        periodTab={periodTab}
        tradeTypeTab={tradeTypeTab}
        assetTypeTab={assetTypeTab}
        periodActive={periodActive}
        tradeActive={tradeActive}
        assetActive={assetActive}
      />
    </div>
  )
}

export default AssetChange
