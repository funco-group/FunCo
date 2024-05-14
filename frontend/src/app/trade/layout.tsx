'use client'

import PriceWindow from '@/components/Common/PriceWindow/PriceWindow'
import WideLayout from '@/components/layout/WideLayout'
import React, { useState } from 'react'
import { PriceType } from '@/interfaces/PriceWindowType'
import { useRecoilValue } from 'recoil'
import priceListState from '@/recoils/crypto/atoms'
import {
  BottomContainer,
  CryptoPageContainer,
} from '@/containers/CryptoContainer/styled'

function TradeLayout({ children }: { children: React.ReactNode }) {
  const [priceList, setPriceList] = useState<PriceType[]>(
    useRecoilValue(priceListState),
  )

  return (
    <WideLayout>
      <CryptoPageContainer>
        <div>
          {/* <Chart priceList={priceList} /> */}
          <div></div>
          <BottomContainer>{children}</BottomContainer>
        </div>
        <PriceWindow priceList={priceList} setPriceList={setPriceList} />
      </CryptoPageContainer>
    </WideLayout>
  )
}

export default TradeLayout
