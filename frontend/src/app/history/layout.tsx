'use client'

import HistoryTab from '@/components/TradeHistory/HistoryTab'
import WideLayout from '@/components/layout/WideLayout'
import { CryptoPageContainer } from '@/containers/CryptoContainer/styled'
import TradeHistoryPageContainer from '@/containers/TradeHistoryContainer/styled'

function HistoryLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <WideLayout>
        <CryptoPageContainer>
          <TradeHistoryPageContainer>
            <HistoryTab />
            {children}
          </TradeHistoryPageContainer>
          <div />
        </CryptoPageContainer>
      </WideLayout>
    </div>
  )
}

export default HistoryLayout
