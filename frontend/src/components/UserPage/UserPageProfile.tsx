import { useState } from 'react'
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
  ProfileEditButtonDiv,
  ProfileImg,
  ProfileRankDiv,
  ProfileRankFlexDiv,
  ProfileRankOuterDiv,
  UserPageProfileContainer,
} from './UserPageProfile.styled'
import BuyPortfolioModal from './BuyPortfolioModal'

interface UserPageProfileProps {
  member: MemberType
}
function UserPageProfile({ member }: UserPageProfileProps) {
  const [introduction] = useState(
    member.introduction ? member.introduction : '-',
  )
  const { onFollowModal } = useFollowModalState()
  const [onFollowAssetModal, setOnFollowAssetModal] = useState(false)
  const [onBuyPortfolioModal, setOnBuyPortfolioModal] = useState(false)
  const [portfolioStatus, setPortfolioStatus] = useState<string>(
    member.portfolioStatus,
  )

  const handleFollowClick = () => {
    onFollowModal({
      memberId: member.memberId,
    })
  }

  const handlePortFolioClick = () => {
    setOnFollowAssetModal((prev) => !prev)
  }

  const handleBuyPortFolioClick = () => {
    setOnBuyPortfolioModal(!onBuyPortfolioModal)
  }

  function renderButton() {
    return (
      <ProfileEditButtonDiv>
        <BrandButtonComponent
          content={member.isFollow ? '팔로잉' : '팔로우'}
          color={null}
          cancel={member.isFollow}
          onClick={handleFollowClick}
          disabled={member.isFollow}
        />
        <BrandButtonComponent
          imgSrc={
            portfolioStatus === 'PRIVATE'
              ? '/icon/lock.svg'
              : '/icon/unlock.svg'
          }
          content="포트폴리오"
          color={null}
          cancel={portfolioStatus !== 'PRIVATE'}
          onClick={
            portfolioStatus === 'PRIVATE'
              ? handleBuyPortFolioClick
              : handlePortFolioClick
          }
          disabled={false}
        />
      </ProfileEditButtonDiv>
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
      {onBuyPortfolioModal && (
        <BuyPortfolioModal
          memberId={member.memberId}
          nickname={member.nickname}
          price={member.portfolioPrice}
          handleBuyPortFolioClick={handleBuyPortFolioClick}
          setPortfolioStatus={setPortfolioStatus}
        />
      )}
      <ComponentTitleH3>프로필</ComponentTitleH3>
      <ProfileDetailContainer>
        <ProfileImg>
          <img src={member.profileUrl} alt="member-profile" />
        </ProfileImg>
        <NicknameDiv>{member.nickname}</NicknameDiv>
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
