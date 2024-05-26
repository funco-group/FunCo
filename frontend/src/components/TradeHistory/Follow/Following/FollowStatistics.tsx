import { GreenDataDiv, GreenTitleDiv } from '@/styles/TradeHistoryStyled'
import MonochromePieChart from '@/components/Common/Chart/MonochromePieChart'
import {
  FollowingStatisticsContainer,
  FollowingStatisticsDetailDiv,
  FollowingStatisticsDetailInnerDiv,
  StatisticsGraphDiv,
  StatisticsRowDiv,
} from './FollowStatistics.styled'

interface FollowStatisticsProps {
  totalInvestment: number
  totalEstimatedValue: number
  totalAsset: number
  investmentList: (string | number)[][]
}

function getColor(idx: number, value: string | number) {
  if (idx < 2) {
    return 'black'
  }
  return value.toString().startsWith('-') ? 'blue' : 'red'
}

function FollowStatistics({
  totalInvestment,
  totalEstimatedValue,
  totalAsset,
  investmentList,
}: FollowStatisticsProps) {
  const totalEstimatedProfitRate = (
    ((totalEstimatedValue - totalInvestment) / totalInvestment) *
    100
  ).toFixed(2)
  const totalInvestmentAssetRatio = Math.round(
    (totalInvestment / (totalAsset + totalInvestment)) * 100,
  )

  const statisticsList = [
    ['총 투자 금액', totalInvestment.toLocaleString('ko-KR')],
    ['총 예상 수익금', totalEstimatedValue.toLocaleString('ko-KR')],
    ['총 예상 수익률', totalEstimatedProfitRate],
  ]
  return (
    <FollowingStatisticsContainer>
      <FollowingStatisticsDetailDiv>
        <FollowingStatisticsDetailInnerDiv>
          {statisticsList.map((statistic, idx) => (
            <StatisticsRowDiv key={statistic[0]}>
              <GreenTitleDiv>{statistic[0]}</GreenTitleDiv>
              <GreenDataDiv color={getColor(idx, statistic[1])}>
                {statistic[1]} <span>{idx === 2 ? ' %' : ' WON'}</span>
              </GreenDataDiv>
            </StatisticsRowDiv>
          ))}
        </FollowingStatisticsDetailInnerDiv>
        <GreenTitleDiv>
          당신의 총 자산{' '}
          {(totalAsset + totalInvestment).toLocaleString('ko-KR')} 중{' '}
          {totalInvestmentAssetRatio}%가 투자 중입니다.
        </GreenTitleDiv>
      </FollowingStatisticsDetailDiv>
      <StatisticsGraphDiv>
        <MonochromePieChart
          key={investmentList.length}
          investmentList={investmentList}
          isLegend
        />
      </StatisticsGraphDiv>
    </FollowingStatisticsContainer>
  )
}

export default FollowStatistics
