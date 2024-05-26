import { useRouter } from 'next/navigation'
import medalMap from '@/lib/medalMap'
import { RankType } from '@/interfaces/rank/RankType'
import { FollowingContentMarginDiv } from '@/components/TradeHistory/Follow/Following/FollowingUser.styled'
import { RankTableColumnGridDiv } from '@/components/Rank/RankTable.styled'
import { MoneySpan, RateSpan } from '@/components/Rank/TopRankContent.styled'
import {
  RankSpan,
  RankTableContentContainer,
  RankTableContentMarginDiv,
  RankTableContentUserDiv,
} from '@/components/Rank/RankTableContent.styled'
import getColorByReturnRate from '@/utils/getColorByReturnRate'

interface RankTableContentProps {
  rank: RankType
  nowTabName: string
}

function RankTableContent({ rank, nowTabName }: RankTableContentProps) {
  const router = useRouter()

  const handleProfileClick = () => {
    router.push(`/member/${rank.member.id}`)
  }

  return (
    <RankTableContentContainer>
      <RankTableColumnGridDiv>
        <FollowingContentMarginDiv color="">
          <RankSpan $isTopRank={medalMap.get(rank.rank)}>
            {medalMap.get(rank.rank) || rank.rank}
          </RankSpan>
        </FollowingContentMarginDiv>
        <div>
          <RankTableContentUserDiv onClick={handleProfileClick}>
            <img
              src={rank.member.profileUrl}
              alt="rank-user-profile"
              draggable={false}
            />
            <div>{rank.member.nickname}</div>
          </RankTableContentUserDiv>
        </div>
        <RankTableContentMarginDiv>
          <RateSpan $isProfit={getColorByReturnRate(rank.returnRate)}>
            {rank.returnRate} %
          </RateSpan>
        </RankTableContentMarginDiv>
        <RankTableContentMarginDiv>
          <MoneySpan $active={nowTabName === 'follow'}>
            {rank.followingAsset.toLocaleString('en-US')} WON
          </MoneySpan>
        </RankTableContentMarginDiv>
        <RankTableContentMarginDiv>
          <MoneySpan $active={nowTabName === 'asset'}>
            {rank.totalAsset.toLocaleString('en-US')} WON
          </MoneySpan>
        </RankTableContentMarginDiv>
      </RankTableColumnGridDiv>
    </RankTableContentContainer>
  )
}

export default RankTableContent
