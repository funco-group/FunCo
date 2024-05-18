import palette from '@/lib/palette'
import { ComponentTitleH3 } from '@/containers/UserPageContainer/styled'
import { RecentInvestmentContainer } from './RecentInvestment.styled'
import {
  ColorSpan,
  UserFollowContentDiv,
  UserFollowRowDiv,
} from './UserFollow.styled'
import { MoneySpan } from '../TradeHistory/OpenOrder/OpenOrderContent.styled'

interface UserFollowProps {
  followingCash: number
  followerCash: number
}

function UserFollow({ followingCash, followerCash }: UserFollowProps) {
  return (
    <RecentInvestmentContainer>
      <ComponentTitleH3>팔로우</ComponentTitleH3>
      <UserFollowContentDiv>
        <UserFollowRowDiv>
          <div>총 팔로잉 금액</div>
          <div>
            <MoneySpan>
              {followingCash ? followingCash.toLocaleString('en-US') : '-'}
            </MoneySpan>
            <ColorSpan color={palette.brandDarkGray}> WON</ColorSpan>
          </div>
        </UserFollowRowDiv>
        <UserFollowRowDiv>
          <div>총 팔로워 금액</div>
          <div>
            <MoneySpan>
              {followerCash ? followerCash.toLocaleString('en-US') : '-'}
            </MoneySpan>
            <ColorSpan color={palette.brandDarkGray}> WON</ColorSpan>
          </div>
        </UserFollowRowDiv>
      </UserFollowContentDiv>
    </RecentInvestmentContainer>
  )
}

export default UserFollow
