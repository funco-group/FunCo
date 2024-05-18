'use client'

import { useEffect, useState } from 'react'
import useFollowModalState from '@/hooks/recoilHooks/useFollowModalState'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { MemberType } from '@/interfaces/userPage/MemberType'
import { getMemberInfo, getMyInfo } from '@/apis/member'
import FollowModal from '@/components/UserPage/FollowModal'
import UserPageProfile from '@/components/UserPage/UserPageProfile'
// import AssetGraph from '@/components/UserPage/AssetGraph'
// import RecentInvestment from '@/components/UserPage/RecentInvestment'
// import UserFollow from '@/components/UserPage/UserFollow'
// import ReturnRateGraph from '@/components/UserPage/ReturnRateGraph'
import MyPageProfile from '@/components/UserPage/MyPageProfile'
import { UserLayoutRowDiv } from './styled'

function UserPageContainer({ memberId }: { memberId: number }) {
  const { user } = useUserState()
  const [member, setMember] = useState<MemberType>()
  const { followModal } = useFollowModalState()

  const isCurrentUser = user?.memberId === +memberId

  useEffect(() => {
    if (isCurrentUser) {
      getMyInfo((res) => {
        const { data } = res
        setMember({
          ...data,
          memberId: user.memberId,
          nickname: user.nickname,
          profileUrl: user.profileUrl,
        })
      })
    } else {
      getMemberInfo(memberId, (res) => {
        const { data } = res
        setMember(data)
      })
    }
  }, [])

  if (!memberId || !user || !member) {
    return null
  }

  return (
    <div>
      {followModal && member && (
        <FollowModal member={member} setMember={setMember} />
      )}
      <UserLayoutRowDiv>
        {isCurrentUser ? (
          <MyPageProfile member={member} />
        ) : (
          <UserPageProfile member={member} />
        )}

        {/* <AssetGraph member={member} /> */}
      </UserLayoutRowDiv>
      {/* <UserLayoutRowDiv>
        <RecentInvestment topCoins={member.topCoins} />
        <UserFollow
          followingCash={member.followingCash}
          followerCash={member.followerCash}
        />
      </UserLayoutRowDiv>
      <ReturnRateGraph memberId={member.memberId} /> */}
    </div>
  )
}

export default UserPageContainer
