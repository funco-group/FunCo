'use client'

import { getActiveFutures } from '@/apis/trade'
import PriceWindow from '@/components/Common/PriceWindow/PriceWindow'
import Chart from '@/components/Trade/Chart'
import Position from '@/components/Trade/FuturesTrade/Position'
import Trade from '@/components/Trade/FuturesTrade/Trade'
import {
  BottomContainer,
  CryptoPageContainer,
} from '@/containers/CryptoContainer/styled'
import { PriceType } from '@/interfaces/PriceWindowType'
import { FuturesType } from '@/interfaces/TradeType'
import { usePathname } from 'next/navigation'
import React, { useEffect, useState } from 'react'
import { useRecoilValue } from 'recoil'
import priceListState from '@/recoils/crypto/atoms'

function FuturesTrade() {
  const pathname = usePathname()

  const [trade, setTrade] = useState<FuturesType | null>(null)
  const [isTrade, setIsTrade] = useState<boolean>(false)
  const [liquidate, setLiquidate] = useState<boolean>(false)
  const coinCode = pathname?.split('/')[3]

  const [priceList, setPriceList] = useState<PriceType[]>(
    useRecoilValue(priceListState),
  )

  useEffect(() => {
    if (!liquidate) {
      getActiveFutures(coinCode, (response) => {
        if (response.status === 200) {
          const { data } = response
          setTrade(data)
          setIsTrade(true)
        }
      })
    }
  }, [isTrade, liquidate])

  return (
    <CryptoPageContainer>
      <div>
        <Chart coin={priceList.find((price) => price.code === coinCode)!} />
        <BottomContainer>
          <Position
            isTrade={isTrade}
            setIsTrade={setIsTrade}
            coin={
              isTrade
                ? priceList.find((price) => price.code === coinCode)!
                : null
            }
            trade={trade}
            liquidate={liquidate}
            setLiquidate={setLiquidate}
          />
          <Trade
            isTrade={isTrade}
            setIsTrade={setIsTrade}
            futureId={isTrade && trade ? trade.id : 0}
          />
        </BottomContainer>
      </div>
      <PriceWindow priceList={priceList} setPriceList={setPriceList} />
    </CryptoPageContainer>
  )
}

export default FuturesTrade
