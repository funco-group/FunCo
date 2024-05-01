'use client'

import { getCoinList } from '@/apis/upbit'
import LoginAlertModal from '@/components/Common/Modal/LoginAlertModal'
import useLoginAlertModalState from '@/hooks/recoilHooks/useLoginAlertModalState'
import { PriceType, ResMarketCodeType } from '@/interfaces/PriceWindowType'
import { priceListState } from '@/recoils/crypto'
import { AxiosResponse } from 'axios'
import { useEffect } from 'react'
import { useSetRecoilState } from 'recoil'

interface RecoilSetterProps {
  children: React.ReactNode
}

const toPriceType = (coinData: ResMarketCodeType[]): PriceType[] =>
  coinData
    .filter((coin) => coin.market.startsWith('KRW'))
    .map((coin: ResMarketCodeType) => ({
      code: coin.market,
      koreanName: coin.korean_name,
      tradePrice: 0,
      change: 'EVEN',
      signedChangeRate: 0,
      signedChangePrice: 0,
      accTradeVolme24h: 0,
      accTradePrice24h: 0,
      highPrice: 0,
      lowPrice: 0,
      updated: false,
    }))

function RecoilSetter({ children }: RecoilSetterProps) {
  const setPriceList = useSetRecoilState(priceListState)
  const { loginAlertModal } = useLoginAlertModalState()

  useEffect(() => {
    getCoinList((response: AxiosResponse<ResMarketCodeType[]>) => {
      const { data } = response
      setPriceList(toPriceType(data))
    })
  }, [])

  return (
    <div>
      {children}
      {loginAlertModal && <LoginAlertModal />}
    </div>
  )
}

export default RecoilSetter
