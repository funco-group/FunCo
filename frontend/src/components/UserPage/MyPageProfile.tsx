import { useEffect, useState } from 'react'
import BrandButtonComponent from '@/components/Common/Button/BrandButtonComponent'

import { MemberType } from '@/interfaces/userPage/MemberType'
import useUserState from '@/hooks/recoilHooks/useUserState'
import medalMap from '@/lib/medalMap'
import { editIntroduction, editNickname } from '@/apis/member'
import { ComponentTitleH3 } from '@/containers/UserPageContainer/styled'
import SetPortfolioModal from './SetPortfolioModal'
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
  const [status, setStatus] = useState<boolean>(false) // private = true, public = false
  const [price, setPrice] = useState<number>(0)
  const [priceStr, setPriceStr] = useState<string>('')
  const { updateNickname } = useUserState()

  useEffect(() => {
    setPrice(member.portfolioPrice === null ? 0 : member.portfolioPrice)
    setPriceStr(
      member.portfolioPrice === null
        ? '0'
        : member.portfolioPrice.toLocaleString('ko-KR'),
    )
    setStatus(member.portfolioStatus !== 'PUBLIC')
  }, [])

  const handleNicknameInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value)
  }

  const handleNicknameEditClick = async () => {
    setIsEditNickname(!isEditNickname)

    if (isEditNickname) {
      await editNickname(nickname, () => {
        updateNickname(nickname)
      })
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
          status={status}
          setStatus={setStatus}
          price={price}
          setPrice={setPrice}
          priceStr={priceStr}
          setPriceStr={setPriceStr}
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
