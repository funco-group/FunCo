'use client'

import { useEffect, useState } from 'react'
import { AxiosResponse } from 'axios'
import { useRecoilValue } from 'recoil'
import { getTickerPrice } from '@/apis/upbit'
import { ResTickerType } from '@/interfaces/tradeHistory/follow/ResTickerType'
import { FollowingType } from '@/interfaces/tradeHistory/follow/FollowingTyps'
import { ComputedFollowingType } from '@/interfaces/tradeHistory/follow/ComputedFollowingType'
import { ResFollowingType } from '@/interfaces/tradeHistory/follow/ResFollowingType'
import { getFollowingList } from '@/apis/follow'
import useSettleModalState from '@/hooks/recoilHooks/useSettleModalState'
import { codeListState } from '@/recoils/crypto'
import FollowStatistics from '@/components/TradeHistory/Follow/Following/FollowStatistics'
import FollowingUser from '@/components/TradeHistory/Follow/Following/FollowingUser'
import SettleModal from '@/components/TradeHistory/Follow/Following/SettleModal'
import { getTotalAsset } from '@/apis/member'
import FollowingUserListContainer from './styled'

function Following() {
  const tickerList = useRecoilValue(codeListState).join(',')
  const [tickerPriceMap, setTickerPriceMap] = useState<Map<string, number>>(
    new Map(null),
  )
  const [isLoading, setIsLoading] = useState(false)
  const [totalAsset, setTotalAsset] = useState<number>()
  const [followings, setFollowings] = useState<FollowingType[]>()
  const [totalInvestment, setTotalInvesment] = useState<number>()
  const [totalEstimatedValue, setTotalEstimatedValue] = useState<number>()
  const [computedFollowings, setComputedFollowings] = useState<
    ComputedFollowingType[]
  >([])
  const [investmentList, setInvestmentList] = useState<(string | number)[][]>(
    [],
  )
  const [isNoFollowings, setIsNoFollowings] = useState(false)

  const { settleModal } = useSettleModalState()

  useEffect(() => {
    // 티커 가격 요청 및 맵 설정
    const fetchTickerPrice = () => {
      getTickerPrice(tickerList, (res: AxiosResponse<ResTickerType[]>) => {
        const mapList: [string, number][] = res.data.map((coin) => [
          coin.market,
          coin.trade_price,
        ])
        setTickerPriceMap(new Map(mapList))
      })
    }

    setIsLoading(true)
    fetchTickerPrice()
    getTotalAsset((res) => {
      const { data } = res
      setTotalAsset(data.asset)
    })
    getFollowingList((res: AxiosResponse<ResFollowingType>) => {
      const { data } = res
      setFollowings(data.followings)
    })
  }, [tickerList])

  useEffect(() => {
    // 팔로잉된 사용자 정보를 계산하는 함수
    const computeFollowingInfo = (following: FollowingType) => {
      const estimatedValue =
        following.cash +
        following.coins.reduce((total, coin) => {
          const price = tickerPriceMap.get(coin.ticker) || 0
          return total + coin.volume * price
        }, 0)

      const coinsInfo = following.coins.map((coin) => {
        const tickerPrice = tickerPriceMap.get(coin.ticker) || 0
        return {
          ticker: coin.ticker,
          price: tickerPrice * coin.volume,
        }
      })

      return {
        followId: following.followId,
        nickname: following.member.nickname,
        date: following.followedAt,
        investment: following.investment,
        estimatedValue: Math.round(estimatedValue),
        cash: following.cash,
        coins: coinsInfo,
      }
    }

    if (!followings?.length) {
      setIsLoading(false)
      setIsNoFollowings(true)
      return // 더 이상 실행할 필요가 없으므로 이후 코드 실행을 막습니다.
    }

    if (!tickerPriceMap.size || !totalAsset) {
      return // 가격 정보나 자산 정보가 없으면 계산을 할 수 없으므로 종료합니다.
    }

    let newTotalInvestment = 0
    let newTotalEstimatedValue = 0
    const newComputedFollowings: ComputedFollowingType[] = []
    const newInvestmentList: (string | number)[][] = []

    followings.forEach((following) => {
      const computedFollowing = computeFollowingInfo(following)
      newComputedFollowings.push(computedFollowing)
      newInvestmentList.push([following.member.nickname, following.investment])
      newTotalInvestment += following.investment
      newTotalEstimatedValue += computedFollowing.estimatedValue
    })

    setTotalInvesment(newTotalInvestment)
    setTotalEstimatedValue(newTotalEstimatedValue)
    setComputedFollowings(newComputedFollowings)
    setInvestmentList(newInvestmentList)
  }, [totalAsset, followings, tickerPriceMap])

  useEffect(() => {
    if (
      totalInvestment &&
      totalEstimatedValue &&
      investmentList &&
      computedFollowings
    ) {
      setIsLoading(false)
      setIsNoFollowings(false)
    }
  }, [totalInvestment, totalEstimatedValue, investmentList, computedFollowings])

  if (isLoading) {
    return <>Loading</>
  }
  if (isNoFollowings) {
    return <>팔로잉하고 있는 유저가 없습니다.</>
  }
  return (
    <div>
      {settleModal && followings !== undefined && (
        <SettleModal followings={followings} setFollowings={setFollowings} />
      )}
      {totalInvestment && totalEstimatedValue && totalAsset && (
        <FollowStatistics
          totalAsset={totalAsset}
          totalInvestment={totalInvestment}
          totalEstimatedValue={totalEstimatedValue}
          investmentList={investmentList}
        />
      )}

      <FollowingUserListContainer>
        {computedFollowings.map((following) => (
          <FollowingUser key={following.followId} followingUser={following} />
        ))}
      </FollowingUserListContainer>
    </div>
  )
}

export default Following
