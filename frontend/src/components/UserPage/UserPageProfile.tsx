import { useEffect, useState } from 'react'
import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'

import { MemberType } from '@/interfaces/userPage/MemberType'
import useFollowModalState from '@/hooks/recoilHooks/useFollowModalState'
import medalMap from '@/lib/medalMap'
import { ComponentTitleH3 } from '@/containers/UserPageContainer/styled'
import PortfolioModal from './PortfolioModal'
import {
  IntroductionDiv,
  NicknameDiv,
  ProfileButtonDiv,
  ProfileDetailContainer,
  ProfileRankDiv,
  ProfileRankFlexDiv,
  ProfileRankOuterDiv,
  UserPageProfileContainer,
} from './UserPageProfile.styled'

interface UserPageProfileProps {
  member: MemberType
}

function UserPageProfile({ member }: UserPageProfileProps) {
  const [nickname, setNickname] = useState(member.nickname)
  const [introduction, setIntroduction] = useState(member.introduction)
  const { onFollowModal } = useFollowModalState()
  const [onFollowAssetModal, setOnFollowAssetModal] = useState(false)

  useEffect(() => {
    setNickname(member.nickname)
    if (!member.introduction) {
      setIntroduction('한 줄 소개를 입력해주세요!')
    } else {
      setIntroduction(member.introduction)
    }
  }, [member])

  const handleFollowClick = () => {
    onFollowModal({
      memberId: member.memberId,
    })
  }

  const handlePortFolioClick = () => {
    setOnFollowAssetModal((prev) => !prev)
  }

  function renderButton() {
    if (!member.isFollow) {
      return (
        <BrandButtonComponent
          content="팔로우"
          color={null}
          cancel={false}
          onClick={handleFollowClick}
          disabled={false}
        />
      )
    }
    return (
      <BrandButtonComponent
        content="포트폴리오 보기"
        color={null}
        cancel={false}
        onClick={handlePortFolioClick}
        disabled={false}
      />
    )
  }
  return (
    <UserPageProfileContainer>
      {onFollowAssetModal && (
        <PortfolioModal
          memberId={member.memberId}
          nickname={member.nickname}
          handlePortFolioClick={handlePortFolioClick}
        />
      )}
      <ComponentTitleH3>프로필</ComponentTitleH3>
      <ProfileDetailContainer>
        <img src={member.profileUrl} alt="member-profile" />
        <NicknameDiv>{nickname}</NicknameDiv>
        <ProfileRankFlexDiv>
          <ProfileRankOuterDiv>
            <div>총 자산 랭킹</div>
            <ProfileRankDiv>
              {member.assetRank ? (
                <>
                  <span>{medalMap.get(member.assetRank) || '🏃‍♂️'}</span>
                  {member.assetRank}
                </>
              ) : (
                '-'
              )}
              위
            </ProfileRankDiv>
          </ProfileRankOuterDiv>
          <ProfileRankOuterDiv>
            <div>총 팔로워 랭킹</div>
            <ProfileRankDiv>
              {member.followingCashRank ? (
                <>
                  <span>{medalMap.get(member.followingCashRank) || '🏃‍♂️'}</span>
                  {member.followingCashRank}
                </>
              ) : (
                '-'
              )}
              위
            </ProfileRankDiv>
          </ProfileRankOuterDiv>
        </ProfileRankFlexDiv>
        <IntroductionDiv>{introduction}</IntroductionDiv>
      </ProfileDetailContainer>
      <ProfileButtonDiv>{renderButton()}</ProfileButtonDiv>
    </UserPageProfileContainer>
  )
}

export default UserPageProfile
