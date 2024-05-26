'use client'

import PriceWindow from '@/components/Common/PriceWindow/PriceWindow'
import HistoryTab from '@/components/TradeHistory/HistoryTab'
import WideLayout from '@/components/layout/WideLayout'
import { CryptoPageContainer } from '@/containers/CryptoContainer/styled'
import TradeHistoryPageContainer from '@/containers/TradeHistoryContainer/styled'
import { PriceType } from '@/interfaces/PriceWindowType'
import { priceListState } from '@/recoils/crypto'
import { useState } from 'react'
import { useRecoilValue } from 'recoil'

function HistoryLayout({ children }: { children: React.ReactNode }) {
  const [priceList, setPriceList] = useState<PriceType[]>(
    useRecoilValue(priceListState),
  )

  return (
    <div>
      <WideLayout>
        <CryptoPageContainer>
          <TradeHistoryPageContainer>
            <HistoryTab />
            {children}
          </TradeHistoryPageContainer>
          <PriceWindow priceList={priceList} setPriceList={setPriceList} />
        </CryptoPageContainer>
      </WideLayout>
    </div>
  )
}

export default HistoryLayout
