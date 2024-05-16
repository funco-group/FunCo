import { useRouter } from 'next/navigation'
import medalMap from '@/lib/medalMap'
import { RankType } from '@/interfaces/rank/RankType'
import getColorByReturnRate from '@/utils/getColorByReturnRate'
import {
  MoneySpan,
  RateSpan,
  TopRankContentContainer,
  TopRankContentStatDiv,
  TopRankProfileDiv,
} from './TopRankContent.styled'

interface TopRankContentProps {
  topRank: RankType
  nowTapName: string
}

function TopRankContent({ topRank, nowTapName }: TopRankContentProps) {
  const router = useRouter()

  const handleProfileClick = () => {
    router.push(`/member/${topRank.member.id}`)
  }
  return (
    <TopRankContentContainer>
      <TopRankProfileDiv
        $isTop={topRank.rank === 1}
        onClick={handleProfileClick}
      >
        <img
          src={topRank.member.profileUrl}
          alt="top-rank-profile"
          draggable={false}
        />
        <div>
          <span>{medalMap.get(topRank.rank)}</span>
        </div>
        <div>{topRank.member.nickname}</div>
      </TopRankProfileDiv>
      <TopRankContentStatDiv>
        <div>수익률</div>
        <div>
          <RateSpan $isProfit={getColorByReturnRate(topRank.returnRate)}>
            {topRank.returnRate}
          </RateSpan>{' '}
          %
        </div>
      </TopRankContentStatDiv>
      <TopRankContentStatDiv>
        <div>따라오는 금액</div>
        <div>
          <MoneySpan $active={nowTapName === 'follow'}>
            {topRank.followingAsset.toLocaleString('en-US')}
          </MoneySpan>{' '}
          WON
        </div>
      </TopRankContentStatDiv>
      <TopRankContentStatDiv>
        <div>총 자산</div>
        <div>
          <MoneySpan $active={nowTapName === 'asset'}>
            {topRank.totalAsset.toLocaleString('en-US')}
          </MoneySpan>{' '}
          WON
        </div>
      </TopRankContentStatDiv>
    </TopRankContentContainer>
  )
}

export default TopRankContent
