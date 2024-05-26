'use client'

import PriceWindow from '@/components/Common/PriceWindow/PriceWindow'
import Chart from '@/components/Trade/Chart'
import OrderBook from '@/components/Trade/SpotTrade/OrderBook'
import Trade from '@/components/Trade/SpotTrade/Trade'
import {
  BottomContainer,
  CryptoPageContainer,
} from '@/containers/CryptoContainer/styled'
import React, { useState } from 'react'
import { useRecoilValue } from 'recoil'
import priceListState from '@/recoils/crypto/atoms'
import { PriceType } from '@/interfaces/PriceWindowType'
import { usePathname } from 'next/navigation'

function SpotTrade() {
  const pathname = usePathname()
  const coinCode = pathname?.split('/')[3]

  const [priceList, setPriceList] = useState<PriceType[]>(
    useRecoilValue(priceListState),
  )

  return (
    <CryptoPageContainer>
      <div>
        <Chart coin={priceList.find((price) => price.code === coinCode)!} />
        <BottomContainer>
          <OrderBook />
          <Trade />
        </BottomContainer>
      </div>
      <PriceWindow priceList={priceList} setPriceList={setPriceList} />
    </CryptoPageContainer>
  )
}

export default SpotTrade
