import { useEffect, useState } from 'react'
import {
  AssetHistoryType,
  AssetResponseType,
  AssetType,
  TotalAssetType,
} from '@/interfaces/AssetType'
import { getAsset } from '@/apis/asset'
import { AxiosResponse } from 'axios'
import {
  AssetChangeListContainer,
  HistoryListContainer,
} from '@/components/TradeHistory/AssetChange/AssetChangeList.styled'
import {
  ColumnContainer,
  ColumnGrid,
  ColumnTitleDiv,
} from '@/styles/CommonStyled'
import { AssetChangeListItemContainer } from '@/components/TradeHistory/AssetChange/AssetChangeListItem.styled'
import AssetChangeListItem from '@/components/TradeHistory/AssetChange/AssetChangeListItem'
import {
  ChartContainer,
  TotalAssetInfoContainer,
} from '@/containers/TradeHistoryContainer/AssetContainer/styled'
import TotalAsset from '@/components/TradeHistory/Asset/TotalAsset'
import AssetList from '@/components/TradeHistory/Asset/AssetList'
import { getTickerPrice } from '@/apis/upbit'
import { ResTickerType } from '@/interfaces/tradeHistory/follow/ResTickerType'
import MonochromePieChart from '@/components/Common/Chart/MonochromePieChart'
import cashIcon from '@/assets/icon/cash-icon.png'
import followIcon from '@/assets/icon/follow-icon.png'
import FollowingModal from './FollowingModal'

interface FollowAssetModalProps {
  handlePortFolioClick: () => void
}

function FollowAssetModal({ handlePortFolioClick }: FollowAssetModalProps) {
  // 보유자산
  const [assets, setAssets] = useState<AssetType[]>([])
  const [totalAsset, setTotalAsset] = useState<TotalAssetType>()

  const [investmentList, setInvestmentList] = useState<(string | number)[][]>()

  const getCurPrice = async (asset: AssetResponseType) => {
    const curPrice = new Map<string, number>()
    if (asset.holdingCoinInfos.length !== 0) {
      const codes = asset.holdingCoinInfos.map((coin) => coin.ticker).join(',')
      await getTickerPrice(
        codes,
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
        imgSrc: cashIcon.src,
        type: 'cash',
        name: '현금',
        volume: null,
        averagePrice: null,
        price: null,
        evaluationAmount: assetsRes.cash,
        evaluationProfit: null,
      },
      {
        imgSrc: followIcon.src,
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
    setInvestmentList([
      ['현금', assetsRes.cash],
      ['팔로우', assetsRes.followingInvestment],
      [
        '가상화폐',
        assetsRes.holdingCoinInfos.reduce(
          (acc, coin) =>
            acc + Math.floor(coin.volume * curPrice.get(coin.ticker)!),
          0,
        ),
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
        Math.floor(((evaluationAmount - price) / price) * 100 * 100) / 100

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

  // 자산 변동 내역
  const [historyList, setHistoryList] = useState<AssetHistoryType[]>([])
  const columns = [
    '체결시간',
    '보유자산',
    '종류',
    '거래수량',
    '거래단가',
    '거래금액',
    '수수료',
    '정산금액',
  ]

  useEffect(() => {
    // getHistory((response: AxiosResponse<AssetHistoryType[]>) => {
    //   const { data } = response
    //   setHistoryList(data)
    // })
  }, [])

  return (
    <FollowingModal title="~님의 포트폴리오" handleClick={handlePortFolioClick}>
      <div>
        <TotalAssetInfoContainer>
          <TotalAsset totalAsset={totalAsset} />
          <ChartContainer>
            {investmentList && (
              <MonochromePieChart investmentList={investmentList} isLegend />
            )}
          </ChartContainer>
        </TotalAssetInfoContainer>
        <AssetList assets={assets} />
      </div>
      <div>
        <AssetChangeListContainer>
          <ColumnContainer>
            <AssetChangeListItemContainer>
              <ColumnGrid $column="6rem 5rem 5rem 1.3fr 1fr 1fr 1fr 1fr">
                {columns.map((column) => (
                  <ColumnTitleDiv key={column}>{column}</ColumnTitleDiv>
                ))}
              </ColumnGrid>
            </AssetChangeListItemContainer>
          </ColumnContainer>
          <HistoryListContainer>
            {historyList.map((history) => (
              <AssetChangeListItem key={history.date} history={history} />
            ))}
          </HistoryListContainer>
        </AssetChangeListContainer>
      </div>
    </FollowingModal>
  )
}

export default FollowAssetModal
