import palette from '@/lib/palette'
import { TopCoinsType } from '@/interfaces/userPage/MemberType'
import { codeNameMapState } from '@/recoils/crypto'
import { useRecoilValue } from 'recoil'
import parseDate from '@/utils/parseDate'
import { ComponentTitleH3 } from '@/containers/UserPageContainer/styled'
import { NoDataDiv } from './ReturnRateGraph.styled'
import {
  CoinNameDiv,
  DateDiv,
  NumberDiv,
  RecentInvestmentContainer,
  RecentInvestmentContentContainer,
  RecentInvestmentContentDiv,
  RecentInvestmentRowDiv,
} from './RecentInvestment.styled'

interface RecentInvestmentProps {
  topCoins: TopCoinsType[]
}

function RecentInvestment({ topCoins }: RecentInvestmentProps) {
  const nameMap = useRecoilValue(codeNameMapState)

  return (
    <RecentInvestmentContainer>
      <ComponentTitleH3>최근 투자</ComponentTitleH3>
      <RecentInvestmentContentContainer>
        {topCoins?.length !== 0 ? (
          <RecentInvestmentContentDiv>
            {topCoins.map((coin, idx) => (
              <RecentInvestmentRowDiv key={coin.ticker}>
                <div>
                  <NumberDiv color={palette.brandColor}>{idx + 1}.</NumberDiv>
                  <CoinNameDiv>
                    <img
                      src={`https://static.upbit.com/logos/${coin.ticker.split('-')[1]}.png`}
                      alt=""
                      width={15}
                    />
                    {nameMap.get(coin.ticker)}
                  </CoinNameDiv>
                </div>
                <DateDiv>{parseDate(coin.createdAt).split(' ')[0]}</DateDiv>
              </RecentInvestmentRowDiv>
            ))}
          </RecentInvestmentContentDiv>
        ) : (
          <NoDataDiv>최근 투자 내역이 없습니다.</NoDataDiv>
        )}
      </RecentInvestmentContentContainer>
    </RecentInvestmentContainer>
  )
}

export default RecentInvestment
