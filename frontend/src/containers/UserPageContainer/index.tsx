'use client'

import { useEffect, useState } from 'react'
import useFollowModalState from '@/hooks/recoilHooks/useFollowModalState'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { MemberType } from '@/interfaces/userPage/MemberType'
import { getMemberInfo } from '@/apis/member'
import FollowModal from '@/components/UserPage/FollowModal'
import UserPageProfile from '@/components/UserPage/UserPageProfile'
import AssetGraph from '@/components/UserPage/AssetGraph'
import RecentInvestment from '@/components/UserPage/RecentInvestment'
import UserFollow from '@/components/UserPage/UserFollow'
import ReturnRateGraph from '@/components/UserPage/ReturnRateGraph'
import { UserLayoutRowDiv } from './styled'
import MyPageProfile from '@/components/UserPage/MyPageProfile'

function UserPageContainer({ memberId }: { memberId: number }) {
  const { user } = useUserState()
  const [member, setMember] = useState<MemberType>()
  const [my, setMy] = useState<MyType>()
  const { followModal } = useFollowModalState()

  useEffect(() => {
    if (memberId) {
      getMemberInfo(memberId, (res) => {
        const { data } = res
        setMember(data)
      })
    }
  }, [memberId])

  if (!memberId || !user || !member) {
    return <div>존재하지 않는 회원입니다.</div>
  }

  const isCurrentUser = user?.memberId === +memberId
  return (
    <div>
      {followModal && member && (
        <FollowModal member={member} setMember={setMember} />
      )}
      <UserLayoutRowDiv>
        {isCurrentUser ? (
          <UserPageProfile member={member} />
        ) : (
          <MyPageProfile member={member} />
        )}

        <AssetGraph member={member} />
      </UserLayoutRowDiv>
      <UserLayoutRowDiv>
        <RecentInvestment topCoins={member.topCoins} />
        <UserFollow
          followingCash={member.followingCash}
          followerCash={member.followerCash}
        />
      </UserLayoutRowDiv>
      <ReturnRateGraph memberId={member.memberId} />
    </div>
  )
}

export default UserPageContainer
