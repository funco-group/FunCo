'use client'

/* eslint-disable no-nested-ternary */

import TabButton from '@/components/Common/Button/TabButton.styled'
import { AssetTabType } from '@/interfaces/tradeHistory/follow/AssetChangeType'
import { DateTabContainer, TabDiv, TabTitleDiv } from './DateTab.styled'

interface DateTabProps {
  periodTab: AssetTabType[]
  tradeTypeTab: AssetTabType[][]
  assetTypeTab: AssetTabType[]
  periodActive: number
  tradeActive: number
  assetActive: number
  setPeriodActive: React.Dispatch<React.SetStateAction<number>>
  setTradeActive: React.Dispatch<React.SetStateAction<number>>
  setAssetActive: React.Dispatch<React.SetStateAction<number>>
}

function DateTab({
  periodTab,
  tradeTypeTab,
  assetTypeTab,
  periodActive,
  tradeActive,
  assetActive,
  setPeriodActive,
  setTradeActive,
  setAssetActive,
}: DateTabProps) {
  const dateClick = (tab: number) => {
    setPeriodActive(tab)
  }

  const tradeClick = (tab: number) => {
    setTradeActive(tab)
  }

  const assetClick = (tab: number) => {
    setAssetActive(tab)
    setTradeActive(0)
  }

  const borderRadius = (id: number, lastId: number) => {
    if (id === 0) {
      return 'left'
    }
    if (id === lastId) {
      return 'right'
    }
    return ''
  }

  return (
    <DateTabContainer>
      <TabDiv>
        <TabTitleDiv>
          기간 <span>2024년 01월 01일 ~ 2024년 03월 12일</span>
        </TabTitleDiv>
        {periodTab.map((tab) => (
          <TabButton
            key={tab.id}
            width="4rem"
            height="2.5rem"
            $active={tab.id === periodActive}
            onClick={() => dateClick(tab.id)}
            radius={borderRadius(tab.id, 3)}
          >
            {tab.typeKor}
          </TabButton>
        ))}
      </TabDiv>
      <TabDiv>
        <TabTitleDiv>거래종류</TabTitleDiv>
        {tradeTypeTab[assetActive].map((tab) => (
          <TabButton
            key={tab.id}
            width="4rem"
            height="2.5rem"
            $active={tab.id === tradeActive}
            onClick={() => tradeClick(tab.id)}
            radius={borderRadius(tab.id, 2)}
          >
            {tab.typeKor}
          </TabButton>
        ))}
      </TabDiv>
      <TabDiv>
        <TabTitleDiv>자산종류</TabTitleDiv>
        {assetTypeTab.map((tab) => (
          <TabButton
            key={tab.id}
            width="5rem"
            height="2.5rem"
            $active={tab.id === assetActive}
            onClick={() => assetClick(tab.id)}
            radius={borderRadius(tab.id, 3)}
          >
            {tab.typeKor}
          </TabButton>
        ))}
      </TabDiv>
    </DateTabContainer>
  )
}

export default DateTab
