'use client'

import { useEffect, useState } from 'react'
import AssetList from '@/components/TradeHistory/Asset/AssetList'
import TotalAsset from '@/components/TradeHistory/Asset/TotalAsset'
import { getAsset } from '@/apis/asset'
import {
  AssetResponseType,
  AssetType,
  TotalAssetType,
} from '@/interfaces/AssetType'
import { AxiosResponse } from 'axios'
import { getTickerPrice } from '@/apis/upbit'
import { ResTickerType } from '@/interfaces/tradeHistory/follow/ResTickerType'
import MonochromePieChart from '@/components/Common/Chart/MonochromePieChart'
import { TitleDiv } from '@/styles/TradeHistoryStyled'
import { ChartContainer, TotalAssetInfoContainer } from './styled'

function Asset() {
  const [assets, setAssets] = useState<AssetType[]>([])
  const [totalAsset, setTotalAsset] = useState<TotalAssetType>()
  const [investmentList, setInvestmentList] = useState<(string | number)[][]>()

  const getCurPrice = async (assetRes: AssetResponseType) => {
    const curPrice = new Map<string, number>()
    if (assetRes.holdingCoinInfos.length !== 0) {
      const codes: string[] = []

      assetRes.holdingCoinInfos.forEach((info) => {
        codes.push(info.ticker)
      })

      assetRes.activeFutureInfos.forEach((info) => {
        codes.push(info.ticker)
      })

      const uniqueCodes = Array.from(new Set(codes))

      await getTickerPrice(
        uniqueCodes.join(', '),
        (response: AxiosResponse<ResTickerType[]>) => {
          const { data } = response
          data.forEach((coin) => {
            curPrice.set(coin.market, coin.trade_price)
          })
        },
      )
    }
    return curPrice
  }

  const setAssetsInfo = (
    assetsRes: AssetResponseType,
    curPrice: Map<string, number>,
  ) => {
    setAssets([
      {
        imgSrc: '/icon/cash-icon.png',
        type: 'cash',
        name: '현금',
        volume: null,
        averagePrice: null,
        price: null,
        evaluationAmount: assetsRes.cash,
        evaluationProfit: null,
      },
      {
        imgSrc: '/icon/follow-icon.png',
        type: 'follow',
        name: '팔로우',
        volume: null,
        averagePrice: null,
        price: assetsRes.followingInvestment,
        evaluationAmount: assetsRes.followingInvestment,
        evaluationProfit: null,
      },
    ])
    assetsRes.holdingCoinInfos.forEach((coin) => {
      const price = Math.floor(coin.volume * coin.averagePrice)
      const evaluationAmount = Math.floor(
        coin.volume * curPrice.get(coin.ticker)!,
      )
      setAssets((asset) => [
        ...asset,
        {
          imgSrc: `https://static.upbit.com/logos/${coin.ticker.split('-')[1]}.png`,
          type: 'coin',
          name: coin.ticker,
          volume: coin.volume,
          averagePrice: coin.averagePrice,
          price,
          evaluationAmount,
          evaluationProfit:
            Math.floor(((evaluationAmount - price) / price) * 100 * 100) / 100,
        },
      ])
    })

    assetsRes.activeFutureInfos.forEach((coin) => {
      let rate =
        ((curPrice.get(coin.ticker)! - coin.price) / coin.price) * coin.leverage
      if (coin.tradeType === 'SHORT') {
        rate = -rate
      }
      const evaluationAmount = coin.orderCash * rate + coin.orderCash

      setAssets((asset) => [
        ...asset,
        {
          imgSrc: `https://static.upbit.com/logos/${coin.ticker.split('-')[1]}.png`,
          type: coin.tradeType,
          name: coin.ticker,
          volume: null,
          averagePrice: coin.price,
          price: coin.orderCash,
          evaluationAmount: Math.ceil(evaluationAmount),
          evaluationProfit: Math.ceil(rate * 10000) / 100,
        },
      ])
    })

    setInvestmentList([
      ['현금', assetsRes.cash],
      ['팔로우', assetsRes.followingInvestment],
      [
        '가상화폐',
        assetsRes.holdingCoinInfos.reduce(
          (acc, coin) =>
            acc + Math.floor(coin.volume * curPrice.get(coin.ticker)!),
          0,
        ) +
          assetsRes.activeFutureInfos.reduce((acc, coin) => {
            let rate =
              ((curPrice.get(coin.ticker)! - coin.price) / coin.price) *
              coin.leverage
            if (coin.tradeType === 'SHORT') {
              rate = -rate
            }
            const evaluationAmount = coin.orderCash * rate + coin.orderCash

            return acc + evaluationAmount
          }, 0),
      ],
    ])
  }

  useEffect(() => {
    getAsset((response: AxiosResponse<AssetResponseType>) => {
      const { data } = response
      const curPrice = getCurPrice(data)
      curPrice.then((res) => {
        setAssetsInfo(data, res)
      })
    })
  }, [])

  useEffect(() => {
    if (assets.length !== 0) {
      // 보유
      const cash =
        assets.filter((asset) => asset.name === '현금')[0].evaluationAmount ?? 0
      // 총 매수금액
      const price = assets
        .filter((asset) => asset.name !== '현금')
        // .reduce((acc, item) => acc + item.price!, 0)
        .reduce((acc, item) => {
          if (item.price) {
            if (typeof item.price === 'number') {
              return acc + item.price
            }
            return acc + +item.price.split(' (')[0]
          }
          return acc
        }, 0)
      // 총 평가금액
      const evaluationAmount = assets
        .filter((asset) => asset.name !== '현금')
        .reduce((acc, item) => {
          if (item.evaluationAmount) {
            return acc + item.evaluationAmount
          }
          return acc
        }, 0)
      // 총 보유자산
      const asset = cash + price
      // 총 평가손익
      const returnResult = evaluationAmount - price
      // 총 평가수익률
      const evaluationProfit =
        price > 0
          ? Math.floor(((evaluationAmount - price) / price) * 100 * 100) / 100
          : 0
      setTotalAsset({
        cash,
        price,
        evaluationAmount,
        asset,
        returnResult,
        evaluationProfit,
      })
    }
  }, [assets])

  if (!investmentList || !assets || !totalAsset) return null

  return (
    <div>
      <TotalAssetInfoContainer>
        <TotalAsset totalAsset={totalAsset} setAssets={setAssets} />
        <ChartContainer>
          <MonochromePieChart investmentList={investmentList} isLegend />
        </ChartContainer>
      </TotalAssetInfoContainer>
      <TitleDiv>보유자산 목록</TitleDiv>
      <AssetList assets={assets} />
    </div>
  )
}

export default Asset
