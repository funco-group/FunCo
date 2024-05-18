import { useState } from 'react'
import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'

import { MemberType } from '@/interfaces/userPage/MemberType'
import medalMap from '@/lib/medalMap'
import { editIntroduction, editNickname } from '@/apis/member'
import { ComponentTitleH3 } from '@/containers/UserPageContainer/styled'
import {
  IntroductionDiv,
  NicknameDiv,
  ProfileButtonDiv,
  ProfileDetailContainer,
  ProfileInput,
  ProfileRankDiv,
  ProfileRankFlexDiv,
  ProfileRankOuterDiv,
  ProfileTextArea,
  UserPageProfileContainer,
  ProfileImg,
} from './UserPageProfile.styled'
import SetPortfolioModal from './SetPortfolioModal'

interface UserPageProfileProps {
  member: MemberType
}

function MyPageProfile({ member }: UserPageProfileProps) {
  const [nickname, setNickname] = useState(member.nickname)
  const [isEditNickname, setIsEditNickname] = useState(false)
  const [settingPortfolio, setSettingPortfolio] = useState<boolean>(false)
  const [introduction, setIntroduction] = useState(
    member.introduction ? member.introduction : '한 줄 소개를 입력해주세요!',
  )
  const [isEditIntro, setIsEditIntro] = useState(false)

  const handleNicknameInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value)
  }

  const handleNicknameEditClick = async () => {
    setIsEditNickname(!isEditNickname)

    if (isEditNickname) {
      await editNickname(nickname)
    }
  }

  const handleIntroInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIntroduction(e.target.value)
  }

  const handleIntroEditClick = async () => {
    setIsEditIntro((prev) => !prev)

    if (isEditIntro) {
      await editIntroduction(introduction)
    }
  }

  const handleSettingPortfolio = () => {
    setSettingPortfolio(!settingPortfolio)
  }

  return (
    <UserPageProfileContainer>
      {settingPortfolio && (
        <SetPortfolioModal
          handleSettingPortfolio={handleSettingPortfolio}
          portfolioStatus={member.portfolioStatus}
          portfolioPrice={member.portfolioPrice ? member.portfolioPrice : 0}
        />
      )}
      <ComponentTitleH3>프로필</ComponentTitleH3>
      <ProfileDetailContainer>
        <ProfileImg>
          <img src={member.profileUrl} alt="member-profile" />
        </ProfileImg>
        <NicknameDiv>
          {isEditNickname ? (
            <ProfileInput
              type="text"
              value={nickname}
              onChange={handleNicknameInput}
              maxLength={15}
            />
          ) : (
            nickname
          )}
          <img
            src="/icon/pencil.png"
            onClick={handleNicknameEditClick}
            width={15}
            alt="pencil"
          />
        </NicknameDiv>
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
        <IntroductionDiv>
          {isEditIntro ? (
            <ProfileTextArea
              type="text"
              value={introduction}
              onChange={handleIntroInput}
              maxLength={21}
            />
          ) : (
            <div>{introduction}</div>
          )}
          <img
            src="/icon/pencil.png"
            onClick={handleIntroEditClick}
            width={15}
            alt="pencil"
          />
        </IntroductionDiv>
      </ProfileDetailContainer>
      <ProfileButtonDiv>
        <BrandButtonComponent
          content="포트폴리오 설정"
          color={null}
          cancel={false}
          onClick={handleSettingPortfolio}
          disabled={false}
        />
      </ProfileButtonDiv>
    </UserPageProfileContainer>
  )
}

export default MyPageProfile
