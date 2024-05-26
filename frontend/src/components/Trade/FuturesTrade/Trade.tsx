'use client'

import React, { useState } from 'react'
import { TradeContainer } from '@/styles/Trade.styled'
import Tab from '@/components/Common/Tab/TableTab'
import { useRecoilValue } from 'recoil'
import { userState } from '@/recoils/user'
import useLoginAlertModalState from '@/hooks/recoilHooks/useLoginAlertModalState'
import FuturesTrade from './FuturesTrade'
import TradeList from './TradeList'

interface TradeProps {
  futureId: number
  isTrade: boolean
  setIsTrade: React.Dispatch<React.SetStateAction<boolean>>
}

const Trade = React.memo(({ futureId, isTrade, setIsTrade }: TradeProps) => {
  const tabs = ['선물거래', '거래내역']
  const [activeTab, setActiveTab] = useState<string>('선물거래')
  const user = useRecoilValue(userState)
  const { onLoginAlertModal } = useLoginAlertModalState()

  const changeTab = (tab: string) => {
    if (!user.user) {
      onLoginAlertModal()
    }
    setActiveTab(tab)
  }

  return (
    <TradeContainer>
      <Tab
        columns={2}
        tabs={tabs}
        activeTab={activeTab}
        changeTab={changeTab}
      />
      {activeTab === '선물거래' && (
        <FuturesTrade
          futureId={futureId}
          isTrade={isTrade}
          setIsTrade={setIsTrade}
        />
      )}
      {activeTab === '거래내역' && <TradeList />}
    </TradeContainer>
  )
})

export default Trade
